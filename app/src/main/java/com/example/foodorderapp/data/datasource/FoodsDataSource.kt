package com.example.foodorderapp.data.datasource

import android.util.Log
import com.example.foodorderapp.data.entity.CartFood
import com.example.foodorderapp.data.entity.Foods
import com.example.foodorderapp.retrofit.FoodsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class FoodsDataSource(var fDao : FoodsDao) {
    val kullanici = "denemekullanicisi"
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
        Log.e("addToCartLogicSequence", "Add to cart begin")
        Log.e("addToCartLogicSequence", "Loading existing cartItems prior to addition.")
        val oldCartItemList = loadCart() ?: emptyList()
        Log.e("addToCartLogicSequence", "Old list;")
        Log.e("addToCartLogicSequence", oldCartItemList.toString())
        var additionDoesExist = false
        var oldCartItemQuantity = 0
        var oldCartItemId = 0

        if (oldCartItemList.isNotEmpty()){
            Log.e("addToCartLogicSequence", "Old list is not empty and checking if addition exists")
            for(x:CartFood in oldCartItemList){
                Log.e("addToCartLogicSequence", "For loop initiated.")
                if (x.cartItemName == itemName && x.cartItemPicture == itemPicture && x.cartItemPrice == itemPrice){
                    Log.e("addToCartLogicSequence", "Addition exists. Loading its oldquantity to memory.")
                    oldCartItemQuantity = x.cartItemQuantity
                    oldCartItemId = x.cartItemId
                    additionDoesExist = true
                    break
                }
            }
        }
        if(additionDoesExist){
            Log.e("addToCartLogicSequence", "Addition exists. Removing old item from cart.")
            removeFromCart(oldCartItemId,kullanici)
            val newQuantity = itemQuantity + oldCartItemQuantity
            Log.e("addToCartLogicSequence", "Loading old + new added quantity amount of the item.")
            fDao.addToCart(itemName,itemPicture,itemPrice,newQuantity,kullanici)
        }else{
            Log.e("addToCartLogicSequence", "Brand new item addition.")
            fDao.addToCart(itemName,itemPicture,itemPrice,itemQuantity,kullanici)
        }
    }
    suspend fun removeFromCart(cartItemId:Int,username:String){
        fDao.removeFromCart(cartItemId,username)
    }

    suspend fun loadCart() : List<CartFood> =
        withContext(Dispatchers.IO){
            try {
                Log.e("cartItemChecker","preItemLoad")
                val loadCartItemsListResponse = fDao.loadCart(kullanici)
                Log.e("cartItemChecker","postItemLoad")
                Log.e("cartItemChecker", loadCartItemsListResponse.toString())
                return@withContext loadCartItemsListResponse?.sepet_yemekler ?: emptyList()
            } catch (e: IOException) {
                Log.e("cartItemChecker","IOException during loadCartItemsList", e)
                return@withContext emptyList<CartFood>()
            } catch (e: HttpException) {
                Log.e("cartItemChecker","HttpException during loadCartItemsList", e)
                return@withContext emptyList<CartFood>()
            }
        }
    /*suspend fun addToCart(yemek_adi : String, yemek_resim_adi:String,yemek_fiyat:Int,yemek_siparis_adet:Int,kullanici_adi:String) = fDao.addToCart(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    suspend fun loadCart(): List<CartFood> =
        withContext(Dispatchers.IO){
            try {
                fDao.loadCart(kullanici).sepet_yemekler
            }catch (e:Exception){
                Log.d("FoodDataSource","Cart is empty and throws exception: \n $e")
                emptyList()
            }
        }*/

}