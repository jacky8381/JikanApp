package com.example.jikanapp.domain.repository

import com.example.jikanapp.data.remote.response.AnimeDto
import com.example.jikanapp.domain.model.Anime
import com.example.jikanapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface JikanRepo {

    suspend fun getTopAnimeList() : Flow<Resource<List<Anime>>>

    suspend fun getAnimeDetail(animeId: Int): Flow<Resource<Anime>>
}