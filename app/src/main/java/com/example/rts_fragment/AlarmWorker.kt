package com.example.rts_fragment

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.rts_fragment.RetrofitData.GyeonguiObject

import com.example.rts_fragment.viewmodel.NOTRAINDATA
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar


class AlarmWorker(val context: Context, workerParameters: WorkerParameters)
   :Worker(context, workerParameters){
    lateinit var db: FirebaseFirestore

//    @RequiresApi(Build.VERSION_CODES.O)
//    @SuppressLint("RestrictedApi")
    lateinit var a: String
    private var _trainInfoaaaa = MutableLiveData<MutableList<String>>( NOTRAINDATA )
    val trainInfo: LiveData<MutableList<String>> get() = _trainInfoaaaa
//    //val ltime= LoadTimeInfo()
//    val aaa= _trainInfoaaaa.value
//
//
//     fun loadTimeInfo(test: MutableLiveData<MutableList<String>>, dat:Calendar){
//        val call = GyeonguiObject.getApi.changeEnd()
//        call.enqueue(object: Callback<Gyeongui> {
//            override fun onResponse(call: Call<Gyeongui>, response: Response<Gyeongui>) {
//                if(response.isSuccessful()){
//                    response.body()?.let{
//                        Log.d("MainActivity",it.toString())
//                        it.body.forEach{ data ->
//                            Log.d("MainActivity", data.toString())
//                        }
//                    }
//                    test.postValue(dataSave(response.body()!!.body,dat))
//                }else{
//                    Log.d("MainActivity", "response가 실패")
//                }
//            }
//
//            override fun onFailure(call: Call<Gyeongui>, t: Throwable) {
//                Log.d("MainActivity", "ErrorMsg: $t, 인터넷 연결 오류")
//            }
//        })
//
//    }
//
//
//    fun dataSave(body: ArrayList<Body>,dat: Calendar): MutableList<String> {  //메인 액티비티에서 loadTimeInfo 함수를 실행시켰다면 body에 data가 들어있음.
//        var trainInfoArray = mutableListOf<String>()
//        var count = 0
//        for(i in body.indices){
//            val array = splitString(body[i].arvlMsg2, body[i].trainLineNm)
//            when(body[i].updnLine){
//                "상행" -> {
//                    if(count >= 2) continue
//                    trainInfoArray.add(count*2,array[1])
//                    trainInfoArray.add(count*2+1,array[0])
//                    Log.d("MainActivity", trainInfoArray[count*2])
//                    Log.d("MainActivity", trainInfoArray[count*2+1])
//                    count++
//
//                }
//                "하행" -> {
//                    if(count == 4) break
//                    trainInfoArray.add(count*2,array[1])
//                    trainInfoArray.add(count*2+1,array[0])
//                    Log.d("MainActivity", trainInfoArray[count*2])
//                    Log.d("MainActivity", trainInfoArray[count*2+1])
//                    count++
//                }
//            }
//        }
//        Log.d("jebal",trainInfoArray.toString())
//        makeAlarm(trainInfoArray,dat)
//        return trainInfoArray
//    }
//
//    private fun splitString(str1: String, str2: String):Array<String> {
//        val returnArr = arrayOf<String>("","")
//        if(str1.length > 8){
//            val targetNum = str1.split("[", "]")
//            val arvlMsg = targetNum[1] + "번째 전역"
//            returnArr[0] = arvlMsg
//        }
//        else {
//            returnArr[0] = str1
//        }
//        val targetStr = str2.split(" - ", "방면")
//        val trainLine = targetStr[0] + targetStr[2]
//        returnArr[1] = trainLine
//
//        return returnArr
//    }
//
//
//
//    fun makeAlarm(list: MutableList<String>,k : Calendar) {
//        Log.d("jebal",list.toString())
//        val intent = Intent(context, AlarmReceiver::class.java)
//        intent.putExtra("dgaja",list.toString())
//
//        //intent.setData(Uri.parse(list.toString()))
//        val pendingIntent = PendingIntent.getBroadcast(
//            context, 1, intent, PendingIntent.FLAG_IMMUTABLE
//        )
//
//        context.getSystemService(AlarmManager::class.java).setExact(
//            AlarmManager.RTC_WAKEUP,
//            k.timeInMillis,
//            pendingIntent
//        )
//    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {


        //MakeAlarm(context).doit()
        //loadTimeInfo(_trainInfoaaaa)
        //Log.d("jesus",k.toString())
        LoadTimeInfo().loadTimeInfo(_trainInfoaaaa)
        //var aa = dataSave(_trainInfot)
        Log.d("jebal",_trainInfoaaaa.value.toString())
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
        //LoadTimeInfo()
        db.collection("userData").document(a)
            .get()
            .addOnSuccessListener { result ->
                if(result.getBoolean("alarmEnabled")==true && result.getBoolean("modify")==true) {
                    Log.d(TAG, "{document.id}==>${result}")
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
                        //loadTimeInfo(_trainInfoaaaa,cal)
                        //MakeAlarm(context).loadTimeInfo(_trainInfoaaaa,cal)
                }

            }
            .addOnFailureListener{exception->
                Log.d(TAG, "Error getting documents:", exception)
            }

       return Result.success()

  }
//    @RequiresApi(Build.VERSION_CODES.O)
    fun makeAlarm(k : Calendar) {
        Log.d("alarm",k.toString())
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

