package com.drafe.yogatrainingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.drafe.yogatrainingapp.databinding.TrainingDurationFragmentBinding
import java.util.UUID

class TrainingDurationFragment: Fragment() {
    private val args: TrainingDurationFragmentArgs by navArgs()
    private var _binding: TrainingDurationFragmentBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TrainingDurationFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         Log.d("TrainingDurationFragment", "Asana ID is ${args.asanaId}")

        binding.trainingDurationPicker.minValue = 10
        binding.trainingDurationPicker.maxValue = 120
        binding.trainingDurationPicker.value = 30

        binding.startTrainingButton.setOnClickListener {
            val duration: Int = binding.trainingDurationPicker.value
            findNavController().navigate(
                TrainingDurationFragmentDirections.showCamera(args.asanaId, duration)
            )
        }
    }
}

