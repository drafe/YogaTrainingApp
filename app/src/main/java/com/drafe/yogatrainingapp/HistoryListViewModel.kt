package com.drafe.yogatrainingapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class HistoryListViewModel: ViewModel() {
    private val historyRepository = TrainHistoryRepository.get()

    val historyList = historyRepository.getTrainHistory()

    init {
        viewModelScope.launch {

        }
    }
}