package com.example.jikanapp.data.local.database

import androidx.room.TypeConverter
import com.example.jikanapp.data.local.entity.DemographicEntity
import com.example.jikanapp.data.local.entity.GenreEntity
import com.example.jikanapp.data.local.entity.LicensorEntity
import com.example.jikanapp.data.local.entity.ProducerEntity
import com.example.jikanapp.data.local.entity.StudioEntity
import com.example.jikanapp.data.local.entity.ThemeEntity
import com.example.jikanapp.data.local.entity.TitleEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromStringList(list: List<String>?): String =
        gson.toJson(list ?: emptyList<String>())

    @TypeConverter
    fun toStringList(value: String?): List<String> =
        safeDecode<List<String>>(value)


    @TypeConverter
    fun fromGenreList(list: List<GenreEntity>?): String =
        gson.toJson(list ?: emptyList<GenreEntity>())

    @TypeConverter
    fun toGenreList(value: String?): List<GenreEntity> =
        safeDecode<List<GenreEntity>>(value)


    @TypeConverter
    fun fromThemeList(list: List<ThemeEntity>?): String =
        gson.toJson(list ?: emptyList<ThemeEntity>())

    @TypeConverter
    fun toThemeList(value: String?): List<ThemeEntity> =
        safeDecode<List<ThemeEntity>>(value)


    @TypeConverter
    fun fromProducerList(list: List<ProducerEntity>?): String =
        gson.toJson(list ?: emptyList<ProducerEntity>())

    @TypeConverter
    fun toProducerList(value: String?): List<ProducerEntity> =
        safeDecode<List<ProducerEntity>>(value)


    @TypeConverter
    fun fromStudioList(list: List<StudioEntity>?): String =
        gson.toJson(list ?: emptyList<StudioEntity>())

    @TypeConverter
    fun toStudioList(value: String?): List<StudioEntity> =
        safeDecode<List<StudioEntity>>(value)


    @TypeConverter
    fun fromLicensorList(list: List<LicensorEntity>?): String =
        gson.toJson(list ?: emptyList<LicensorEntity>())

    @TypeConverter
    fun toLicensorList(value: String?): List<LicensorEntity> =
        safeDecode<List<LicensorEntity>>(value)


    @TypeConverter
    fun fromTitleEntityList(list: List<TitleEntity>?): String =
        gson.toJson(list ?: emptyList<TitleEntity>())

    @TypeConverter
    fun toTitleEntityList(value: String?): List<TitleEntity> =
        safeDecode<List<TitleEntity>>(value)


    @TypeConverter
    fun fromDemographicList(list: List<DemographicEntity>?): String =
        gson.toJson(list ?: emptyList<DemographicEntity>())

    @TypeConverter
    fun toDemographicList(value: String?): List<DemographicEntity> =
        safeDecode<List<DemographicEntity>>(value)


    private inline fun <reified T> safeDecode(value: String?): T {
        if (value.isNullOrBlank()) return emptyList<Any>() as T

        return try {
            gson.fromJson<T>(value, object : TypeToken<T>() {}.type)
                ?: emptyList<Any>() as T
        } catch (e: Exception) {
            emptyList<Any>() as T
        }
    }
}
