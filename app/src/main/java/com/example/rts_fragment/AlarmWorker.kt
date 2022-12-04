package com.example.rts_fragment

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.icu.text.MessageFormat.format
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.text.format.DateFormat.format
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import androidx.work.Worker
import androidx.work.WorkerParameters

import com.example.rts_fragment.repository.UserDataRepository
import com.example.rts_fragment.viewmodel.TraidDataViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.internal.bind.util.ISO8601Utils.format
import okhttp3.internal.http.HttpDate.format
import java.lang.String.format
import java.sql.Timestamp
import java.text.DateFormat
import java.text.MessageFormat.format
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


class AlarmWorker(val context: Context, workerParameters: WorkerParameters)
   :Worker(context, workerParameters){
    lateinit var db: FirebaseFirestore

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi")
    lateinit var a: String


    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        db=Firebase.firestore
        var cal: Calendar = Calendar.getInstance()
        var todayTime: Calendar = Calendar.getInstance()

        if (cal.get(Calendar.DAY_OF_WEEK)  == 1) a = "6_sun"
        else if (cal.get(Calendar.DAY_OF_WEEK) == 2) a = "0_mon"
        else if (cal.get(Calendar.DAY_OF_WEEK) == 3) a = "1_tue"
        else if (cal.get(Calendar.DAY_OF_WEEK) == 4) a = "2_wed"
        else if (cal.get(Calendar.DAY_OF_WEEK) == 5) a = "3_thu"
        else if (cal.get(Calendar.DAY_OF_WEEK) == 6) a = "4_fri"
        else if (cal.get(Calendar.DAY_OF_WEEK) == 7) a = "5_sat"

        db.collection("userData").document(a)
            .get()
            .addOnSuccessListener { result ->
                if(result.getBoolean("alarmEnabled")==true && result.getBoolean("modify")==true) {
                    //Log.d(TAG, "{document.id}==>${result}")
                    var curTime = result.getTimestamp("time")

                    var k = curTime?.seconds?.toLong()
                    if (k != null) {
                        k=k*1000
                        todayTime.timeInMillis=k
                    }

                    cal.set(Calendar.SECOND,0)
                    cal.set(Calendar.HOUR, todayTime.get(Calendar.HOUR))
                    cal.set(Calendar.MINUTE, todayTime.get(Calendar.MINUTE))

                    if (System.currentTimeMillis() < cal.timeInMillis)
                        makeAlarm(cal)
                }

            }
            .addOnFailureListener{exception->
                Log.d(TAG, "Error getting documents:", exception)
            }

       return Result.success()

  }
    @RequiresApi(Build.VERSION_CODES.O)
    fun makeAlarm(k : Calendar) {

        val intent = Intent(context, AlarmReceiver::class.java)
//        intent.setData(Uri.parse(formatted))
        val pendingIntent = PendingIntent.getBroadcast(
            context, 1, intent, PendingIntent.FLAG_IMMUTABLE
        )

        context.getSystemService(AlarmManager::class.java).setExact(
            AlarmManager.RTC_WAKEUP,
            k.timeInMillis,
            pendingIntent
        )
    }

}

