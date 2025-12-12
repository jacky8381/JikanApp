package com.example.jikanapp.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jikanapp.domain.model.Anime
import com.example.jikanapp.domain.repository.JikanRepo
import com.example.jikanapp.utils.Constants
import com.example.jikanapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    val repo : JikanRepo
) : ViewModel() {
    private val _screenState = MutableStateFlow<AnimeListScreenState>(AnimeListScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    init {
        getAnimeList()
    }

     fun getAnimeList(){
        viewModelScope.launch {
            try {
                repo.getTopAnimeList().collect(){data->
                    when (data) {
                        is Resource.Loading -> {
                            _screenState.value = AnimeListScreenState.Loading
                        }

                        is Resource.Success -> {
                            _screenState.value = AnimeListScreenState.Success(
                                animeList = data.data.orEmpty().sortedBy { it.rank }
                            )
                        }
                        is Resource.Error -> {
                            val isNoInternet = data.message == Constants.NO_INTERNET_CONNECTION
                            _screenState.value = AnimeListScreenState.Error(
                                message = data.message.orEmpty(),
                                isNoInternet = isNoInternet
                            )
                        }
                    }

                }
            }catch (e : Exception){
                Log.d("ExceptionTAG" , "e $e")
                _screenState.value = AnimeListScreenState.Error(
                    message = Constants.ERROR_MSG,
                    isNoInternet = false
                )
            }
        }
    }
}

sealed class AnimeListScreenState {
    object Loading : AnimeListScreenState()
    data class Success(val animeList: List<Anime>) : AnimeListScreenState()
    data class Error(val message: String, val isNoInternet: Boolean) : AnimeListScreenState()
}