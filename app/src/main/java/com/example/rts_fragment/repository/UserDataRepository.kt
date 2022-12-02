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

class UserDataRepository {
    private val database = Firebase.firestore
    val docRef = database.collection("userData")

    fun getInfo(alarmData: MutableLiveData<MutableList<Boolean>>) {
        docRef.addSnapshotListener { value, error ->
            if(error != null){
                Log.w("Listen failed.", error)
                return@addSnapshotListener
            }
            val newData = ArrayList<Boolean>()
            for (doc in value!!){
                doc.getBoolean("alarmEnabled")?.let {
                    newData.add(it as Boolean)
                }
                doc.getBoolean("toSusek")?.let{
                    newData.add(it)
                }

            }
            println(newData)

            alarmData.postValue(newData.toMutableList())

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


