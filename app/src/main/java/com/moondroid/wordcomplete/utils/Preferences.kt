package com.moondroid.wordcomplete.utils

import android.content.Context
import android.content.SharedPreferences

object Preferences {
    private lateinit var preferences: SharedPreferences
    private const val PREF_NAME = "redis_preferences"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getBoolean(key: String, defVal: Boolean = false): Boolean {
        return preferences.getBoolean(key, defVal)
    }

    fun putBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun clear() {
        preferences.edit().clear().apply()
    }
}