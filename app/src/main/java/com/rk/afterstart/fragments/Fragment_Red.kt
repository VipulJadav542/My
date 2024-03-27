package com.rk.afterstart.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import com.rk.afterstart.R
import com.rk.afterstart.databinding.FragmentGreenBinding
import com.rk.afterstart.databinding.FragmentRedBinding

class Fragment_Red : Fragment() {
    private lateinit var binding:FragmentRedBinding
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController=androidx.navigation.Navigation.findNavController(view)

        binding.First.setOnClickListener {
            navController.navigate(R.id.action_fragment_Red_to_fragment_Green)
        }
        binding.Third.setOnClickListener {
            navController.navigate(R.id.action_fragment_Red_to_fragment_Yellow)
        }
    }
}