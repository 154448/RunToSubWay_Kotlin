package com.example.rts_fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class NotificationHelper(base: Context?) : ContextWrapper(base) {
    private val channelID = "channelID"

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel() {
        var channel = NotificationChannel(
            channelID, "Alert",
            NotificationManager.IMPORTANCE_HIGH
        )

        getManager().createNotificationChannel(channel)


    }

    fun getManager(): NotificationManager {
        return getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }
    val contentIntent = Intent(this , MainActivity::class.java)


    val pendingIntent = PendingIntent.getActivity(this, 0, contentIntent, PendingIntent.FLAG_IMMUTABLE)




    fun getChannelNotification(): NotificationCompat.Builder {
        return NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle("전철도착정보")
            .setContentText("알림")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.ic_baseline_train_24)
            .setContentIntent(pendingIntent)



    }
}