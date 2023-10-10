package com.example.foodorderapp.data.datasource

import com.example.foodorderapp.data.database.FavoritesDao
import com.example.foodorderapp.data.entity.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesDataSource(var favoritesDao: FavoritesDao) {
    suspend fun loadFavoriteFood(): List<Favorite> =
        withContext(Dispatchers.IO) {
            return@withContext favoritesDao.loadFavoriteFood()
        }
    suspend fun addFavorites(item_id:Int ,item_name: String,item_picture:String,item_price:Int){
        val addedFavorite = Favorite(0,item_id,item_name,item_picture,item_price,"denemekullanicisi")
        favoritesDao.addFavorites(addedFavorite)
    }
    suspend fun deleteFavorites(id: Int,item_id:Int,item_price: Int){
        val deletedFood=Favorite(id,item_id,"","",item_price,"denemekullanicisi")
        favoritesDao.deleteFavorites(deletedFood)
    }
    suspend fun searchFavoriteFood(foodName : String): List<Favorite> =
        withContext(Dispatchers.IO){
            return@withContext favoritesDao.searchFavoriteFood(foodName)
        }
}