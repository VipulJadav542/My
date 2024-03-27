package com.rk.afterstart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rk.afterstart.databinding.ActivityRecieveDataBinding

class RecieveData : AppCompatActivity() {
    private lateinit var binding:ActivityRecieveDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecieveDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        binding.textView2.text = intent.getStringExtra("name")
        binding.textView3.text = intent.getStringExtra("city")
        binding.textView4.text = intent.getStringExtra("mobile")
        binding.textView5.text = intent.getStringExtra("email")
    }
}