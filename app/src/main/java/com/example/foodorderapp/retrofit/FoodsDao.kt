package com.example.foodorderapp.retrofit

import com.example.foodorderapp.data.entity.CRUDResponse
import com.example.foodorderapp.data.entity.CartFoodResponse
import com.example.foodorderapp.data.entity.FoodsResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodsDao {
    //http://kasimadalan.pe.hu/yemekler/tumYemekleriGetir.php
    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun loadFoods() : FoodsResponse

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun addToCart(@Field("yemek_adi") itemName:String,
                          @Field("yemek_resim_adi") itemPicture:String,
                          @Field("yemek_fiyat") itemPrice:Int,
                          @Field("yemek_siparis_adet") itemQuantity:Int,
                          @Field("kullanici_adi") username:String) : CRUDResponse

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun loadCart(@Field("kullanici_adi") username: String) : CartFoodResponse

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun removeFromCart(@Field("sepet_yemek_id") cartFoodId: Int,
                               @Field("kullanici_adi") username: String) : CRUDResponse
}