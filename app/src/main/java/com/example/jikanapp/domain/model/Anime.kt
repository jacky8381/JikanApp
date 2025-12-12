package com.example.jikanapp.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Anime(
    val episodes: Int,
    val mal_id: Int,
    val rank: Int,
    val broadcast: Broadcast,
    val score: Double,
    val airing: Boolean,
    val title_japanese: String,
    val themes: List<Theme>,
    val producers: List<Producer>,
    val members: Int,
    val background: String,
    val scored_by: Int,
    val title_english: String,
    val source: String,
    val favorites: Int,
    val status: String,
    val title_synonyms: List<String>,
    val rating: String,
    val studios: List<Studio>,
    val genres: List<Genre>,
    val title: String,
    val demographic: List<Demographic>,
    val approved: Boolean,
    val trailer: Trailer,
    val type: String,
    val year: Int,
    val aired: Aired,
    val popularity: Int,
    val url: String,
    val images: Images,
    val licensors: List<Licensor>,
    val duration: String,
    val titles: List<Title>,
    val synopsis : String
)

