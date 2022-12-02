package com.example.rts_fragment.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rts_fragment.repository.UserDataRepository
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.LocalTime

val UNCHECKED = mutableListOf<Boolean>(false, false, false, false, false, false, false, false)
val NOTRAINDATA = mutableListOf<String>("일산행", "9:00", "문산행", "9:00", "청량리행", "9:10", "서울역행", "12:00")
class TraidDataViewModel: ViewModel() {
    //AlarmData
    private val _alarm = MutableLiveData<MutableList<Boolean>>( UNCHECKED )
    val alarm: MutableLiveData<MutableList<Boolean>> get() = _alarm

    private val repository = UserDataRepository()
    init{
        repository.getInfo(_alarm)
    }
    private val _trainInfo = MutableLiveData<MutableList<String>>( NOTRAINDATA )
    val trainInfo: MutableLiveData<MutableList<String>> get() = _trainInfo

    //Train_Information
    fun getTrainWay(index: Int) = trainInfo.value?.get(index * 2)
    fun getTrainTime(index: Int)= trainInfo.value?.get(index * 2 + 1)

    //Alarm_Information
    val isZero get() = alarm.value?.get(0)
    val isOne get() = alarm.value?.get(1)
    val isTwo get() = alarm.value?.get(2)
    val isThree get() = alarm.value?.get(3)
    val isFour get() = alarm.value?.get(4)
    val isFive get() = alarm.value?.get(5)
    val isSix get() = alarm.value?.get(6)
    //열차방면
    val isWay get() = alarm.value?.get(7)


    fun setZero(newValue: Boolean){
        repository.updateAlarmChk("0_mon", newValue)
    }
    fun setOne(newValue: Boolean){
        //modifyAlarm(1, newValue)
        repository.updateAlarmChk("1_tue", newValue)
    }
    fun setTwo(newValue: Boolean){
        //modifyAlarm(2, newValue)
        repository.updateAlarmChk("2_wed", newValue)
    }
    fun setThree(newValue: Boolean){
        //modifyAlarm(3, newValue)
        repository.updateAlarmChk("3_thu", newValue)
    }
    fun setFour(newValue: Boolean){
        //modifyAlarm(4,newValue)
        repository.updateAlarmChk("4_fri", newValue)
    }
    fun setFive(newValue: Boolean){
        //modifyAlarm(5, newValue)
        repository.updateAlarmChk("5_sat", newValue)
    }
    fun setSix(newValue: Boolean){
        //modifyAlarm(6, newValue)
        repository.updateAlarmChk("6_sun", newValue)
    }
    //inputFragment_radioGroupWay
    fun setWay(newValue: Boolean){
        //modifyAlarm(7, newValue)
        repository.updateWay(newValue)
    }

    fun setTime(day: String, time: Timestamp){
        repository.updateTime(day, time)
    }





}