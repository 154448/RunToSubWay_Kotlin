package com.example.rts_fragment

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.activityViewModels
import com.example.rts_fragment.databinding.FragmentInputBinding
import com.example.rts_fragment.viewmodel.TraidDataViewModel
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class InputFragment : Fragment() {

    var binding : FragmentInputBinding? = null
    val viewModel: TraidDataViewModel by activityViewModels()

    fun updateUi(){
        viewModel.wayOne.observe(viewLifecycleOwner){
            binding?.textWayFirst?.text = viewModel.wayOne.value
            binding?.textWaySecond?.text = viewModel.wayTwo.value
            binding?.textWayThird?.text = viewModel.wayThree.value
            binding?.textWayFourth?.text = viewModel.wayFour.value
            binding?.textTimeFirst?.text = viewModel.timeOne.value
            binding?.textTimeSecond?.text = viewModel.timeTwo.value
            binding?.textTimeThird?.text = viewModel.timeThree.value
            binding?.textTimeFourth?.text = viewModel.timeFour.value
            println(viewModel.alarm)
        }
    }

    fun getTime(day: String, index: Int){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.HOUR, hourOfDay)
            cal.set(Calendar.MINUTE, minute)
            val k = Timestamp(cal.timeInMillis)
            viewModel.setTime(day, k)
            val t_dataFormat = SimpleDateFormat("yyyy-MM-dd kk:mm:ss E", Locale("ko", "KR"))
            //임시
            binding?.textInputData?.text = t_dataFormat.format(k).toString()
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
        updateUi()

        binding?.btnMon?.setOnClickListener {
            getTime("mon",0)
        }
        binding?.btnTue?.setOnClickListener {
            getTime("tue", 1)
        }
        binding?.btnWen?.setOnClickListener {
            getTime("wen",2)
        }
        binding?.btnTur?.setOnClickListener {
            getTime("thu",3)
        }
        binding?.btnFri?.setOnClickListener {
            getTime("fri",4)
        }
        binding?.btnSat?.setOnClickListener {
            getTime("sat",5)
        }
        binding?.btnSun?.setOnClickListener {
            getTime("sun",6)
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
            viewModel.setZero(binding?.chkMon?.isChecked?: false)
            binding?.textInputData?.text = binding?.chkMon?.isChecked.toString()
        }
        binding?.chkTue?.setOnClickListener {
            viewModel.setOne(binding?.chkTue?.isChecked?: false)
        }
        binding?.chkWen?.setOnClickListener {
            viewModel.setTwo(binding?.chkWen?.isChecked?: false)
        }
        binding?.chkTur?.setOnClickListener {
            viewModel.setThree(binding?.chkTur?.isChecked?: false)
        }
        binding?.chkFri?.setOnClickListener {
            viewModel.setFour(binding?.chkFri?.isChecked?: false)
        }
        binding?.chkSat?.setOnClickListener {
            viewModel.setFive(binding?.chkSat?.isChecked?: false)
        }
        binding?.chkSun?.setOnClickListener {
            viewModel.setSix(binding?.chkSun?.isChecked?: false)
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