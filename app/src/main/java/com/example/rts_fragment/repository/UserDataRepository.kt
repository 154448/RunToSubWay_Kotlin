package com.example.rts_fragment.repository

import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.material.tabs.TabLayout.TabGravity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class UserDataRepository {
    private val database = Firebase.firestore
    val docRef = database.collection("userData")

    fun getInfo(alarmData: MutableLiveData<MutableList<Boolean>>,
                modifyData: MutableLiveData<MutableList<Boolean>>,
                wayData: MutableLiveData<Boolean>,
                userTime: MutableLiveData<MutableList<com.google.firebase.Timestamp>>
    ) {
        docRef.addSnapshotListener { value, error ->
            if(error != null){
                Log.w("Listen failed.", error)
                return@addSnapshotListener
            }
            val newAlarmInfo = ArrayList<Boolean>()
            val newModifyInfo = ArrayList<Boolean>()
            val newUserTime = ArrayList<com.google.firebase.Timestamp>()
            for (doc in value!!){
                doc.getBoolean("alarmEnabled")?.let { enabled ->
                    newAlarmInfo.add(enabled)
                }
                doc.getBoolean("toSusek")?.let{ way ->
                    wayData.postValue(way)
                }
                doc.getBoolean("modify")?.let { modify ->
                    newModifyInfo.add(modify)
                }
                doc.getTimestamp("time").let{ time ->
                    if (time != null) {
                        newUserTime.add(time)
                        println(doc.javaClass)
                    }
                    else{
                        //데이터를 가져오지 못한 경우 현재시간을 저장하고 modify를 false로 해 알림 발생을 막음
                        newUserTime.add(com.google.firebase.Timestamp.now())
                        doc.reference.update("modify", false)
                    }

                }

            }

            alarmData.postValue(newAlarmInfo.toMutableList())
            modifyData.postValue(newModifyInfo.toMutableList())
            userTime.postValue(newUserTime.toMutableList())
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


