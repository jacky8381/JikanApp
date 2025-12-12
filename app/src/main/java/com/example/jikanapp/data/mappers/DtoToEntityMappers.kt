package com.example.jikanapp.data.mappers

import com.example.jikanapp.data.local.entity.*
import com.example.jikanapp.data.remote.response.*

fun AnimeDto.toEntity(isDetailFetched : Boolean): AnimeEntity {
    return AnimeEntity(
        episodes = this.episodes,
        mal_id = this.mal_id ?: -1 ,
        rank = this.rank ,
        broadcast = this.broadcast?.toEntity(),
        score = this.score,
        airing = this.airing,
        title_japanese = this.title_japanese,
        themes = this.themes?.map { it.toEntity() },
        producers = this.producers?.map { it.toEntity() },
        members = this.members,
        background = this.background,
        scored_by = this.scored_by,
        title_english = this.title_english,
        source = this.source,
        favorites = this.favorites,
        status = this.status,
        title_synonyms = this.title_synonyms,
        rating = this.rating,
        studios = this.studios?.map { it.toEntity() },
        genres = this.genres?.map { it.toEntity() },
        title = this.title,
        demographic = this.demographic?.map { it.toEntity() },
        approved = this.approved,
        trailer = this.trailer?.toEntity(),
        type = this.type,
        year = this.year,
        aired = this.aired?.toEntity(),
        popularity = this.popularity,
        url = this.url,
        images = this.images?.toEntity(),
        licensors = this.licensors?.map { it.toEntity() },
        duration = this.duration,
        titles = this.titles?.map { it.toEntity() },
        isDetailFetched = isDetailFetched,
        synopsis = this.synopsis.orEmpty()
    )
}


fun BroadcastDto.toEntity(): BroadcastEntity = BroadcastEntity(
    day = this.day,
    time = this.time,
    timezone = this.timezone,
    string = this.string
)

fun AiredDto.toEntity(): AiredEntity = AiredEntity(
    from = this.from,
    to = this.to,
    prop = this.prop?.toPropEntity(),
    string = this.string
)

fun PropDto.toPropEntity(): PropEntity = PropEntity(
    from = this.from?.toFromEntity(),
    to = this.to?.toToEntity()
)

fun FromDto.toFromEntity(): FromEntity = FromEntity(
    day = this.day,
    month = this.month,
    year = this.year
)

fun ToDto.toToEntity(): ToEntity = ToEntity(
    day = this.day,
    month = this.month,
    year = this.year
)

fun JpgDto.toJpgEntity(): JpgEntity = JpgEntity(
    image_url = this.image_url,
    small_image_url = this.small_image_url,
    large_image_url = this.large_image_url
)

fun WebpDto.toWebpEntity(): WebpEntity = WebpEntity(
    image_url = this.image_url,
    small_image_url = this.small_image_url,
    large_image_url = this.large_image_url
)

fun ImagesDto.toEntity(): ImagesEntity = ImagesEntity(
    jpg = this.jpg.toJpgEntity(),
    webp = this.webp.toWebpEntity()
)

fun TrailerDto.toEntity(): TrailerEntity = TrailerEntity(
    youtube_id = this.youtube_id,
    url = this.url,
    embed_url = this.embed_url
)

fun ThemeDto.toEntity(): ThemeEntity = ThemeEntity(
    mal_id = this.mal_id,
    type = this.type,
    name = this.name,
    url = this.url
)

fun ProducerDto.toEntity(): ProducerEntity = ProducerEntity(
    mal_id = this.mal_id,
    type = this.type,
    name = this.name,
    url = this.url
)

fun StudioDto.toEntity(): StudioEntity = StudioEntity(
    mal_id = this.mal_id,
    type = this.type,
    name = this.name,
    url = this.url
)

fun GenreDto.toEntity(): GenreEntity = GenreEntity(
    mal_id = this.mal_id,
    type = this.type,
    name = this.name,
    url = this.url
)

fun TitleDto.toEntity(): TitleEntity = TitleEntity(
    type = this.type,
    title = this.title
)

fun LicensorDto.toEntity(): LicensorEntity = LicensorEntity(
    mal_id = this.mal_id,
    type = this.type,
    name = this.name,
    url = this.url
)

fun DemographicDto.toEntity(): DemographicEntity = DemographicEntity(
    mal_id = this.mal_id,
    type = this.type,
    name = this.name,
    url = this.url
)
