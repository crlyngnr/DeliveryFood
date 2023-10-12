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
import com.example.foodorderapp.R
import com.example.foodorderapp.databinding.FragmentCartBinding
import com.example.foodorderapp.ui.adapter.CartFoodAdapter
import com.example.foodorderapp.ui.adapter.FoodsAdapter
import com.example.foodorderapp.ui.viewmodel.CartViewModel
import com.example.foodorderapp.ui.viewmodel.DetailViewModel
import com.example.foodorderapp.ui.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private lateinit var binding:FragmentCartBinding
    private lateinit var viewModel: CartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_cart,container,false)
        binding.cartFragment=this
        viewModel.cartFoodList.observe(viewLifecycleOwner){
            val cartFoodAdapter = it?.let{it -> CartFoodAdapter(requireContext(),it,viewModel)}
            binding.cartFoodAdapter = cartFoodAdapter
        }
        viewModel.totalOrderPrice.observe(viewLifecycleOwner){
                totalPrice -> binding.textViewTotalCost.text = "Order Price: ${totalPrice}₺"
        }


        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : CartViewModel by viewModels()
        viewModel = tempViewModel
    }
    override fun onResume() {
        super.onResume()
        viewModel.loadCart()
    }
    fun orderConfirmation(view: View) {
        if ((viewModel.totalOrderPrice.value ?: 0) > 0) {
            Snackbar.make(view, R.string.order_confirmed, Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(requireContext(),R.color.appColor))
                .setTextColor(Color.WHITE).show()
            goToCheckOrder()
            removeAllItemsFromCart()
        } else {
            Snackbar.make(view, R.string.empty_cart, Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.appColor))
                .setTextColor(Color.WHITE).show()
        }
    }
    fun backToMain() {
        findNavController().navigate(R.id.backToMain)
    }
    private fun goToCheckOrder(){
        findNavController().navigate(R.id.goToCheckOut)
    }
    private fun removeAllItemsFromCart() {
        viewModel.cartFoodList.value?.forEach { cartItem ->
            viewModel.removeFromCart(cartItem.cartItemId, cartItem.username)
        }
    }

}