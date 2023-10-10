package com.example.foodorderapp.data.repository

import com.example.foodorderapp.data.datasource.FoodsDataSource
import com.example.foodorderapp.data.entity.CartFood
import com.example.foodorderapp.data.entity.Favorite
import com.example.foodorderapp.data.entity.Foods
import kotlinx.coroutines.flow.Flow

class FoodsRepository(var fDs : FoodsDataSource) {

    suspend fun buttonIncrementClick(currentQuantity:String) : String{
        return fDs.buttonIncrementClick(currentQuantity)
    }
    suspend fun buttonDecrementClick(currentQuantity:String) : String{
        return fDs.buttonDecrementClick(currentQuantity)
    }

    suspend fun loadFoods() : List<Foods> {
        return fDs.loadFoods()
    }

    suspend fun addToCart(itemId:Int,itemName:String,itemPicture:String,itemPrice:Int,itemQuantity:Int) {
        return fDs.addToCart(itemId, itemName, itemPicture, itemPrice, itemQuantity)
    }

    suspend fun loadCart():List<CartFood> {
        return fDs.loadCart()
    }

    suspend fun removeFromCart(cartItemId:Int,username:String) {
        fDs.removeFromCart(cartItemId,username)
    }
}