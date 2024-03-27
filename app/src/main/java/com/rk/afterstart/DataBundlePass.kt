package com.rk.afterstart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rk.afterstart.databinding.ActivityDataBundlePassBinding

class DataBundlePass : AppCompatActivity() {
    private lateinit var binding:ActivityDataBundlePassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDataBundlePassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submit.setOnClickListener {
            val name=binding.name.text.toString()
            val city=binding.city.text.toString()
            val mobile=binding.mobile.text.toString()
            val email=binding.email.text.toString()

            val intent=Intent(this,RecieveData::class.java)
            intent.putExtra("name", name)
            intent.putExtra("city", city)
            intent.putExtra("mobile", mobile)
            intent.putExtra("email", email)
            startActivity(intent)
        }
    }
}