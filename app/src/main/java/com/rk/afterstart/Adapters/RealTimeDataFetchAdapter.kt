package com.rk.afterstart.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.rk.afterstart.DataModel.RealTime
import com.rk.afterstart.DataModel.RealTime1
import com.rk.afterstart.RetriveFromRealTime
import com.rk.afterstart.databinding.RetrievDataBinding

class RealTimeDataFetchAdapter(
    private var datalistItem: MutableList<RealTime1>, var context: RetriveFromRealTime,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RealTimeDataFetchAdapter.MyViewHolder>() {
    interface OnItemClickListener {
        fun OnDeleteClick(Id: String)
        fun OnUpdateClick(Id: String, title: String, description: String,email: String,date: String)
    }

    inner class MyViewHolder(val binding: RetrievDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
//        fun bind(note: RealTime) {
//            binding.title.text = note.title
//            binding.description.text = note.description
//        }
    }
    fun anim(view: View) {
        var animation = AlphaAnimation(0.0f, 1.0f)
        animation.duration = 1500
        view.startAnimation(animation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding = RetrievDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datalistItem.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        anim(holder.itemView)
        val note = datalistItem[position]
//        val noteId=datalistItem[position].noteId
        holder.binding.title.text = note.title
        holder.binding.description.text = note.description
//        holder.itemView.setOnClickListener {
//            Toast.makeText(context,"clicked successfully",Toast.LENGTH_SHORT).show()
//        }
        holder.binding.Update.setOnClickListener {
            itemClickListener.OnUpdateClick(note.noteId, note.title, note.description,note.email,note.date_time)
        }
        holder.binding.Delete.setOnClickListener {
            itemClickListener.OnDeleteClick(note.noteId)
        }

    }
}