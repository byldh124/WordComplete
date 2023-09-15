package com.moondroid.wordcomplete.utils.firebase

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

object FBAnalyze {
    private lateinit var analytics : FirebaseAnalytics

    fun init(context: Context) {
        analytics = FirebaseAnalytics.getInstance(context)
    }

    fun logEvent(event: String) {
        if (this::analytics.isInitialized) {
            analytics.logEvent(event, null)
        }
    }

    fun logEvent(event: String, params: Bundle) {
        if (this::analytics.isInitialized) {
            analytics.logEvent(event, params)
        }
    }
}