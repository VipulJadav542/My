package com.rk.afterstart.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavAction
import androidx.navigation.NavController
import com.rk.afterstart.Navigation
import com.rk.afterstart.R
import com.rk.afterstart.databinding.FragmentGreenBinding

class Fragment_Green : Fragment() {
    private lateinit var binding: FragmentGreenBinding
    private lateinit var navController: NavController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("Tag","onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Tag","onCreate")
        Toast.makeText(requireContext()," onCreate", Toast.LENGTH_SHORT).show()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentGreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=androidx.navigation.Navigation.findNavController(view)

        binding.Second.setOnClickListener {
            navController.navigate(R.id.action_fragment_Green_to_fragment_Red)
        }
        binding.Third.setOnClickListener {
            navController.navigate(R.id.action_fragment_Green_to_fragment_Yellow)
        }
        binding.fragment.setOnClickListener {
            navController.navigate(R.id.action_fragment_Green_to_retriveFromRealTimeFragment)
        }

    }
    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("Tag","onActivityCreated")
        Toast.makeText(requireContext()," onActivityCreated", Toast.LENGTH_SHORT).show()

    }

    override fun onStart() {
        super.onStart()
        Log.d("Tag","onStart")
        Toast.makeText(requireContext()," onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Log.d("Tag","onStop")
        Toast.makeText(requireContext(),"onStop ", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Log.d("Tag","onPause")
        Toast.makeText(requireContext()," onPause", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Tag","onDestroyView")
        Toast.makeText(requireContext(),"onDestroyView ", Toast.LENGTH_SHORT).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("Tag","onDestroy")
        Toast.makeText(requireContext(),"onDestroy ", Toast.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("Tag","onDetach")
        Toast.makeText(requireContext()," onDetach", Toast.LENGTH_SHORT).show()
    }


}