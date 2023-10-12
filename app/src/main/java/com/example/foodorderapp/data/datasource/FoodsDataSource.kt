package com.example.foodorderapp.data.datasource

import android.util.Log
import com.example.foodorderapp.utils.Constants.kullanici
import com.example.foodorderapp.data.entity.CartFood
import com.example.foodorderapp.data.entity.Foods
import com.example.foodorderapp.retrofit.FoodsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class FoodsDataSource(var fDao : FoodsDao) {
    suspend fun loadFoods():List<Foods> =
        withContext(Dispatchers.IO){
            return@withContext fDao.loadFoods().yemekler
        }

    suspend fun buttonIncrementClick(currentQuantity:String) : String =
        withContext(Dispatchers.IO){
            val oldQuantity = currentQuantity.toInt()
            val newQuantity = oldQuantity+1
            return@withContext newQuantity.toString()
        }

    suspend fun buttonDecrementClick(currentQuantity:String) : String =
        withContext(Dispatchers.IO){
            val oldQuantity = currentQuantity.toInt()
            var newQuantity = oldQuantity
            if(oldQuantity>1){
                newQuantity -=1
            }
            return@withContext newQuantity.toString()
        }

    suspend fun addToCart(itemId:Int,itemName:String,itemPicture:String,itemPrice:Int,itemQuantity:Int) {
        val oldCartItemList = loadCart() ?: emptyList()
        var additionDoesExist = false
        var oldCartItemQuantity = 0
        var oldCartItemId = 0

        if (oldCartItemList.isNotEmpty()){
            for(cartFood:CartFood in oldCartItemList){
                if (cartFood.cartItemName == itemName && cartFood.cartItemPicture == itemPicture && cartFood.cartItemPrice == itemPrice){
                    oldCartItemQuantity = cartFood.cartItemQuantity
                    oldCartItemId = cartFood.cartItemId
                    additionDoesExist = true
                    break
                }
            }
        }
        if(additionDoesExist){
            removeFromCart(oldCartItemId,kullanici)
            val newQuantity = itemQuantity + oldCartItemQuantity
            fDao.addToCart(itemName,itemPicture,itemPrice,newQuantity,kullanici)
        }else{
            fDao.addToCart(itemName,itemPicture,itemPrice,itemQuantity,kullanici)
        }
    }
    suspend fun removeFromCart(cartItemId:Int,username:String){
        fDao.removeFromCart(cartItemId,username)
    }

    suspend fun loadCart() : List<CartFood> =
        withContext(Dispatchers.IO){
            try {
                val loadCartItemsListResponse = fDao.loadCart(kullanici)
                return@withContext loadCartItemsListResponse.sepet_yemekler ?: emptyList()
            } catch (e: IOException) {
                return@withContext emptyList<CartFood>()
            } catch (e: HttpException) {
                return@withContext emptyList<CartFood>()
            }
        }
}