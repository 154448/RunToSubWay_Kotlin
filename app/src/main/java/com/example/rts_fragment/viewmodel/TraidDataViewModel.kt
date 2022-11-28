package com.example.rts_fragment.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rts_fragment.repository.UserDataRepository
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.LocalTime

val UNCHECKED = booleanArrayOf(false, false, false, false, false, false, false, false)
class TraidDataViewModel: ViewModel() {
    //AlarmData
    private val _alarm = MutableLiveData<BooleanArray>(UNCHECKED)
    val alarm: LiveData<BooleanArray> get() = _alarm

    private val repository = UserDataRepository()
    init{
        repository.getInfo(_alarm)
    }

    //Train_Information
    private val _wayOne = MutableLiveData<String>("집으로")
    val wayOne: LiveData<String> get() = _wayOne
    private val _wayTwo = MutableLiveData<String>("가즈아")
    val wayTwo: LiveData<String> get() = _wayTwo
    private val _wayThree = MutableLiveData<String>("피곤타")
    val wayThree: LiveData<String> get() = _wayThree
    private val _wayFour = MutableLiveData<String>("빨리와")
    val wayFour: LiveData<String> get() = _wayFour

    private val _timeOne = MutableLiveData<String>("01:00")
    val timeOne: LiveData<String> get() = _timeOne
    private val _timeTwo = MutableLiveData<String>("02:00")
    val timeTwo: LiveData<String> get() = _timeTwo
    private val _timeThree = MutableLiveData<String>("03:00")
    val timeThree: LiveData<String> get() = _timeThree
    private val _timeFour = MutableLiveData<String>("04:00")
    val timeFour: LiveData<String> get() = _timeFour

    //For_input_Fragment_chkBox
    val isZero get() = alarm.value?.get(0)
    val isOne get() = alarm.value?.get(1)
    val isTwo get() = alarm.value?.get(2)
    val isThree get() = alarm.value?.get(3)
    val isFour get() = alarm.value?.get(4)
    val isFive get() = alarm.value?.get(5)
    val isSix get() = alarm.value?.get(6)
    //열차방면
    val isWay get() = alarm.value?.get(7)

    private fun modifyAlarm(index: Int, newValue: Boolean){
        _alarm.value = _alarm.value?.let{
            val boolArr = it
            boolArr[index] = newValue
            boolArr
        }?: UNCHECKED
    }

    fun setZero(newValue: Boolean){
        println(newValue)
        modifyAlarm(0, newValue)
        repository.updateAlarmChk("mon", newValue)
    }
    fun setOne(newValue: Boolean){
        modifyAlarm(1, newValue)
        repository.updateAlarmChk("tue", newValue)
    }
    fun setTwo(newValue: Boolean){
        modifyAlarm(2, newValue)
        repository.updateAlarmChk("wen", newValue)
    }
    fun setThree(newValue: Boolean){
        modifyAlarm(3, newValue)
        repository.updateAlarmChk("thu", newValue)
    }
    fun setFour(newValue: Boolean){
        modifyAlarm(4,newValue)
        repository.updateAlarmChk("fri", newValue)
    }
    fun setFive(newValue: Boolean){
        modifyAlarm(5, newValue)
        repository.updateAlarmChk("sat", newValue)
    }
    fun setSix(newValue: Boolean){
        modifyAlarm(6, newValue)
        repository.updateAlarmChk("sun", newValue)
    }
    //inputFragment_radioGroupWay
    fun setWay(newValue: Boolean){
        modifyAlarm(7, newValue)
        repository.updateWay(newValue)
    }

    fun setTime(day: String, time: Timestamp){
        repository.updateTime(day, time)
    }





}