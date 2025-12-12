package com.example.jikanapp.data.mappers

import com.example.jikanapp.data.local.entity.*
import com.example.jikanapp.domain.model.*


fun AnimeEntity.toModel(): Anime {
    return Anime(
        episodes = episodes ?: 0,
        mal_id = mal_id ?: 0,
        rank = rank ?: 0,
        broadcast = broadcast?.toModel() ?: Broadcast(),
        score = score ?: 0.0,
        airing = airing ?: false,
        title_japanese = title_japanese.orEmpty(),
        themes = themes?.map { it.toModel() } ?: emptyList(),
        producers = producers?.map { it.toModel() } ?: emptyList(),
        members = members ?: 0,
        background = background.orEmpty(),
        scored_by = scored_by ?: 0,
        title_english = title_english.orEmpty(),
        source = source.orEmpty(),
        favorites = favorites ?: 0,
        status = status.orEmpty(),
        title_synonyms = title_synonyms ?: emptyList(),
        rating = rating.orEmpty(),
        studios = studios?.map { it.toModel() } ?: emptyList(),
        genres = genres?.map { it.toModel() } ?: emptyList(),
        title = title.orEmpty(),
        demographic = demographic?.map { it.toModel() } ?: emptyList(),
        approved = approved ?: false,
        trailer = trailer?.toModel() ?: Trailer(),
        type = type.orEmpty(),
        year = year ?: 0,
        aired = aired?.toModel() ?: Aired(),
        popularity = popularity ?: 0,
        url = url.orEmpty(),
        images = images?.toModel() ?: Images(),
        licensors = licensors?.map { it.toModel() } ?: emptyList(),
        duration = duration.orEmpty(),
        titles = titles?.map { it.toModel() } ?: emptyList(),
        synopsis = synopsis
    )
}

fun BroadcastEntity.toModel(): Broadcast = Broadcast(
    day = day.orEmpty(),
    time = time.orEmpty(),
    timezone = timezone.orEmpty(),
    string = string.orEmpty()
)

fun AiredEntity.toModel(): Aired = Aired(
    from = from.orEmpty(),
    to = to.orEmpty(),
    prop = prop?.toPropModel() ?: Prop(),
    string = string.orEmpty()
)

fun PropEntity.toPropModel():Prop  = Prop(
    from = from?.toFromModel() ?: From(),
    to = to?.toToModel() ?: To()
)

fun FromEntity.toFromModel() : From = From(
    day = this.day ?: 0,
    month = this.month ?: 0,
    year = this.year ?: 0
)
fun ToEntity.toToModel() : To = To(
    day = this.day ?: 0,
    month = this.month ?: 0,
    year = this.year ?: 0
)

fun TrailerEntity.toModel(): Trailer = Trailer(
    youtube_id = youtube_id.orEmpty(),
    url = url.orEmpty(),
    embed_url = embed_url.orEmpty()
)

fun ThemeEntity.toModel(): Theme = Theme(
    name = name.orEmpty()
)

fun ProducerEntity.toModel(): Producer = Producer(
    name = name.orEmpty()
)

fun StudioEntity.toModel(): Studio = Studio(
    name = name.orEmpty()
)

fun GenreEntity.toModel(): Genre = Genre(
    name = name.orEmpty()
)

fun TitleEntity.toModel(): Title = Title(
    type = type.orEmpty(),
    title = title.orEmpty()
)

fun LicensorEntity.toModel(): Licensor = Licensor(
    name = name.orEmpty()
)

fun DemographicEntity.toModel(): Demographic = Demographic(
    name = name.orEmpty()
)

fun ImagesEntity.toModel(): Images = Images(
    jpg = jpg?.toJpGModel() ?: Jpg(),
    webp = webp?.toWebpModel() ?: Webp()
)

fun JpgEntity.toJpGModel(): Jpg = Jpg(
    image_url = image_url.orEmpty(),
    large_image_url = large_image_url.orEmpty(),
    small_image_url = small_image_url.orEmpty()
)

fun WebpEntity.toWebpModel(): Webp = Webp(
    image_url = image_url.orEmpty(),
    large_image_url = large_image_url.orEmpty(),
    small_image_url = small_image_url.orEmpty()
)