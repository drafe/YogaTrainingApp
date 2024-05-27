package com.drafe.yogatrainingapp

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.Manifest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.drafe.yogatrainingapp.databinding.FragmentFirstAktBinding

class FirstAktFragment: Fragment() {

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                Log.d("FirstScreenFragment", "Camera permission granted")
            } else {
                Log.d("FirstScreenFragment", "Camera permission denied")
            }
        }

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
        checkCameraPermission()
        return binding.root
    }
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // Using the new permission request API
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            newTrainBtn.setOnClickListener {
                Log.d("FirstScreenFragment", "newTrainBtn clicked")
                findNavController().navigate(R.id.show_Asana_list)

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