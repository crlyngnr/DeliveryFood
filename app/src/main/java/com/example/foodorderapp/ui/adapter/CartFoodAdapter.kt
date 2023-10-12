package com.example.foodorderapp.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderapp.R
import com.example.foodorderapp.databinding.CartItemBinding
import com.example.foodorderapp.data.entity.CartFood
import com.example.foodorderapp.ui.viewmodel.CartViewModel
import com.google.android.material.snackbar.Snackbar

class CartFoodAdapter(var mContext : Context, var cartFoodList : List<CartFood>,var cartViwModel : CartViewModel)
    :RecyclerView.Adapter<CartFoodAdapter.CartFoodViewHolder>(){

    inner class CartFoodViewHolder(var cartItemBinding: CartItemBinding) : RecyclerView.ViewHolder(cartItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartFoodAdapter.CartFoodViewHolder {
        val binding:CartItemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.cart_item,parent,false)
        return CartFoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartFoodAdapter.CartFoodViewHolder, position: Int) {
        val cartFoods = cartFoodList.get(position)
        val c = holder.cartItemBinding
        c.foodsItem = cartFoods

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${cartFoods.cartItemPicture}"
        Glide.with(mContext).load(url).override(100,100).into(c.imageView2)

        c.imageBtnDelete.setOnClickListener {
            Snackbar.make(it,"${cartFoods.cartItemName} removed from cart.", Snackbar.LENGTH_SHORT).setBackgroundTint(
                ContextCompat.getColor(mContext, R.color.appColor))
                .setTextColor(Color.WHITE).show()
            removeFromCart(cartFoods.cartItemId,cartFoods.username)
        }

    }
    fun removeFromCart(cartItemId:Int,username:String){
        cartViwModel.removeFromCart(cartItemId,username)
    }
    override fun getItemCount(): Int {return cartFoodList.size }
}