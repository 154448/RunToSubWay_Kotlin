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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInformationBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateTrainInfo()
        viewModel.trainInfo.observe(viewLifecycleOwner){
            binding?.textWayGFirst?.text = viewModel.getTrainWay(0)
            binding?.textWayGSecond?.text = viewModel.getTrainWay(1)
            binding?.textWaySFirst?.text = viewModel.getTrainWay(2)
            binding?.textWaySSecond?.text = viewModel.getTrainWay(3)
            binding?.textTimeGFirst?.text = viewModel.getTrainTime(0)
            binding?.textTimeGSecond?.text = viewModel.getTrainTime(1)
            binding?.textTimeSFirst?.text = viewModel.getTrainTime(2)
            binding?.textTimeSSecond?.text = viewModel.getTrainTime(3)
        }

        binding?.btnUpdate?.setOnClickListener{
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}