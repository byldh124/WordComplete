package com.moondroid.wordcomplete.view.dialog

import android.content.Context
import android.os.Bundle
import com.moondroid.wordcomplete.databinding.DialogDisconnectNetworkBinding
import com.moondroid.wordcomplete.view.base.BaseDialog

class DisconnectNetworkDialog(context: Context) : BaseDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DialogDisconnectNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}