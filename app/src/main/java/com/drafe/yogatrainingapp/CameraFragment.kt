package com.drafe.yogatrainingapp

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.drafe.yogatrainingapp.databinding.CameraFragmentBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID


class CameraFragment: Fragment() {
    private val args: CameraFragmentArgs by navArgs()
    private var _binding: CameraFragmentBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val cameraViewModel: CameraViewModel by viewModels {
        CameraViewModelFactory(args.asanaId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CameraFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.timerCircle.max = args.duration

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cameraViewModel.asana.collect { asana ->
                    asana?.let { updateUi(it) }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cameraViewModel.timerValue.collect { timerValue ->
                    updateTimer(timerValue)
                }
            }
        }

        // Start the timer
        cameraViewModel.startTimer(args.duration)
    }

    private fun updateTimer(timerValue: Int) {
        binding.timerCircle.progress = timerValue
    }

    private fun updateUi(asana: Asana) {
        binding.asanaName.text = asana.nameEng
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class CameraViewModelFactory(private val asanaId: UUID) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass:Class<T>): T{
        return CameraViewModel(asanaId) as T
    }

}


class CameraViewModel(asanaId: UUID): ViewModel() {
    private val asanaRepository = YogaRepository.get()

    private val _asana: MutableStateFlow<Asana?> = MutableStateFlow(null)
    val asana: StateFlow<Asana?> = _asana.asStateFlow()

    private val _timerValue = MutableStateFlow(0)
    val timerValue: StateFlow<Int> = _timerValue.asStateFlow()
    private var timerStarted: Boolean = false

    init {
        viewModelScope.launch {
            _asana.value = asanaRepository.getAsanaById(asanaId)
        }
    }

    fun startTimer(duration: Int) {
        if (timerStarted) return

        // Start the timer
        timerStarted = true
        viewModelScope.launch {
            for (i in duration downTo 0) {
                _timerValue.value = i
                delay(1000L) // delay for 1 second
            }
        }
    }
}

