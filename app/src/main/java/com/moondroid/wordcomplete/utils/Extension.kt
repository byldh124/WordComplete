package com.moondroid.wordcomplete.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.moondroid.wordcomplete.BuildConfig

object Extension {
    fun View.visible(boolean: Boolean = true) {
        visibility = if (boolean) View.VISIBLE else View.GONE
    }

    fun Any.debug(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d("Moondroid", "[${this.javaClass.simpleName.trim()}] | $msg")
        }
    }

    fun String.shuffle(): String {
        val list = this.toByteArray()
        list.shuffle()
        return String(list)
    }

    @SuppressLint("DiscouragedApi")
    fun Context.getDrawableId(name: String): Int {
        return try {
            resources.getIdentifier(name, "drawable", packageName)
        } catch (e: Exception) {
            0
        }
    }

    fun TextView.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    @Suppress("Unused")
    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Convert Dp -> Px
     */
    fun dpToPixel(context: Context, dp: Int): Int {
        var pixel = 0.0f
        try {
            pixel = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                context.resources.displayMetrics
            )
        } catch (ignore: Exception) {

        }

        return pixel.toInt()
    }
}