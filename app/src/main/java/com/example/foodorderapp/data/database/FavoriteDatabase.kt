package com.example.foodorderapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodorderapp.data.entity.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase(){
    abstract fun getFavoritesDao() : FavoritesDao
}