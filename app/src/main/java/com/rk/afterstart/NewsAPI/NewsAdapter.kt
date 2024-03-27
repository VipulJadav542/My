package com.rk.afterstart.NewsAPI

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rk.afterstart.R
import com.rk.afterstart.databinding.SingleRowBinding

class NewsAdapter(val context: Context, val articles: List<Articles>) :
    RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding =SingleRowBinding.inflate(LayoutInflater.from(context),parent,false)
        return ArticleViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        Glide.with(context).load(article.urlToImage).into(holder.binding.imageView)
        holder.binding.title.text=article.title
        holder.binding.description.text=article.description
        holder.itemView.setOnClickListener {
            Toast.makeText(context,"clicked article",Toast.LENGTH_SHORT).show()
        }

    }

    inner class ArticleViewHolder(val binding: SingleRowBinding):RecyclerView.ViewHolder(binding.root)
}