package com.drafe.yogatrainingapp

import android.app.Application
import android.util.Log

class TrainHistoryIntentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("TrainApp", "Repo init")
        TrainHistoryRepository.initialize(this)
    }
}
