package com.moondroid.wordcomplete.view.base

import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moondroid.wordcomplete.utils.Extension.debug
import com.moondroid.wordcomplete.utils.NetworkConnection
import com.moondroid.wordcomplete.view.dialog.DisconnectNetworkDialog

open class BaseActivity : AppCompatActivity() {
    protected val mContext by lazy { this }
    private val disconnectNetworkDialog by lazy { DisconnectNetworkDialog(mContext) }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, 0, 0)
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(0, 0)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetworkConnection.observe(this) {
            disconnectNetworkDialog.isShow = !it
        }
    }
}