package com.example.foodorderapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.foodorderapp.R
import com.example.foodorderapp.databinding.FragmentMainBinding
import com.example.foodorderapp.ui.adapter.FoodsAdapter
import com.example.foodorderapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding : FragmentMainBinding
    private lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        binding.mainFragment=this
        binding.mainToolbarHeader = "Foods"
        viewModel.foodsList.observe(viewLifecycleOwner) {
            if (binding.recyclerViewFilmler.adapter == null) {
                val foodsAdapter = FoodsAdapter(requireContext(), it,viewModel)
                binding.foodsAdapter = foodsAdapter
            } else {
                (binding.recyclerViewFilmler.adapter as FoodsAdapter).updateItems(it)
            }
        }
        binding.imageViewFavorite.setOnClickListener {
            goToFav()
        }
        binding.floatingActionButton.setOnClickListener {
            goToCart()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchFood(newText)
                return true
            }
        })
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : MainViewModel by viewModels()
        viewModel = tempViewModel
    }
    override fun onResume() {
        super.onResume()
            viewModel.loadFoods()
        viewModel.getAllFavorites()
        viewModel.updateFavoriteItems()
    }
    fun goToFav(){
        findNavController().navigate(R.id.toFavoriteFragment)
    }
    fun goToCart(){
        findNavController().navigate(R.id.to_cartFragment)
    }

}