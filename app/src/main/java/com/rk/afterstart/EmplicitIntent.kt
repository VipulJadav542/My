package com.rk.afterstart

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.rk.afterstart.databinding.ActivityEmplicitIntentBinding

class EmplicitIntent : AppCompatActivity() {
    private lateinit var binding:ActivityEmplicitIntentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEmplicitIntentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.OpenWebPage.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https:///reatroots/"))
            startActivity(intent)
        }
        binding.openPhoneCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data= Uri.parse("tel:9023978985")
            startActivity(intent)
        }
        binding.openCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)
        }
        binding.sendData.setOnClickListener {
            val data=binding.name.text.toString()
            val intent = Intent(Intent.ACTION_SEND)
            intent.type="plain/text"
            intent.putExtra(Intent.EXTRA_TEXT, data)
            startActivity(Intent.createChooser(intent,"Share Via"))
        }
    }
}