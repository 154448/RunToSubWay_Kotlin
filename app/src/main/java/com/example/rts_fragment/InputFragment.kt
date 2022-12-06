package com.example.rts_fragment

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.work.*
import com.example.rts_fragment.databinding.FragmentInputBinding
import com.example.rts_fragment.viewmodel.TraidDataViewModel
import com.example.rts_fragment.viewmodel.WeekDay
import java.sql.Timestamp
import java.util.*
import java.util.concurrent.TimeUnit

class InputFragment : Fragment() {
    var binding : FragmentInputBinding? = null
    val viewModel: TraidDataViewModel by activityViewModels()

    fun getTime(day: WeekDay){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.HOUR, hourOfDay)
            cal.set(Calendar.MINUTE, minute)
            val k = Timestamp(cal.timeInMillis)
            viewModel.setTime(day, k)
        }
        TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.trainInfo.observe(viewLifecycleOwner){
            binding?.textWayFirst?.text = viewModel.getTrainWay(0)
            binding?.textWaySecond?.text = viewModel.getTrainWay(1)
            binding?.textWayThird?.text = viewModel.getTrainWay(2)
            binding?.textWayFourth?.text = viewModel.getTrainWay(3)
            binding?.textTimeFirst?.text = viewModel.getTrainTime(0)
            binding?.textTimeSecond?.text = viewModel.getTrainTime(1)
            binding?.textTimeThird?.text = viewModel.getTrainTime(2)
            binding?.textTimeFourth?.text = viewModel.getTrainTime(0)
        }

        binding?.btnMon?.setOnClickListener {
            getTime(WeekDay.MONDAY)
        }
        binding?.btnTue?.setOnClickListener {
            getTime(WeekDay.TUESDAY)
        }
        binding?.btnWed?.setOnClickListener {
            getTime(WeekDay.WEDNESDAY)
        }
        binding?.btnThu?.setOnClickListener {
            getTime(WeekDay.THURSDAY)
        }
        binding?.btnFri?.setOnClickListener {
            getTime(WeekDay.FRIDAY)
        }
        binding?.btnSat?.setOnClickListener {
            getTime(WeekDay.SATURDAY)
        }
        binding?.btnSun?.setOnClickListener {
            getTime(WeekDay.SUNDAY)
        }

        //Alarm_On/Off_represent
        viewModel.alarm.observe(viewLifecycleOwner){
            binding?.chkMon?.isChecked = viewModel.getAlarmChk(WeekDay.MONDAY) == true
            binding?.chkTue?.isChecked = viewModel.getAlarmChk(WeekDay.TUESDAY) == true
            binding?.chkWen?.isChecked = viewModel.getAlarmChk(WeekDay.WEDNESDAY) == true
            binding?.chkTur?.isChecked = viewModel.getAlarmChk(WeekDay.THURSDAY) == true
            binding?.chkFri?.isChecked = viewModel.getAlarmChk(WeekDay.FRIDAY) == true
            binding?.chkSat?.isChecked = viewModel.getAlarmChk(WeekDay.SATURDAY) == true
            binding?.chkSun?.isChecked = viewModel.getAlarmChk(WeekDay.SUNDAY) == true
            binding?.radioButtonWayS?.isChecked = viewModel.isWay == true
            binding?.radioButtonWayG?.isChecked = viewModel.isWay == false


        }

        //Alarm_On/Off_Set
        binding?.chkMon?.setOnClickListener {
            viewModel.setAlarmChk(binding?.chkMon?.isChecked?: false, WeekDay.MONDAY)
        }
        binding?.chkTue?.setOnClickListener {
            viewModel.setAlarmChk(binding?.chkTue?.isChecked?: false, WeekDay.TUESDAY)
        }
        binding?.chkWen?.setOnClickListener {
            viewModel.setAlarmChk(binding?.chkWen?.isChecked?: false, WeekDay.WEDNESDAY)
        }
        binding?.chkTur?.setOnClickListener {
            viewModel.setAlarmChk(binding?.chkTur?.isChecked?: false, WeekDay.THURSDAY)
        }
        binding?.chkFri?.setOnClickListener {
            viewModel.setAlarmChk(binding?.chkFri?.isChecked?: false, WeekDay.FRIDAY)
        }
        binding?.chkSat?.setOnClickListener {
            viewModel.setAlarmChk(binding?.chkSat?.isChecked?: false, WeekDay.SATURDAY)
        }
        binding?.chkSun?.setOnClickListener {
            viewModel.setAlarmChk(binding?.chkSun?.isChecked?: false, WeekDay.SUNDAY)
        }
        //열차방면 설정 0이면 일산/ 1이면 서울
        binding?.radioGroupWay?.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radioButton_wayG ->{
                    viewModel.setWay(false)
            }
                R.id.radioButton_wayS->{
                    viewModel.setWay(true)
                }
            }
        }
        binding?.btnFinished?.setOnClickListener {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build()

            val cTime = Calendar.getInstance()
            val delayTime=24-cTime.get(Calendar.HOUR).toLong()


            val workRequest = PeriodicWorkRequest.Builder(AlarmWorker::class.java,1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .setInitialDelay(delayTime,TimeUnit.HOURS)
                .build()

            val tempWorkRequest = OneTimeWorkRequest.from(AlarmWorker::class.java)

            WorkManager.getInstance().enqueueUniqueWork("one", ExistingWorkPolicy.REPLACE,tempWorkRequest)

            WorkManager.getInstance().enqueueUniquePeriodicWork("stable", ExistingPeriodicWorkPolicy.REPLACE,workRequest)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}