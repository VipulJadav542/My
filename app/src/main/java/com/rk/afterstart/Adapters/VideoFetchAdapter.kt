package com.rk.afterstart.Adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.MediaController
import androidx.recyclerview.widget.RecyclerView
import com.rk.afterstart.DataModel.Video
import com.rk.afterstart.VideoUpload
import com.rk.afterstart.databinding.SingleVideoBinding

class VideoFetchAdapter(private var videoList: MutableList<Video>, var context: VideoUpload):RecyclerView.Adapter<VideoFetchAdapter.MyViewHolder>()
{
    inner class MyViewHolder(val binding: SingleVideoBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    fun anim(view: View)
    {
        val animation=AlphaAnimation(0.0f,1.0f)
        animation.duration=1000
        view.startAnimation(animation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding=SingleVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        anim(holder.itemView)
        val video=videoList[position].videoId
//        Picasso.get().load(video).into(holder.binding.videoview)
        val mediaController=MediaController(context)
        mediaController.setAnchorView(holder.binding.videoview)
        holder.binding.videoview.setVideoURI(Uri.parse(video))
        holder.binding.videoview.setMediaController(mediaController)
        holder.binding.videoview.start()
    }
}