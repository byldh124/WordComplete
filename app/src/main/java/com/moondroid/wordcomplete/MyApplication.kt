package com.moondroid.wordcomplete

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.moondroid.wordcomplete.utils.ItemHelper
import com.moondroid.wordcomplete.utils.firebase.FBAnalyze

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        FBAnalyze.init(applicationContext)
    }
}