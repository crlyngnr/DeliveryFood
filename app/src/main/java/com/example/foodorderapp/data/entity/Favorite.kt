package com.example.foodorderapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "favorites")
data class Favorite(@PrimaryKey
                    @ColumnInfo("yemek_id") @NotNull var item_id:Int,
                    @ColumnInfo("yemek_adi") @NotNull var item_name: String,
                    @ColumnInfo("yemek_resim_adi") @NotNull var item_picture:String,
                    @ColumnInfo("yemek_fiyat") @NotNull var item_price:Int,
                    @ColumnInfo("username") @NotNull var username:String): Serializable {

}