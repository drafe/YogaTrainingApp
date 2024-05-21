package com.drafe.yogatrainingapp

import android.util.Log
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

    fun getTrainHistory(id: UUID){
        Log.d("HistoryListViewModel", "$id")
        viewModelScope.launch {
            val en = historyRepository.getTrainHistoryById(id)
            Log.d("HistoryListViewModel", "$en")
            if (en != null) {Log.d("HistoryListViewModel", "${en.id}, ${en.asanName}")}
        }
    }
}