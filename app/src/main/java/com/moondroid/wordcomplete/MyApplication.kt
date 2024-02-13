package com.moondroid.wordcomplete

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.MobileAds
import com.moondroid.wordcomplete.domain.respository.Repository
import com.moondroid.wordcomplete.utils.NetworkConnection
import com.moondroid.wordcomplete.utils.Preferences
import com.moondroid.wordcomplete.utils.firebase.FBAnalyze
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {
    @Inject
    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        FBAnalyze.init(applicationContext)
        MobileAds.initialize(applicationContext)
        Preferences.init(applicationContext)
        NetworkConnection.init(applicationContext)
        if (!BuildConfig.DEBUG) {
            postMessage()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun postMessage() {
        CoroutineScope(Dispatchers.Main).launch {
            val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
            val prefix = if (Preferences.getBoolean("first_open", true)) {
                Preferences.putBoolean("first_open", false)
                "[${getString(R.string.app_name)} - 신규]"
            } else {
                "[${getString(R.string.app_name)} - 기존]"
            }
            val token = "Bearer ${getString(R.string.slack_token)}"
            repository.postMessage(token, "$prefix\n사용자가 들어왔습니다. $date")
        }
    }
}