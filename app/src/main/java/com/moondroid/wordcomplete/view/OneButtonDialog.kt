package com.moondroid.wordcomplete.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.moondroid.wordcomplete.R
import com.moondroid.wordcomplete.databinding.DialogOneButtonBinding

class OneButtonDialog(context: Context, var msg: String, var onClick: () -> Unit) :
    Dialog(context, R.style.DialogTheme) {

    lateinit var binding: DialogOneButtonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogOneButtonBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)
        binding.btnConfirm.setOnClickListener {
            cancel()
        }
    }

    override fun show() {
        super.show()
        binding.tvMessage.text = msg
    }

    override fun cancel() {
        super.cancel()
        onClick()
    }
}