package com.example.rts_fragment.repository

import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.material.tabs.TabLayout.TabGravity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp
import java.io.File


class UserDataRepository {
    private val database = Firebase.firestore
    private val docRef = database.collection("userData")

    fun getInfo(alarmData: MutableLiveData<BooleanArray>) {
        docRef.whereEqualTo("modify", true)
            .addSnapshotListener { value, error ->
                if(error != null){
                    Log.w("Listen failed.", error)
                    return@addSnapshotListener
                }
                println(value)

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
        docRef.document("way").update("toSusek", newWay)
    }

}


