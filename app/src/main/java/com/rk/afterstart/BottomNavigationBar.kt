package com.rk.afterstart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rk.afterstart.databinding.ActivityBottomNavigationBarBinding

class BottomNavigationBar : AppCompatActivity() {
    private lateinit var binding:ActivityBottomNavigationBarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBottomNavigationBarBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}