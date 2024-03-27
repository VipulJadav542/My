package com.rk.afterstart.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.recyclerview.widget.RecyclerView
import com.emreesen.sntoast.SnToast
import com.emreesen.sntoast.Type
import com.rk.afterstart.DataModel.RvModel
import com.rk.afterstart.R
import com.rk.afterstart.databinding.RvItemBinding

class RvAdapter(var datalistItem:ArrayList<RvModel>,var context: Context):RecyclerView.Adapter<RvAdapter.MyViewHolder>(){
    inner class MyViewHolder(val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root)
    fun anim(view: View)
    {
        var animation=AlphaAnimation(0.0f,1.0f)
        animation.duration=1500
        view.startAnimation(animation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        var view=LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false)
//        return MyViewHolder(view)
        var binding=RvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return datalistItem.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        anim(holder.itemView)
        holder.binding.imageView.setImageResource(datalistItem.get(position).image)
        holder.binding.title.text=datalistItem.get(position).title
        holder.binding.description.text=datalistItem.get(position).description
        holder.itemView.setOnClickListener {
            SnToast.Builder().context(context).type(Type.SUCCESS).message("Clicked successfully")
                .iconSize(34).textSize(18).animation(true).duration(1000)
                .backgroundColor(R.color.green).build()
        }

    }
}