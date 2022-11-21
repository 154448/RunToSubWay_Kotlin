package com.example.rts_fragment.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
/*
class DataStoreMoudle(private val context: Context){
    private val context: Context.datastore by
            prefer

}

 */
const val UNCHECKED = "00000000"
class TraidDataViewModel: ViewModel() {
    val temp : Array<String?> = arrayOfNulls<String?>(14)
    private val _userInfo: MutableList<String?> = temp.toMutableList()
    val userInfo: MutableList<String?> get() = _userInfo

    //AlarmData
    private val _alarm = MutableLiveData<String>(UNCHECKED)
    val alarm: LiveData<String> get() = _alarm

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
    val isZero get() = alarm.value?.get(0) == '1'
    val isOne get() = alarm.value?.get(1) == '1'
    val isTwo get() = alarm.value?.get(2) == '1'
    val isThree get() = alarm.value?.get(3) == '1'
    val isFour get() = alarm.value?.get(4) == '1'
    val isFive get() = alarm.value?.get(5) == '1'
    val isSix get() = alarm.value?.get(6) == '1'
    //열차방면
    val isWay get() = alarm.value?.get(7) == '1'

    private fun modifyAlarm(index: Int, newValue: Char){
        _alarm.value = _alarm.value?.let{
            val chArr = it.toCharArray()
            chArr[index] = newValue
            String(chArr)
        }?: UNCHECKED
    }

    fun setZero(newValue: Boolean){
        modifyAlarm(0, if(newValue) '1' else '0')
    }
    fun setOne(newValue: Boolean){
        modifyAlarm(1, if(newValue) '1' else '0')
    }
    fun setTwo(newValue: Boolean){
        modifyAlarm(2, if(newValue) '1' else '0')
    }
    fun setThree(newValue: Boolean){
        modifyAlarm(3, if(newValue) '1' else '0')
    }
    fun setFour(newValue: Boolean){
        modifyAlarm(4, if(newValue) '1' else '0')
    }
    fun setFive(newValue: Boolean){
        modifyAlarm(5, if(newValue) '1' else '0')
    }
    fun setSix(newValue: Boolean){
        modifyAlarm(6, if(newValue) '1' else '0')
    }
    //inputFragment_radioGroupWay
    fun setWay(newValue: Boolean){
        modifyAlarm(7, if(newValue) '1' else '0')
    }

    private fun modifyUserData(index: Int, hour:String, min: String){
        _userInfo[index * 2] = hour
        _userInfo[index * 2 + 1] = min

    }

    fun setInfo(index: Int, hour: String, min: String){
        modifyUserData(index, hour, min)
    }





}