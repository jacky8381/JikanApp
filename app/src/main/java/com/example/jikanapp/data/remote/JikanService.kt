package com.example.jikanapp.data.remote

import com.example.jikanapp.data.remote.response.AnimeDetailDTO
import com.example.jikanapp.data.remote.response.AnimeDto
import com.example.jikanapp.data.remote.response.AnimeListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanService {

    @GET("v4/top/anime")
    suspend fun getTopAnimeList() : Response<AnimeListDto>


    @GET("v4/anime/{anime_id}")
    suspend fun getAnimeDetails(
        @Path("anime_id") id : Int
    ) : Response<AnimeDetailDTO>

    companion object {
        const val BASE_URL = "https://api.jikan.moe/"
    }
}