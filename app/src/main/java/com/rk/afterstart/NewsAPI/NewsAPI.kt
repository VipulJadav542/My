package com.rk.afterstart.NewsAPI

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rk.afterstart.Notification.NewsServices
import com.rk.afterstart.R
import retrofit2.Call
import retrofit2.Response

class NewsAPI : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_api)
        getNews()
    }

    private fun getNews() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView2)
        val news = NewsServices.newsInstance.getNewsHeadlines("In", 1)
        val everything = NewsServices.newsInstance.getEverything("wsj.com")

        everything.enqueue(object : retrofit2.Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val everything = response.body()
                everything?.let {

                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Toast.makeText(this@NewsAPI, "Error", Toast.LENGTH_SHORT).show()
            }
        })

        news.enqueue(object : retrofit2.Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                news?.let {
                    val adapter=NewsAdapter(this@NewsAPI,news.articles)
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@NewsAPI)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Toast.makeText(this@NewsAPI, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
