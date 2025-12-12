package com.example.jikanapp.domain.repository

import android.util.Log
import com.example.jikanapp.data.local.dao.AnimeDao
import com.example.jikanapp.data.local.database.AnimeDatabase
import com.example.jikanapp.data.remote.JikanService
import com.example.jikanapp.data.remote.response.AnimeDto
import com.example.jikanapp.domain.model.Anime
import com.example.jikanapp.data.mappers.toEntity
import com.example.jikanapp.data.mappers.toModel
import com.example.jikanapp.utils.Constants
import com.example.jikanapp.utils.NetworkUtil
import com.example.jikanapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JikanRepoImpl @Inject constructor(
    private val jikanService: JikanService,
    private val animeDatabase: AnimeDatabase,
    private val networkUtil: NetworkUtil
) : JikanRepo {
    val animeDao = animeDatabase.animeDao
    override suspend fun getTopAnimeList(): Flow<Resource<List<Anime>>> = flow {
        emit(Resource.Loading())
        if (networkUtil.isNetworkAvailable()) {
            try {
                val response = jikanService.getTopAnimeList()
                val responseBody = response.body()?.data
                if (response.isSuccessful && responseBody != null) {
                    animeDao.deleteAllAnime()
                    val entities = responseBody.map { it.toEntity(false) }
                    animeDao.insertAnimeList(entities)
                    emit(Resource.Success(entities.map { it.toModel() }))
                } else {
                    emit(Resource.Error(response.message()))
                }
            } catch (e: Exception) {
                Log.d("ExceptionTAG" , "e $e")
                emit(Resource.Error(Constants.ERROR_MSG))
            }
        } else {
            try {
                val cachedAnime = animeDao.getAnimeListFromDB()
                if (cachedAnime.isNotEmpty()) {
                    emit(Resource.Success(cachedAnime.map { it.toModel() }))
                } else {
                    emit(Resource.Error(Constants.NO_INTERNET_CONNECTION))
                }
            } catch (e: Exception) {
                Log.d("ExceptionTAG" , "e $e")
                emit(Resource.Error(Constants.ERROR_MSG))
            }
        }
    }


    override suspend fun getAnimeDetail(animeId: Int): Flow<Resource<Anime>> = flow {
        emit(Resource.Loading())
        try {
            val cachedAnime = animeDao.getAnimeById(animeId)
            if (cachedAnime != null && cachedAnime.isDetailFetched) {
                emit(Resource.Success(cachedAnime.toModel()))
                return@flow
            }
            if (networkUtil.isNetworkAvailable()) {
                val response = jikanService.getAnimeDetails(animeId)
                val body = response.body()?.data
                if (response.isSuccessful && body != null) {
                    val entity = body.toEntity(true)
                    animeDao.insertAnime(entity)
                    emit(Resource.Success(entity.toModel()))
                } else {
                    emit(Resource.Error(response.message()))
                }
            } else {
                emit(Resource.Error(Constants.NO_INTERNET_CONNECTION))
            }
        } catch (e: Exception) {
            Log.d("ExceptionTAG" , "e $e")
            emit(Resource.Error(e.message ?: Constants.ERROR_MSG))

        }
    }

}