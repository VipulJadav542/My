package com.rk.afterstart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.emreesen.sntoast.SnToast
import com.emreesen.sntoast.Type

class CustomeTaostWithForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val name = findViewById<EditText>(R.id.name)
        val city = findViewById<EditText>(R.id.city)
        val mobile = findViewById<EditText>(R.id.mobile)
        val email = findViewById<EditText>(R.id.editTextText4)

        findViewById<Button>(R.id.submit).setOnClickListener {
//            name.text.clear()
//            city.text.clear()
//            mobile.text.clear()
//            email.text.clear()
            if (name.text.isNotEmpty() && city.text.isNotEmpty() && mobile.text.isNotEmpty() && email.text.isNotEmpty()) {
//                startActivity(Intent(this, CustomeTaostWithForm::class.java))
                SnToast.Builder().context(this).type(Type.SUCCESS).message("Login Successfully").iconSize(34).textSize(18).animation(true).duration(1000).backgroundColor(R.color.green).build()
            } else {
                SnToast.Builder()
                    .context(this@CustomeTaostWithForm)
                    .type(Type.ERROR)
                    .message("Please Enter All Fields") //.cancelable(false or true) Optional Default: False
                    .iconSize(34)
                    .textSize(18)
                    .animation(true)
                    .duration(3000)
                    .backgroundColor(R.color.pink)
//                     .icon(R.drawable.example) Default: It is filled according to the toast type. If an assignment is made, the assigned value is used
                    .build()
            }
        }

    }
}