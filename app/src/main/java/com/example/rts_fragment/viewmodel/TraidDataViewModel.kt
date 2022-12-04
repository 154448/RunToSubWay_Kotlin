package com.example.rts_fragment.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rts_fragment.loadTimeInfo
import com.example.rts_fragment.repository.UserDataRepository
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Calendar

val UNCHECKED = mutableListOf<Boolean>(false, false, false, false, false, false, false)
val UNCKYWAY = false
val UNUPDATED = mutableListOf<Boolean>(false, false, false, false, false, false, false)
val NOTRAINDATA = mutableListOf<String>("일산행", "9:00", "문산행", "9:00", "청량리행", "9:10", "서울역행", "12:00")

enum class WeekDay (val path: String) {
    MONDAY("0_mon"), TUESDAY("1_tue"), WEDNESDAY("2_wed"), THURSDAY("3_thu"),
    FRIDAY("4_fri"), SATURDAY("5_sat"), SUNDAY("6_sun")
}

class TraidDataViewModel: ViewModel() {
    //AlarmData
    private val _alarm = MutableLiveData<MutableList<Boolean>>( UNCHECKED )
    val alarm: LiveData<MutableList<Boolean>> get() = _alarm

    //열차방향 True: Dmc방면, false: 행신방면
    private val _way = MutableLiveData<Boolean>(UNCKYWAY)
    val way: LiveData<Boolean> get() = _way

    //요일별 사용자의 시간 입력 여부: 초기값 false, timePicker로 시간 입력시 True로 변환
    private val _modify = MutableLiveData<MutableList<Boolean>>( UNUPDATED )
    val modify: LiveData<MutableList<Boolean>> get() = _modify

    //사용자의 입력시간 저장
    private val _userTime = MutableLiveData<MutableList<com.google.firebase.Timestamp>>()
    val userTime: LiveData<MutableList<com.google.firebase.Timestamp>>  get() = _userTime

    //사용자가 데이터를 바꿀때마다 실시간으로 관찰해서 앱에 저장
    private val repository = UserDataRepository()
    init{
        repository.getInfo(_alarm, _modify, _way, _userTime)
    }
    //열차정보를 저장하는 배열: UI에서 열차정보를 제공하는 것은 이것을 통해 함. 여기에 저장하시면 됩니다.
    private val _trainInfo = MutableLiveData<MutableList<String>>( NOTRAINDATA )
    val trainInfo: LiveData<MutableList<String>> get() = _trainInfo

    fun updateTrainInfo(){
        loadTimeInfo(_trainInfo)
    }

    //사용자 수정여부 가져오기
    fun getModify(idx: Int) = modify.value?.get(idx)

    //사용자 입력시간 가져오기
    fun getUserTime(idx: Int) = userTime.value?.get(idx)

    val k = getUserTime(Calendar.DAY_OF_WEEK)

    //사용자 알림 활성화 여부 가져오기
    fun getAlarmChk(idx: Int) = alarm.value?.get(idx)

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
    val isWay get() = way.value


    fun setAlarmChk(newValue: Boolean, idx: Int){
        val weekDays = WeekDay.values()
        val doc = weekDays[(idx) % weekDays.size].path
        repository.updateAlarmChk(doc, newValue)
    }

    //inputFragment_radioGroupWay
    fun setWay(newValue: Boolean){
        repository.updateWay(newValue)
    }

    fun setTime(day: String, time: Timestamp){
        repository.updateTime(day, time)
    }
}