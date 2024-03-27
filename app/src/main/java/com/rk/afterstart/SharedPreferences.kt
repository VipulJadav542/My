package com.rk.afterstart

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.rk.afterstart.DataModel.GSON
import com.rk.afterstart.databinding.ActivitySharedPreferencesBinding

class SharedPreferences : AppCompatActivity() {
    private lateinit var binding:ActivitySharedPreferencesBinding
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySharedPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editor=getSharedPreferences("sharedpre", MODE_PRIVATE)
        val data = Gson().fromJson(editor.getString("GsonData",null),GSON::class.java)
        binding.sharedprefName.setText(data.name)
        binding.sharedprefPassword.setText(data.password)



//        binding.sharedprefSave.setOnClickListener{
//            val editor=getSharedPreferences("sharedpre", MODE_PRIVATE).edit()
//            editor.putString("name",binding.sharedprefName.text.toString())
//            editor.putString("password",binding.sharedprefPassword.text.toString())
//            editor.apply()
//        }
//        binding.sharedprefSave.setOnClickListener{
//            val editor=getPreferences(MODE_PRIVATE).edit()
//            editor.putString("name",binding.sharedprefName.text.toString())
//            editor.putString("password",binding.sharedprefPassword.text.toString())
//            editor.apply()
//        }

        binding.sharedprefSave.setOnClickListener{
            val editor=getSharedPreferences("sharedpre", MODE_PRIVATE).edit()
            val userdata=GSON(binding.sharedprefName.text.toString(),binding.sharedprefPassword.text.toString())
            val data=Gson().toJson(userdata,GSON::class.java)
            editor.putString("GsonData",data)
            editor.apply()
        }

        binding.sharedprefClear.setOnClickListener {
            val editor=getSharedPreferences("sharedpre", MODE_PRIVATE).edit()
            editor.clear().apply()
        }

    }
}