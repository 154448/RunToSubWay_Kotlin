package com.example.rts_fragment


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

import androidx.core.app.NotificationCompat

const val NOTIFICATION_ID = 2
class AlarmReceiver: BroadcastReceiver(){

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {


        val contentIntent = Intent(context , MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(context, 0, contentIntent, PendingIntent.FLAG_IMMUTABLE)
        val notification = Notification.Builder(context, App.ALERT_CHANNEL_ID)
            .setContentTitle("전철도착정보")
            .setContentText("test")
            .setSmallIcon(R.drawable.ic_baseline_train_24)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        context?.getSystemService(NotificationManager::class.java)
            ?.notify(NOTIFICATION_ID, notification)

    }
}