package com.example.foodorderapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodorderapp.data.entity.Favorite
import com.example.foodorderapp.data.entity.Foods
import com.example.foodorderapp.data.repository.FavoritesRepository
import com.example.foodorderapp.data.repository.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var fRepo : FoodsRepository,var favoritesRepository: FavoritesRepository) : ViewModel() {
    var foodsList = MutableLiveData<List<Foods>>()
    var favoriteItems = MutableLiveData<List<Favorite>>()
    private var allFoods : List<Foods> = listOf()
    private var allFavorites : List<Favorite> = listOf()
    init {
        loadFoods()
        getAllFavorites()
        updateFavoriteItems()
    }
    fun getAllFavorites() {
        CoroutineScope(Dispatchers.Main).launch {
            allFavorites =favoritesRepository.getAllFavorites()
            foodsList.value =allFoods
        }
    }
    fun loadFoods() {
        CoroutineScope(Dispatchers.Main).launch {
            allFoods =fRepo.loadFoods()
            foodsList.value =allFoods
        }
    }
    fun addFavorites(item_id: Int, item_name: String, item_picture: String, item_price: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            favoritesRepository.addFavorites(item_id, item_name, item_picture, item_price)
            updateFavoriteItems()
        }
    }

    fun deleteFavorites(item_id: Int, item_price: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            favoritesRepository.deleteFavorites(item_id, item_price)
            updateFavoriteItems()
        }
    }
    fun updateFavoriteItems() {
        CoroutineScope(Dispatchers.Main).launch {
            favoriteItems.value = favoritesRepository.getAllFavorites()
        }
    }

    fun searchFood(query:String){
        CoroutineScope(Dispatchers.Main).launch{
            val searchFoods = if (query.isEmpty()) {
                allFoods
            } else {
                allFoods.filter { foods -> foods.itemName.contains(query, ignoreCase = true) }
            }
            foodsList.value = searchFoods
        }
    }

}