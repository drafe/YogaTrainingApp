package com.drafe.yogatrainingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.drafe.yogatrainingapp.databinding.FragmentFirstAktBinding

class FirstAktFragment: Fragment() {

    private var _binding: FragmentFirstAktBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstAktBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            newTrainBtn.setOnClickListener {
                Log.d("FirstScreenFragment", "newTrainBtn clicked")

            }
            seeHistoryBtn.setOnClickListener {
                Log.d("FirstScreenFragment", "seeHistoryBtn clicked")
                findNavController().navigate(R.id.show_TrainHistory_list)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}