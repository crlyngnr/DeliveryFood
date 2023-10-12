package com.example.foodorderapp.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.foodorderapp.R
import com.example.foodorderapp.databinding.FragmentDetailBinding
import com.example.foodorderapp.ui.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: DetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        binding.itemDetailsFragmentDataBindingVariable = this
        viewModel.itemQuantity.observe(viewLifecycleOwner){
            binding.itemQuantityDataBindingVariable = it
        }

        val bundle: DetailFragmentArgs by navArgs()
        val foods = bundle.foods
        binding.itemEntityDataBindingVariable = foods

        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${foods.itemPicture}"
        Glide.with(this).load(url).override(600, 850).into(binding.imageFilm)
        binding.btnAddCart.setOnClickListener {
            if(binding.itemQuantityDataBindingVariable.toString().toInt()==1){
                Snackbar.make(it, "${binding.itemQuantityDataBindingVariable.toString()} piece of ${foods.itemName} added to cart.", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.appColor))
                    .setTextColor(Color.WHITE)
                    .show()
            } else {
                Snackbar.make(it, "${binding.itemQuantityDataBindingVariable.toString()} pieces of ${foods.itemName} added to cart.", Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.appColor))
                    .setTextColor(Color.WHITE)
                    .show()
            }
            addToCart(foods.itemId,foods.itemName,foods.itemPicture,foods.ItemPrice,binding.itemQuantityDataBindingVariable.toString())
            backToMain()
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel :  DetailViewModel by viewModels()
        viewModel = tempViewModel
    }

    fun backToMain() {
        findNavController().popBackStack()
    }


    fun addToCart(itemId:Int, itemName:String,itemPicture:String,itemPrice:Int,itemQuantity:String){
        viewModel.addToCart(itemId,itemName,itemPicture,itemPrice,itemQuantity.toInt())
    }

    fun goToCart() {
        findNavController().navigate(R.id.toCart)
    }
    fun buttonIncrementClick(currentQuantity:String){
        viewModel.buttonIncrementClick(currentQuantity)
    }
    fun buttonDecrementClick(currentQuantity:String){
        viewModel.buttonDecrementClick(currentQuantity)
    }
}