package com.rk.afterstart

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce


class SplaceScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splace_screen)
        Handler(Looper.getMainLooper()).postDelayed(
            {
                val progressBar = findViewById<View>(R.id.spin_kit) as ProgressBar
                val doubleBounce: Sprite = DoubleBounce()
                progressBar.indeterminateDrawable = doubleBounce
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            },4000

        )
    }
}