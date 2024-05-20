package com.drafe.yogatrainingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.drafe.yogatrainingapp.databinding.HistoryListFragmentBinding
import kotlinx.coroutines.launch

class HistoryListFragment:Fragment() {
    private var _binding: HistoryListFragmentBinding? = null

    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val historyViewModel : HistoryListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        _binding = HistoryListFragmentBinding.inflate(inflater, container, false)
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                historyViewModel.historyList.collect { historyList ->
                    binding.historyRecyclerView.adapter =
                        HistoryListAdapter(historyList) {historyId ->
                            findNavController().navigate(
//                                R.id.show_TrainHistory_detail
                                HistoryListFragmentDirections.showTrainHistoryDetail(historyId)
                            )
                        }
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}