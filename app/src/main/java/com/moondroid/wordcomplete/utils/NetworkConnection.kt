package com.moondroid.wordcomplete.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import kotlin.math.acosh

object NetworkConnection: LiveData<Boolean>() {
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    fun init(context: Context) {
        connectivityManager = context.getSystemService(ConnectivityManager::class.java)
    }

    override fun onActive() {
        super.onActive()
        postValue(updateConnection())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(connectivityManagerCallback())
        }
    }

    private fun updateConnection(): Boolean {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val caps = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return when {
            caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private fun connectivityManagerCallback(): ConnectivityManager.NetworkCallback {
        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onUnavailable() {
                super.onUnavailable()
                postValue(false)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                postValue(false)
            }

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                postValue(true)
            }
        }
        return networkCallback
    }
}