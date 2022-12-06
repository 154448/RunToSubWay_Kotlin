package com.example.rts_fragment.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp
import kotlin.collections.ArrayList

class UserDataRepository {
    private val database = Firebase.firestore
    val docRef = database.collection("userData")

    fun getInfo(alarmData: MutableLiveData<MutableList<Boolean>>,
                wayData: MutableLiveData<Boolean>
    ) {
        docRef.addSnapshotListener { value, error ->
            if(error != null){
                Log.w("Listen failed.", error)
                return@addSnapshotListener
            }
            val newAlarmInfo = ArrayList<Boolean>()
            for (doc in value!!){
                doc.getBoolean("alarmEnabled")?.let { enabled ->
                    newAlarmInfo.add(enabled)
                }
                doc.getBoolean("toSusek")?.let{ way ->
                    wayData.postValue(way)
                }
            }
            alarmData.postValue(newAlarmInfo.toMutableList())
        }
    }


    fun updateAlarmChk(docPath: String, newAlarm: Boolean){
        println(newAlarm)
        docRef.document(docPath).update("alarmEnabled", newAlarm)
    }

    fun updateTime(docPath: String, newTime: Timestamp){
        docRef.document(docPath).update("time", newTime)
        docRef.document(docPath).update("modify", true)
    }

    fun updateWay(newWay: Boolean = false){
        docRef.document("7_way").update("toSusek", newWay)
    }

}


