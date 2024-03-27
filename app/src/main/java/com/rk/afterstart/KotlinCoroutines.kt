package com.rk.afterstart

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rk.afterstart.databinding.ActivityKotlinCoroutinesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

class KotlinCoroutines : AppCompatActivity() {
    lateinit var binding: ActivityKotlinCoroutinesBinding
    private val Tag:String="kotlin"
    public var count:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityKotlinCoroutinesBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.Counter.setOnClickListener {
            binding.textView11.text=updateCounter(count).toString()
        }
    }


    private fun updateCounter(x:Int):Int {
        count=count+1
        return count
    }

    private fun largeTask()
    {
        for(i in 0..100000000000000L)
        {
            //binding.textView12.text=i.toString()
            Log.d(Tag, "${Thread.currentThread().name}")
        }
    }



     fun  runTask(view: View)
    {
//        val job=CoroutineScope(Dispatchers.IO).launch {
//            printFollower()
//        }
         CoroutineScope(Dispatchers.IO).launch {
             Log.d(Tag, "first")
             withContext(Dispatchers.IO) {
//                 delay(2000)
                 Log.d(Tag, "Inside")
             }
             Log.d(Tag, "last")
         }
    }

    private suspend fun printFollower(){
        CoroutineScope(Dispatchers.IO).launch {
            val follower=async { getFollower()}
                Log.d(Tag, follower.await().toString())
            }
//        }


//        val job=CoroutineScope(Dispatchers.IO).async {
//            getFollower()
//            "hello"
//
//        }
//        job.join() // wait for followers
//        Log.d(Tag, "${job.await().toString()}")

    }
//    private suspend fun printFollower(){
//        var followers=0
//        val job=CoroutineScope(Dispatchers.IO).launch {
//            followers=getFollower()
//        }
//        job.join() // wait for followers
//        Log.d(Tag, "${followers.toString()}")
//
//    }

    private suspend fun  getFollower(): Int {
//        delay(5000)
        yield()
        return 53
    }

//    fun runTask(view: View) {
        //one way
//        thread {
//            largeTask()
//        }
        //another way
//        CoroutineScope(Dispatchers.IO).launch {
//            Log.d(Tag, "${Thread.currentThread().name}")
//        }
//        GlobalScope.launch(Dispatchers.Main) {
//            Log.d(Tag, "${Thread.currentThread().name}")
//        }
//        MainScope().launch(Dispatchers.Default)
//        {
//            Log.d(Tag, "${Thread.currentThread().name}")
//        }
//        CoroutineScope(Dispatchers.IO).launch {
//            task1()
//        }
//        CoroutineScope(Dispatchers.IO).launch {
//            task2()
//        }
//    }
//
//    private suspend fun task1()
//    {
//        Log.d(Tag, "Task1 Started")
//        yield()
////        delay(1000)
//        Log.d(Tag, "Task1 Ended")
//    }
//    private suspend fun task2()
//    {
//        Log.d(Tag, "Task2 Started")
//        yield()
////        delay(2000)
//        Log.d(Tag, "Task2 Ended")
//    }
}