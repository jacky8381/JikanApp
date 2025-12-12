package com.example.jikanapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnimeEntity(
    val episodes: Int?,
    @PrimaryKey
    val mal_id: Int,
    val rank: Int?,
    @Embedded(prefix = "broadcast_")
    val broadcast: BroadcastEntity?,
    val score: Double?,
    val airing: Boolean?,
    val title_japanese: String?,
    val themes: List<ThemeEntity>?,
    val producers: List<ProducerEntity>?,
    val members: Int?,
    val background: String?,
    val scored_by: Int?,
    val title_english: String?,
    val source: String?,
    val favorites: Int?,
    val status: String?,
    val title_synonyms: List<String>?,
    val rating: String?,
    val studios: List<StudioEntity>?,
    val genres: List<GenreEntity>?,
    val title: String?,
    val demographic: List<DemographicEntity>?,
    val approved: Boolean?,
    @Embedded(prefix = "trailer_")
    val trailer: TrailerEntity?,
    val type: String?,
    val year: Int?,
    @Embedded(prefix = "aired_")
    val aired: AiredEntity?,
    val popularity: Int?,
    val url: String?,
    @Embedded(prefix = "images_")
    val images: ImagesEntity?,
    val licensors: List<LicensorEntity>?,
    val duration: String?,
    val titles: List<TitleEntity>?,
    val isDetailFetched : Boolean,
    val synopsis : String
)

