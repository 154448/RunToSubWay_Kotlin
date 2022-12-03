package com.example.rts_fragment

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import androidx.work.Worker
import androidx.work.WorkerParameters

import com.example.rts_fragment.repository.UserDataRepository
import com.example.rts_fragment.viewmodel.TraidDataViewModel
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AlarmWorker(val context: Context, workerParameters: WorkerParameters)
   :Worker(context, workerParameters){


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi")


    override fun doWork(): Result {

        makeAlarm()



       return Result.success()

  }
    @RequiresApi(Build.VERSION_CODES.O)
    fun makeAlarm() {
        val intent = Intent(context, AlarmReceiver::class.java)

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH시 mm분")
        val formatted = current.format(formatter)
        intent.setData(Uri.parse(formatted))
        val pendingIntent = PendingIntent.getBroadcast(
            context, 1, intent, PendingIntent.FLAG_IMMUTABLE
        )


        context.getSystemService(AlarmManager::class.java).setExact(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 3 * 1000,
            pendingIntent
        )
    }

}