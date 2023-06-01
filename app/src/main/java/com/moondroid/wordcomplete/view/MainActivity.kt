package com.moondroid.wordcomplete.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moondroid.wordcomplete.R
import com.moondroid.wordcomplete.data.model.Item

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    var items : ArrayList<Item>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}