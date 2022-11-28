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

        viewModel.wayOne.observe(viewLifecycleOwner){
            binding?.textWayGFirst?.text = viewModel.wayOne.value
        }
        viewModel.wayTwo.observe(viewLifecycleOwner){
            binding?.textWayGSecond?.text = viewModel.wayOne.value
        }
        viewModel.wayThree.observe(viewLifecycleOwner){
            binding?.textWaySFirst?.text = viewModel.wayOne.value
        }
        viewModel.wayFour.observe(viewLifecycleOwner){
            binding?.textWaySSecond?.text = viewModel.wayOne.value
        }

        viewModel.timeOne.observe(viewLifecycleOwner){
            binding?.textTimeGFirst?.text = viewModel.wayOne.value
        }
        viewModel.timeTwo.observe(viewLifecycleOwner){
            binding?.textTimeGSecond?.text = viewModel.wayOne.value
        }
        viewModel.timeThree.observe(viewLifecycleOwner){
            binding?.textTimeSFirst?.text = viewModel.wayOne.value
        }
        viewModel.timeFour.observe(viewLifecycleOwner){
            binding?.textTimeSSecond?.text = viewModel.wayOne.value
        }

        binding?.btnUpdate?.setOnClickListener{
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}