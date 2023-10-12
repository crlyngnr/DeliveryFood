package com.example.foodorderapp.data.datasource

import com.example.foodorderapp.utils.Constants.kullanici
import com.example.foodorderapp.data.database.FavoritesDao
import com.example.foodorderapp.data.entity.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesDataSource(var favoritesDao: FavoritesDao) {

    suspend fun loadFavoriteFood(): List<Favorite> =
        withContext(Dispatchers.IO) {
            return@withContext favoritesDao.loadFavoriteFood(kullanici)
        }
    suspend fun addFavorites(item_id:Int ,item_name: String,item_picture:String,item_price:Int){
        val addedFavorite = Favorite(item_id,item_name,item_picture,item_price,kullanici)
        favoritesDao.addFavorites(addedFavorite)
    }
    suspend fun deleteFavorites(item_id:Int,item_price: Int){
        val deletedFood=Favorite(item_id,"","",item_price, kullanici)
        favoritesDao.deleteFavorites(deletedFood)
    }
    suspend fun searchFavoriteFood(foodName : String): List<Favorite> =
        withContext(Dispatchers.IO){
            return@withContext favoritesDao.searchFavoriteFood(foodName)
        }
    suspend fun getAllFavorites(): List<Favorite> {
        return favoritesDao.getAllFavorites()
    }
}