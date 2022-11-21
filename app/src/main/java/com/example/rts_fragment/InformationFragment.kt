package com.example.rts_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TwoLineListItem
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import com.example.rts_fragment.databinding.FragmentInformationBinding
import com.example.rts_fragment.viewmodel.TraidDataViewModel

class InformationFragment : Fragment() {
    val viewModel: TraidDataViewModel by activityViewModels()
    var binding : FragmentInformationBinding?= null

    fun updateWay(){
        viewModel.timeOne.observe(viewLifecycleOwner){
            binding?.textWayGFirst?.text = viewModel.wayOne.value
            binding?.textWayGSecond?.text = viewModel.wayTwo.value
            binding?.textWaySFirst?.text = viewModel.wayThree.value
            binding?.textWaySSecond?.text = viewModel.wayFour.value
        }
    }
    fun updateTime(){
        viewModel.timeOne.observe(viewLifecycleOwner){
            binding?.textTimeGFirst?.text = viewModel.timeOne.value
            binding?.textTimeGSecond?.text = viewModel.timeTwo.value
            binding?.textTimeSFirst?.text = viewModel.timeThree.value
            binding?.textTimeSSecond?.text = viewModel.timeFour.value
        }
    }

    fun updateUi(){
        updateTime()
        updateWay()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInformationBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUi()

        binding?.btnUpdate?.setOnClickListener{
            updateUi()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}