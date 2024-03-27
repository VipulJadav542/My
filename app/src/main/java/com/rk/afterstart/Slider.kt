package com.rk.afterstart

import android.graphics.Movie
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Slider : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider)

        val movies = ArrayList<ImageUpload>().apply{
            // add items to arraylist
        }

//        findViewById<CardSliderViewPager>(R.id.viewPager).adapter = MoviesAdapter(movies)
    }

}