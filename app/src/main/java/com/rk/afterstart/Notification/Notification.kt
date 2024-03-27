package com.rk.afterstart.Notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.rk.afterstart.MainActivity
import com.rk.afterstart.R
import com.rk.afterstart.Registration
import com.rk.afterstart.databinding.ActivityNotificationBinding

class Notification : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        binding.high.setOnClickListener {

            val intent=Intent(this, Registration::class.java)
            intent.putExtra("Registration",binding.ndescription.text.toString())
            val pendingIntent= PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

            val notification=NotificationCompat.Builder(this@Notification,Chanels().channel_1)
            notification.setContentTitle(binding.ntitle.text.toString())
            notification.setContentText(binding.ndescription.text.toString())
            notification.setSmallIcon(R.drawable.ic_moon)
            notification.setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.add,"Back",pendingIntent)
                .addAction(R.drawable.add,"Play",null)
                .addAction(R.drawable.add,"Next",null)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)

                .build()

            manager.notify(1,notification.build())
        }
        binding.low.setOnClickListener {
            val notification=NotificationCompat.Builder(this@Notification,Chanels().channel_2)
            notification.setContentTitle(binding.ntitle.text.toString())
            notification.setContentText(binding.ndescription.text.toString())
            notification.setSmallIcon(R.drawable.ic_moon)
            notification.setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
            manager.notify(3,notification.build())
        }
    }
}