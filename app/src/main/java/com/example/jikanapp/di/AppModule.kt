package com.example.jikanapp.di

import android.content.Context
import androidx.room.Room
import com.example.jikanapp.data.local.dao.AnimeDao
import com.example.jikanapp.data.local.database.AnimeDatabase
import com.example.jikanapp.data.remote.JikanService
import com.example.jikanapp.domain.repository.JikanRepo
import com.example.jikanapp.domain.repository.JikanRepoImpl
import com.example.jikanapp.utils.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val httpLoggingInterceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideJikanApi() : JikanService{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(JikanService.BASE_URL)
            .client(client)
            .build()
            .create(JikanService::class.java)
    }

    @Provides
    @Singleton
    fun provideAnimeDatabase(@ApplicationContext context: Context): AnimeDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AnimeDatabase::class.java,
            "anime_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providesRepo(
        jikanService: JikanService,
        animeDatabase: AnimeDatabase,
        networkUtil: NetworkUtil
    ): JikanRepo {
        return JikanRepoImpl(
            jikanService = jikanService,
            animeDatabase = animeDatabase,
            networkUtil = networkUtil
        )
    }

    @Provides
    @Singleton
    fun providesNetworkUtils(
        @ApplicationContext context: Context
    ) : NetworkUtil{
        return  NetworkUtil(context)
    }
}