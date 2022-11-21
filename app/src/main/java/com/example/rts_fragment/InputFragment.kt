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
import java.util.Calendar

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
        }
    }

    fun getTime(index: Int){
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            viewModel.setInfo(index, hourOfDay.toString(),minute.toString())
            //임시
            binding?.textInputData?.text = viewModel.userInfo[index * 2].toString()
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
            getTime(0)
        }
        binding?.btnTue?.setOnClickListener {
            getTime(1)
        }
        binding?.btnWen?.setOnClickListener {
            getTime(2)
        }
        binding?.btnTur?.setOnClickListener {
            getTime(3)
        }
        binding?.btnFri?.setOnClickListener {
            getTime(4)
        }
        binding?.btnSat?.setOnClickListener {
            getTime(5)
        }
        binding?.btnSun?.setOnClickListener {
            getTime(6)
        }

        //Alarm_On/Off_represent
        viewModel.alarm.observe(viewLifecycleOwner){
            binding?.chkMon?.isChecked = viewModel.isZero
            binding?.chkTue?.isChecked = viewModel.isOne
            binding?.chkWen?.isChecked = viewModel.isTwo
            binding?.chkTur?.isChecked = viewModel.isThree
            binding?.chkFri?.isChecked = viewModel.isFour
            binding?.chkSat?.isChecked = viewModel.isFive
            binding?.chkSun?.isChecked = viewModel.isSix
            binding?.radioButtonWayS?.isChecked = viewModel.isWay
            binding?.radioButtonWayG?.isChecked = !(viewModel.isWay)

            //임시
            binding?.textInputData?.text = viewModel.alarm.value
        }

        //Alarm_On/Off_Set
        binding?.chkMon?.setOnClickListener {
            viewModel.setZero(binding?.chkMon?.isChecked?: false)
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