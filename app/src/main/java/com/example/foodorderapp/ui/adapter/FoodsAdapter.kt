package com.example.foodorderapp.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderapp.R
import com.example.foodorderapp.data.entity.Foods
import com.example.foodorderapp.databinding.FoodItemBinding
import com.example.foodorderapp.ui.fragment.MainFragmentDirections
import com.example.foodorderapp.ui.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class FoodsAdapter(var mContext:Context, var foodsList : List<Foods>,var mainViewModel: MainViewModel)
    : RecyclerView.Adapter<FoodsAdapter.FoodsViewHolder>(){

    inner class FoodsViewHolder(var foodItemBinding: FoodItemBinding) : RecyclerView.ViewHolder(foodItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodsViewHolder {
        val binding:FoodItemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.food_item,parent,false)
        return FoodsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodsViewHolder, position: Int) {
        val foods = foodsList.get(position)
        val f = holder.foodItemBinding

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${foods.itemPicture}"
        Glide.with(mContext).load(url).override(500,750).into(f.imageViewFood)
        f.foodsItem = foods
        f.imageAddCart.setOnClickListener {
            val transaction = MainFragmentDirections.toDetail(foods = foods)
            Navigation.findNavController(it).navigate(transaction)
        }
        f.cardFoods.setOnClickListener {
            val transaction = MainFragmentDirections.toDetail(foods = foods)
            Navigation.findNavController(it).navigate(transaction)
        }
        if (isItemInFavorites(foods.itemId)) {
            f.imageView3.setImageResource(R.drawable.ic_favorite_red_24)
        } else {
            f.imageView3.setImageResource(R.drawable.ic_avorite)
        }
        f.imageView3.setOnClickListener {
            val item = foodsList[position]
            val imageView = f.imageView3
            if (isItemInFavorites(item.itemId)) {
                deleteFavorites(item.itemId, item.ItemPrice)
                imageView.setImageResource(R.drawable.ic_avorite)
            } else {
                addFavorites(item.itemId, item.itemName, item.itemPicture, item.ItemPrice)
                imageView.setImageResource(R.drawable.ic_favorite_red_24)
            }
        }

    }

    override fun getItemCount(): Int { return  foodsList.size}
    fun updateItems(newItems : List<Foods>){
        this.foodsList = newItems
        notifyDataSetChanged()
    }
    fun addFavorites(item_id:Int ,item_name: String,item_picture:String,item_price:Int){
        mainViewModel.addFavorites(item_id,item_name,item_picture,item_price)
    }
    fun deleteFavorites(item_id: Int, item_price: Int){
        mainViewModel.deleteFavorites(item_id,item_price)
    }
    private fun isItemInFavorites(itemId: Int): Boolean {
        val favoriteItems = mainViewModel.favoriteItems.value
        return favoriteItems?.any { it.item_id == itemId } == true
    }
}