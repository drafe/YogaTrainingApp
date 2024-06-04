package com.drafe.yogatrainingapp.history

import android.app.Application
import android.util.Log
import com.drafe.yogatrainingapp.YogaRepository

class TrainHistoryIntentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("TrainApp", "Repo init")
        YogaRepository.initialize(this)
    }
}
