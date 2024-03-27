package com.rk.afterstart.RecyclerView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rk.afterstart.Adapters.RvAdapter
import com.rk.afterstart.Constant
import com.rk.afterstart.DataModel.RvModel
import com.rk.afterstart.R
import com.rk.afterstart.databinding.ActivityRecyclerViewBinding

class RecyclerView : AppCompatActivity() {
    private lateinit var binding: ActivityRecyclerViewBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter= RvAdapter(Constant.GetData(),this)
        binding.recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
//        binding.recyclerView.layoutManager=GridLayoutManager(this,1)
//        binding.recyclerView.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL)
        binding.recyclerView.adapter=adapter
    }
}