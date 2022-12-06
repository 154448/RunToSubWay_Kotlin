package com.example.rts_fragment


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rts_fragment.RetrofitData.GyeonguiObject
import com.example.rts_fragment.viewmodel.NOTRAINDATA
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

const val NOTIFICATION_ID = 2
class AlarmReceiver: BroadcastReceiver(){

//    private val _trainInfo = MutableLiveData<MutableList<String>>( NOTRAINDATA )
//    val trainInfo: LiveData<MutableList<String>> get() = _trainInfo
    fun loadTimeInfo(test: MutableLiveData<MutableList<String>>,intent: Intent?){
        val call = GyeonguiObject.getApi.changeEnd()
        call.enqueue(object: Callback<Gyeongui> {
            override fun onResponse(call: Call<Gyeongui>, response: Response<Gyeongui>) {
                if(response.isSuccessful()){
                    response.body()?.let{
                        Log.d("MainActivity",it.toString())
                        it.body.forEach{ data ->
                            Log.d("MainActivity", data.toString())
                        }
                    }
                    test.postValue(dataSave(response.body()!!.body,intent))
                }else{
                    Log.d("MainActivity", "response가 실패")
                }
            }

            override fun onFailure(call: Call<Gyeongui>, t: Throwable) {
                Log.d("MainActivity", "ErrorMsg: $t, 인터넷 연결 오류")
            }
        })

    }


    fun dataSave(body: ArrayList<Body>,intent: Intent?): MutableList<String> {  //메인 액티비티에서 loadTimeInfo 함수를 실행시켰다면 body에 data가 들어있음.
        var trainInfoArray = mutableListOf<String>()
        var count = 0
        for(i in body.indices){
            val array = splitString(body[i].arvlMsg2, body[i].trainLineNm)
            when(body[i].updnLine){
                "상행" -> {
                    if(count >= 2) continue
                    trainInfoArray.add(count*2,array[1])
                    trainInfoArray.add(count*2+1,array[0])
                    Log.d("MainActivity", trainInfoArray[count*2])
                    Log.d("MainActivity", trainInfoArray[count*2+1])
                    count++

                }
                "하행" -> {
                    if(count == 4) break
                    trainInfoArray.add(count*2,array[1])
                    trainInfoArray.add(count*2+1,array[0])
                    Log.d("MainActivity", trainInfoArray[count*2])
                    Log.d("MainActivity", trainInfoArray[count*2+1])
                    count++
                }
            }
        }
        Log.d("jebalk",trainInfoArray.toString())
//        intent?.setData(
//            Uri.parse(trainInfoArray.toString()))
        //Log.d("jebal",trainInfoArray.toString())
        intent?.putExtra("jebal",trainInfoArray.toString())
        return trainInfoArray
    }

    private fun splitString(str1: String, str2: String):Array<String> {
        val returnArr = arrayOf<String>("","")
        if(str1.length > 8){
            val targetNum = str1.split("[", "]")
            val arvlMsg = targetNum[1] + "번째 전역"
            returnArr[0] = arvlMsg
        }
        else {
            returnArr[0] = str1
        }
        val targetStr = str2.split(" - ", "방면")
        val trainLine = targetStr[0] + targetStr[2]
        returnArr[1] = trainLine

        return returnArr
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
//        val _trainInfo = MutableLiveData<MutableList<String>>( NOTRAINDATA )
//        //val trainInfo: LiveData<MutableList<String>> get() = _trainInfo
        //loadTimeInfo(_trainInfo)
        val _trainInfo = MutableLiveData<MutableList<String>>( NOTRAINDATA )
        Log.d("jabalkg",_trainInfo.value.toString())
        //dataSave(_trainInfo)
        //Log.d("jebal",_trainInfo.toString())
        loadTimeInfo(_trainInfo,intent)






        val contentIntent = Intent(context , MainActivity::class.java)
        Log.d("time","wwww")

        val pendingIntent = PendingIntent.getActivity(context, 0, contentIntent, PendingIntent.FLAG_IMMUTABLE)
        val notification = Notification.Builder(context, App.ALERT_CHANNEL_ID)
            .setContentTitle("전철도착정보")
            .setContentText(intent?.getStringExtra("jebal"))
            //.setContentText(intent?.dataString)
            //.setContentText(_trainInfo.value.toString())
            .setSmallIcon(R.drawable.ic_baseline_train_24)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        context?.getSystemService(NotificationManager::class.java)
            ?.notify(NOTIFICATION_ID, notification)

    }
}