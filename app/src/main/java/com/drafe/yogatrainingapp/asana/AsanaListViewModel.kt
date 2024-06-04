package com.drafe.yogatrainingapp.asana

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drafe.yogatrainingapp.YogaRepository
import kotlinx.coroutines.launch
import java.util.UUID

class AsanaListViewModel:ViewModel() {
    private val asanaRepository = YogaRepository.get()

    val asanaList = asanaRepository.getAsanas()

    init {
        viewModelScope.launch {}
    }

    fun getAsana(id: UUID){
        Log.d("AsanaListViewModel", "$id")
        viewModelScope.launch {
//            val en = asanaRepository.getAsanaById(id)
//            Log.d("AsanaListViewModel", "$en")
//            if (en != null) {Log.d("AsanaListViewModel", "${en.id}, ${en.nameEng}")}
        }
    }


}
