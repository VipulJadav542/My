package com.rk.afterstart

import RetriveFromRealTimeFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.rk.afterstart.databinding.ActivityFragmentsBinding
import com.rk.afterstart.fragments.Fragment_Green
import com.rk.afterstart.fragments.Fragment_Red
import com.rk.afterstart.fragments.Fragment_Yellow

class Fragments : AppCompatActivity() {
    private lateinit var binding:ActivityFragmentsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFragmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Transition(RetriveFromRealTimeFragment())
        binding.red.setOnClickListener {
            Transition(Fragment_Red())
        }
        binding.green.setOnClickListener {
            Transition(Fragment_Green())
        }
        binding.yellow.setOnClickListener {
            Transition(Fragment_Yellow())
        }
    }
    private fun Transition(fragment: Fragment)
    {
        val manager =supportFragmentManager
        var tr=manager.beginTransaction()
        tr.replace(R.id.framLayout,fragment)
        tr.addToBackStack(null)
        tr.commit()
    }
}