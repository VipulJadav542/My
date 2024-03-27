package com.rk.afterstart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rk.afterstart.databinding.ActivityGridLayoutBinding

class GridLayout : AppCompatActivity() {
    private lateinit var  binding: ActivityGridLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityGridLayoutBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}