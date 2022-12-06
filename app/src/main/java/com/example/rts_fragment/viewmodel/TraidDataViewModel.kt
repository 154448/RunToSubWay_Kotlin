package com.example.rts_fragment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rts_fragment.LoadTimeInfo
import com.example.rts_fragment.repository.UserDataRepository
import java.sql.Timestamp


val UNCHECKED = mutableListOf<Boolean>(false, false, false, false, false, false, false)
val UNCKYWAY = false
val NOTRAINDATA = mutableListOf<String>("일산행", "9:00", "문산행", "9:00", "청량리행", "9:10", "서울역행", "12:00")

enum class WeekDay (val path: String, val idx: Int) {
    MONDAY("0_mon", 0), TUESDAY("1_tue", 1), WEDNESDAY("2_wed", 2), THURSDAY("3_thu", 3),
    FRIDAY("4_fri", 4), SATURDAY("5_sat", 5), SUNDAY("6_sun", 6)
}

class TraidDataViewModel: ViewModel() {
    //AlarmData
    private val _alarm = MutableLiveData<MutableList<Boolean>>( UNCHECKED )
    val alarm: LiveData<MutableList<Boolean>> get() = _alarm

    //열차방향 True: Dmc방면, false: 행신방면
    private val _way = MutableLiveData<Boolean>(UNCKYWAY)
    val way: LiveData<Boolean> get() = _way

    //사용자가 데이터를 바꿀때마다 실시간으로 관찰해서 앱에 저장
    private val repository = UserDataRepository()
    init{
        repository.getInfo(_alarm, _way)
    }
    //열차정보를 저장하는 배열: UI에서 열차정보를 제공하는 것은 이것을 통해 함. 여기에 저장하시면 됩니다.
    private val _trainInfo = MutableLiveData<MutableList<String>>( NOTRAINDATA )
    val trainInfo: LiveData<MutableList<String>> get() = _trainInfo

    //실시간 경의선 정보(화전역)데이터를 가져오기 위한 객채생성
    private val trainInfoAPI = LoadTimeInfo()

    //열차정보 실시간 업데이트
    fun updateTrainInfo(){
        trainInfoAPI.loadTimeInfo(_trainInfo)
    }

    //사용자 알림 활성화 여부 가져오기
    fun getAlarmChk(day: WeekDay) = alarm.value?.get(day.idx)

    //Train_Information
    fun getTrainWay(index: Int) = trainInfo.value?.get(index * 2)
    fun getTrainTime(index: Int)= trainInfo.value?.get(index * 2 + 1)

    //열차방면
    val isWay get() = way.value

    fun setAlarmChk(newValue: Boolean, day:WeekDay) {
        repository.updateAlarmChk(day.path, newValue)
    }


    //inputFragment_radioGroupWay
    fun setWay(newValue: Boolean){
        repository.updateWay(newValue)
    }

    fun setTime(day: WeekDay, time: Timestamp){
        repository.updateTime(day.path, time)
    }
}