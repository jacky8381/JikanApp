package com.example.jikanapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.jikanapp.data.local.entity.AnimeEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AnimeDao {

    @Upsert
    suspend fun insertAnimeList(list : List<AnimeEntity>)

    @Query("DELETE FROM AnimeEntity")
    suspend fun deleteAllAnime()

    @Query("Select * from AnimeEntity")
    suspend fun getAnimeListFromDB () : List<AnimeEntity>

    @Upsert
    suspend fun insertAnime(anime: AnimeEntity)

    @Query("SELECT * FROM AnimeEntity WHERE mal_id = :id")
    suspend fun getAnimeById(id: Int): AnimeEntity?
}