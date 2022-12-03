package com.example.rts_fragment

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.activityViewModels
import com.example.rts_fragment.databinding.FragmentInputBinding
import com.example.rts_fragment.viewmodel.TraidDataViewModel
import java.sql.Timestamp
import java.util.*

class InputFragment : Fragment() {

    var binding : FragmentInputBinding? = null
    val viewModel: TraidDataViewModel by activityViewModels()

    fun getTime(day: String){
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
            getTime("0_mon")
        }
        binding?.btnTue?.setOnClickListener {
            getTime("1_tue")
        }
        binding?.btnWed?.setOnClickListener {
            getTime("2_wed")
        }
        binding?.btnThu?.setOnClickListener {
            getTime("3_thu")
        }
        binding?.btnFri?.setOnClickListener {
            getTime("4_fri")
        }
        binding?.btnSat?.setOnClickListener {
            getTime("5_sat")
        }
        binding?.btnSun?.setOnClickListener {
            getTime("6_sun")
        }

        //Alarm_On/Off_represent
        viewModel.alarm.observe(viewLifecycleOwner){
            binding?.chkMon?.isChecked = viewModel.isZero == true
            binding?.chkTue?.isChecked = viewModel.isOne == true
            binding?.chkWen?.isChecked = viewModel.isTwo == true
            binding?.chkTur?.isChecked = viewModel.isThree == true
            binding?.chkFri?.isChecked = viewModel.isFour == true
            binding?.chkSat?.isChecked = viewModel.isFive == true
            binding?.chkSun?.isChecked = viewModel.isSix == true
            binding?.radioButtonWayS?.isChecked = viewModel.isWay == true
            binding?.radioButtonWayG?.isChecked = viewModel.isWay == false


        }

        //Alarm_On/Off_Set
        binding?.chkMon?.setOnClickListener {
            viewModel.setAlarmChk(binding?.chkMon?.isChecked?: false, 0)
            Log.d("data", viewModel.getUserTime(2).toString())
        }
        binding?.chkTue?.setOnClickListener {
            viewModel.setAlarmChk(binding?.chkTue?.isChecked?: false, 1)
        }
        binding?.chkWen?.setOnClickListener {
            viewModel.setAlarmChk(binding?.chkWen?.isChecked?: false, 2)
        }
        binding?.chkTur?.setOnClickListener {
            viewModel.setAlarmChk(binding?.chkTur?.isChecked?: false, 3)
        }
        binding?.chkFri?.setOnClickListener {
            viewModel.setAlarmChk(binding?.chkFri?.isChecked?: false, 4)
        }
        binding?.chkSat?.setOnClickListener {
            viewModel.setAlarmChk(binding?.chkSat?.isChecked?: false, 5)
        }
        binding?.chkSun?.setOnClickListener {
            viewModel.setAlarmChk(binding?.chkSun?.isChecked?: false, 6)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}