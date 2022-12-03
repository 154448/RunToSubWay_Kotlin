package com.example.rts_fragment


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import androidx.core.app.NotificationCompat

class AlarmReceiver: BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {

        var notificationHelper: NotificationHelper = NotificationHelper(context)
        var nb: NotificationCompat.Builder = notificationHelper.getChannelNotification()

        notificationHelper.getManager().notify(1,nb.build())
    }
}