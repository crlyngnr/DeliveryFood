package com.example.foodorderapp.data.repository

import com.example.foodorderapp.data.datasource.FavoritesDataSource
import com.example.foodorderapp.data.entity.Favorite

class FavoritesRepository(var favoritesDataSource: FavoritesDataSource) {
    suspend fun addFavorites(item_id:Int ,item_name: String,item_picture:String,item_price:Int) =favoritesDataSource.addFavorites(item_id,item_name,item_picture,item_price)
    suspend fun deleteFavorites(id:Int,item_id: Int,item_price: Int) = favoritesDataSource.deleteFavorites(id, item_id,item_price)
    suspend fun loadFavoriteFood(): List<Favorite> = favoritesDataSource.loadFavoriteFood()
    suspend fun searchFavoriteFood(foodName : String): List<Favorite> = favoritesDataSource.searchFavoriteFood(foodName)
}