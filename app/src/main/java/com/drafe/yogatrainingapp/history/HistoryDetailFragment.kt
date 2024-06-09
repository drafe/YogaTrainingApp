package com.drafe.yogatrainingapp.history

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Html
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
import com.drafe.yogatrainingapp.TrainHistoryWithAsana
import com.drafe.yogatrainingapp.YogaRepository
import com.drafe.yogatrainingapp.asana.Asana
import com.drafe.yogatrainingapp.databinding.HistoryDetailFragmentBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID

class HistoryDetailViewModel(historyId: UUID): ViewModel() {
    private val historyRepository = YogaRepository.get()

    private val _history: MutableStateFlow<TrainHistoryWithAsana?> = MutableStateFlow(null)
    val history: StateFlow<TrainHistoryWithAsana?> = _history.asStateFlow()


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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                historyDetailViewModel.history.collect { history ->
                    history?.let { updateUi(it) }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUi(history: TrainHistoryWithAsana) {
        binding.apply {
            asanName.text = history.asanaName
            trainScore.text = history.score.toString()+"%"
            category.text = history.category
            when(history.difficulty) {
                1 -> difficulty.text = "Beginner"
                2 -> difficulty.text = "Intermediate"
                3 -> difficulty.text = "Advanced"
                else -> difficulty.text = "Unknown"
            }
            // Загрузка иконки асаны из assets
            try {
                val assetManager = requireContext().assets
                val inputStream = assetManager.open("poses/${history.imgName}")
                val drawable = Drawable.createFromStream(inputStream, null)
                imageView.setImageDrawable(drawable)
            } catch (e: IOException) {
                e.printStackTrace()
                // Обработайте ошибку (например, установите изображение по умолчанию)
            }

            val htmlText = history.expertCom.trimIndent()
            textView.text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT)

        }
    }
}
