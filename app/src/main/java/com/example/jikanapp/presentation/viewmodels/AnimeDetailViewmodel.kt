package com.example.jikanapp.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
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
class AnimeDetailViewmodel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val repo: JikanRepo
) : ViewModel() {
    private val animeState = MutableStateFlow(DetailState())
    val animeId = savedStateHandle.get<Int>("animeId")
    val uiState = animeState.asStateFlow()
    init {
        if (animeId != null) {
            getAnimeDetails(animeId)
        }
    }

    fun getAnimeDetails(id : Int){
        viewModelScope.launch {
            try {
                repo.getAnimeDetail(id).collect(){data->
                    when(data){
                        is Resource.Error<*> -> {
                            animeState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = data.message.orEmpty()
                                )
                            }
                        }
                        is Resource.Loading<*> -> {
                            animeState.update {
                                it.copy(
                                    isLoading = true,
                                    errorMessage = ""
                                )
                            }
                        }
                        is Resource.Success<*> -> {
                            animeState.update {
                                it.copy(
                                    anime = data.data,
                                    isLoading = false,
                                    errorMessage = ""
                                )
                            }
                        }
                    }
                }
            }catch (e : Exception){
                animeState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = Constants.ERROR_MSG
                    )
                }
            }
        }
    }

    fun updateErrorMessage(msg : String){
        animeState.update {
            it.copy(
                errorMessage = msg
            )
        }
    }
}

data class DetailState(
    val anime : Anime? = null,
    val isLoading : Boolean = false,
    val errorMessage : String = "",
)