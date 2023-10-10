package com.example.foodorderapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodorderapp.R
import com.example.foodorderapp.data.entity.Favorite
import com.example.foodorderapp.databinding.FavItemBinding
import com.example.foodorderapp.ui.viewmodel.FavoriteViewModel
import com.google.android.material.snackbar.Snackbar

class FavoritesAdapter(var mContext : Context,var favoriteList : List<Favorite>,var viewModel: FavoriteViewModel)
    :RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>(){

    inner class FavoriteViewHolder(var design : FavItemBinding) : RecyclerView.ViewHolder(design.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesAdapter.FavoriteViewHolder {
        val binding : FavItemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
            R.layout.fav_item,parent,false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesAdapter.FavoriteViewHolder, position: Int) {
        val favorite =favoriteList.get(position)
        val f = holder.design
        f.favItem =favorite
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${favorite.item_picture}"
        Glide.with(mContext).load(url).override(100,100).into(f.imageView2)
        f.imageBtnDelete.setOnClickListener {
            Snackbar.make(it,"${favorite.item_name} silinsin mi ?", Snackbar.LENGTH_SHORT).setAction("EVET"){
                viewModel.deleteFavorites(favorite.id,favorite.item_id,favorite.item_price)
            }.show()
        }
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }
}