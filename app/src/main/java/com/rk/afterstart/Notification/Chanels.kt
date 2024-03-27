package com.rk.afterstart.Notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class Chanels:Application() {
    final public val channel_1="channel 1"
    final public val channel_2="channel 2"

    override fun onCreate() {
        super.onCreate()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel1=NotificationChannel(channel_1,"channel1",NotificationManager.IMPORTANCE_HIGH)
            channel1.description="this is high importance channel"

            val channel2=NotificationChannel(channel_2,"channel2",NotificationManager.IMPORTANCE_DEFAULT)
            channel2.description="this is default importance channel"


            val manager=getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(channel1)
            manager.createNotificationChannel(channel2)



        }
    }

}