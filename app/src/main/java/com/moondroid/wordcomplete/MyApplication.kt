package com.moondroid.wordcomplete

import android.annotation.SuppressLint
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.gms.ads.MobileAds
import com.moondroid.wordcomplete.network.MyRetrofit
import com.moondroid.wordcomplete.network.PostMessageRequest
import com.moondroid.wordcomplete.network.SlackApiService
import com.moondroid.wordcomplete.utils.Extension.debug
import com.moondroid.wordcomplete.utils.Preferences
import com.moondroid.wordcomplete.utils.firebase.FBAnalyze
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        FBAnalyze.init(applicationContext)
        //MobileAds.initialize(applicationContext)
        Preferences.init(applicationContext)
        //if (!BuildConfig.DEBUG) {
            postMessage()
        //}
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
            val postMessageRequest =
                PostMessageRequest("C06H55PNMSN", "$prefix\n사용자가 들어왔습니다. $date")
            val service = MyRetrofit.slack.create(SlackApiService::class.java)
            withContext(Dispatchers.IO) {
                service.postMessage(token, postMessageRequest)
            }
        }
    }
}