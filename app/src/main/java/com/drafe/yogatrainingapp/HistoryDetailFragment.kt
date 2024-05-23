package com.drafe.yogatrainingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.drafe.yogatrainingapp.databinding.HistoryDetailFragmentBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class HistoryDetailViewModel(historyId: UUID): ViewModel() {
    private val historyRepository = YogaRepository.get()

    private val _history: MutableStateFlow<TrainHistory?> = MutableStateFlow(null)
    val history: StateFlow<TrainHistory?> = _history.asStateFlow()

    init {
        viewModelScope.launch {
            _history.value = historyRepository.getTrainHistoryById(historyId)
        }
    }

    override fun onCleared() {
        super.onCleared()
//        history.value?.let { crimeRepository.updateCrime(it) }
    }
}

class HistoryDetailViewModelFactory(
    private val historyId: UUID
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HistoryDetailViewModel(historyId) as T
    }
}
class HistoryDetailFragment: Fragment() {
    private val args: HistoryDetailFragmentArgs by navArgs()

    private var _binding: HistoryDetailFragmentBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val historyDetailViewModel: HistoryDetailViewModel by viewModels {
        HistoryDetailViewModelFactory(args.historyId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("HistoryDetailFragment", "History ID is ${args.historyId}")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HistoryDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val history = historyDetailViewModel.history.value

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                historyDetailViewModel.history.collect { history ->
                    history?.let { updateUi(it) }
                }
            }
        }
    }

    private fun updateUi(history: TrainHistory) {
        binding.apply {
            asanName.text = history.asanName

            comment.text = history.comment
        }
    }
}
