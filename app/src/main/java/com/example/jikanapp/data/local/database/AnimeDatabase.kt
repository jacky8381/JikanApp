package com.example.jikanapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.jikanapp.data.local.dao.AnimeDao
import com.example.jikanapp.data.local.entity.AnimeEntity


@Database(
    entities = [AnimeEntity::class],
    version = 1
)
@TypeConverters(
    Converters::class
)
abstract class AnimeDatabase : RoomDatabase(){
    abstract val animeDao : AnimeDao
}