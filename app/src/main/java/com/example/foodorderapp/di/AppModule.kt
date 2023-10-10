package com.example.foodorderapp.di

import android.content.Context
import androidx.room.Room
import com.example.foodorderapp.data.database.FavoriteDatabase
import com.example.foodorderapp.data.database.FavoritesDao
import com.example.foodorderapp.data.datasource.FavoritesDataSource
import com.example.foodorderapp.data.datasource.FoodsDataSource
import com.example.foodorderapp.data.repository.FavoritesRepository
import com.example.foodorderapp.data.repository.FoodsRepository
import com.example.foodorderapp.retrofit.ApiUtils
import com.example.foodorderapp.retrofit.FoodsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
class AppModule {
    @Provides
    @Singleton
    fun provideFoodsDataSource(fDao : FoodsDao) : FoodsDataSource{
        return FoodsDataSource(fDao)
    }

    @Provides
    @Singleton
    fun provideFoodsRepository(fds : FoodsDataSource) : FoodsRepository {
        return FoodsRepository(fds)
    }
    @Provides
    @Singleton
    fun provideFilmlerDao() : FoodsDao{
        return ApiUtils.getFoodsDao()
    }

}