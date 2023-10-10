package com.example.foodorderapp.data.database

import androidx.room.*
import com.example.foodorderapp.data.entity.Favorite

@Dao
interface FavoritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorites(favorite: Favorite)

    @Delete
    suspend fun deleteFavorites(favorite: Favorite)

    @Query("SELECT * FROM favorites")
    suspend fun loadFavoriteFood():List<Favorite>

    @Query("SELECT * FROM favorites WHERE yemek_adi LIKE '%' || :foodName || '%'")
    suspend fun searchFavoriteFood(foodName: String) : List<Favorite>
}