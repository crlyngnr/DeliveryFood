package com.example.foodorderapp.ui.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodorderapp.data.entity.Favorite
import com.example.foodorderapp.data.repository.FavoritesRepository
import com.example.foodorderapp.data.repository.FoodsRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(var fRepo: FoodsRepository,var favoritesRepository: FavoritesRepository) : ViewModel() {
    var itemQuantity = MutableLiveData( "1")

    fun buttonIncrementClick(currentQuantity:String){
        CoroutineScope(Dispatchers.Main).launch{
            itemQuantity.value = fRepo.buttonIncrementClick(currentQuantity)
        }
    }
    fun buttonDecrementClick(currentQuantity:String){
        CoroutineScope(Dispatchers.Main).launch{
            itemQuantity.value = fRepo.buttonDecrementClick(currentQuantity)
        }
    }

    fun addToCart(itemId:Int,itemName:String,itemPicture:String,itemPrice:Int,itemQuantity:Int) {
        CoroutineScope(Dispatchers.Main).launch {
            fRepo.addToCart(itemId,itemName,itemPicture,itemPrice,itemQuantity)
        }
    }
}