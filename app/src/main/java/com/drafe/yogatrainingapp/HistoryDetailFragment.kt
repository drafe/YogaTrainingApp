package com.drafe.yogatrainingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.drafe.yogatrainingapp.databinding.HistoryDetailFragmentBinding
import java.util.UUID

class HistoryDetailViewModel(historyId: UUID): ViewModel() {

}

class HistoryDetailFragment: Fragment() {
    private lateinit var history: TrainHistory
    private val args: HistoryDetailFragmentArgs by navArgs()

    private var _binding: HistoryDetailFragmentBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        history = TrainHistory()

        Log.d("HistoryDetailFragment", "${args.historyId}")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HistoryDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
