package com.example.foodorderapp.data.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CartFood(@SerializedName("sepet_yemek_id")var cartItemId : Int,
                 @SerializedName("yemek_adi")var cartItemName : String,
                 @SerializedName("yemek_resim_adi")var cartItemPicture : String,
                 @SerializedName("yemek_fiyat")var cartItemPrice : Int,
                 @SerializedName("yemek_siparis_adet") var cartItemQuantity : Int,
                 @SerializedName("kullanici_adi") var username : String) {
    val totalCostOfCartItemEntity: Int
        get() = cartItemPrice * cartItemQuantity
}