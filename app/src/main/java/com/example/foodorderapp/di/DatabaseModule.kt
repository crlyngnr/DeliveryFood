package com.example.foodorderapp.di

import android.content.Context
import androidx.room.Room
import com.example.foodorderapp.data.database.FavoriteDatabase
import com.example.foodorderapp.data.database.FavoritesDao
import com.example.foodorderapp.data.datasource.FavoritesDataSource
import com.example.foodorderapp.data.repository.FavoritesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideFavoritesDataSource(favoritesDao: FavoritesDao) : FavoritesDataSource {
        return FavoritesDataSource(favoritesDao)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(favoritesDataSource: FavoritesDataSource) : FavoritesRepository {
        return FavoritesRepository(favoritesDataSource)
    }

    @Provides
    @Singleton
    fun provideFavoritesDao(@ApplicationContext context: Context) : FavoritesDao {
        val vt = Room.databaseBuilder(context, FavoriteDatabase::class.java,"food_favorites.sqlite")
            .createFromAsset("food_favorites.sqlite").build()
        return vt.getFavoritesDao()
    }
}