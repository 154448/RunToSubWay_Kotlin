package com.example.rts_fragment

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class App:Application() {
    companion object{
        const val ALERT_CHANNEL_ID = "com.alertnotification"
    }

    override fun onCreate() {
        super.onCreate()

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            getSystemService(NotificationManager::class.java).run {
                val alertChannel = NotificationChannel(
                    ALERT_CHANNEL_ID,
                    "Alert",
                    NotificationManager.IMPORTANCE_HIGH
                )
                createNotificationChannel(alertChannel)
            }
        }
    }



}