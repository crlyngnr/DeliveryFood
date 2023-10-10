package com.example.foodorderapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodorderapp.data.entity.Favorite
import com.example.foodorderapp.data.repository.FavoritesRepository
import com.example.foodorderapp.data.repository.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(var favoritesRepository: FavoritesRepository) : ViewModel() {
    var favoriteList = MutableLiveData<List<Favorite>>()

    init {
        loadFavoriteFood()
    }

    fun loadFavoriteFood(){
        CoroutineScope(Dispatchers.Main).launch {
            favoriteList.value = favoritesRepository.loadFavoriteFood()
        }
    }
    fun deleteFavorites(id:Int,item_id:Int,item_price:Int){
        CoroutineScope(Dispatchers.Main).launch {
            favoritesRepository.deleteFavorites(id,item_id,item_price)
            loadFavoriteFood()
        }
    }

    fun searchFavoriteFood(foodName : String){
        CoroutineScope(Dispatchers.Main).launch {
            favoriteList.value = favoritesRepository.searchFavoriteFood(foodName)}

    }}