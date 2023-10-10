package com.example.foodorderapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.foodorderapp.R
import com.example.foodorderapp.databinding.FragmentCheckOutBinding
import com.example.foodorderapp.databinding.FragmentWelcomeBinding

class CheckOutFragment : Fragment() {
    private lateinit var binding: FragmentCheckOutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_check_out, container, false)
        binding.imageButtonBack.setOnClickListener {
            val action = CheckOutFragmentDirections.backToHome()
            Navigation.findNavController(it).navigate(action)
        }
        return binding.root
    }
}