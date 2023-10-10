package com.example.foodorderapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodorderapp.data.entity.CartFood
import com.example.foodorderapp.data.repository.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

    @HiltViewModel
    class CartViewModel @Inject constructor(var fRepo : FoodsRepository) : ViewModel() {
        var cartFoodList = MutableLiveData<List<CartFood>?>()
        var totalOrderPrice = MutableLiveData<Int>(0)

        init {
            loadCart()
        }

        fun loadCart() {
            CoroutineScope(Dispatchers.Main).launch {
                val updatedCartItemsList = fRepo.loadCart() ?: emptyList()
                cartFoodList.value = updatedCartItemsList
                var updatedTotalOrderPrice = 0
                if(updatedCartItemsList.isNotEmpty()){
                    for(x: CartFood in updatedCartItemsList){
                        updatedTotalOrderPrice += x.totalCostOfCartItemEntity
                    }
                }
                totalOrderPrice.value = updatedTotalOrderPrice
            }

            }
        fun removeFromCart(cartItemId:Int,username:String){
            CoroutineScope(Dispatchers.Main).launch {
                fRepo.removeFromCart(cartItemId,username)
                loadCart()
            }
        }
    }