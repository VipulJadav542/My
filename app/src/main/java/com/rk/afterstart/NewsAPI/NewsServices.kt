package com.rk.afterstart.Notification

import com.rk.afterstart.NewsAPI.News
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val Base_Url="https://newsapi.org/"
const val API_KEY="0a01bf44e5f74608a5267ac5b53fcf52"

interface NewsInterface {
    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getNewsHeadlines(@Query("country") country: String,@Query("page") page: Int):Call<News>

    @GET("v2/everything?apiKey=$API_KEY")
    fun getEverything(@Query("domains") domain:String):Call<News>

}

object  NewsServices{
    val newsInstance:NewsInterface

    init {
        val retrofit=Retrofit.Builder()
            .baseUrl(Base_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance=retrofit.create(NewsInterface::class.java)

    }

}