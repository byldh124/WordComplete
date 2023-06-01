package com.moondroid.wordcomplete.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moondroid.wordcomplete.R
import com.moondroid.wordcomplete.data.model.Item
import com.moondroid.wordcomplete.databinding.FragmentSplashBinding
import com.moondroid.wordcomplete.delegate.viewBinding

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val binding by viewBinding(FragmentSplashBinding::bind)
    private lateinit var mContext: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        getItems()
    }

    private fun initView() {

    }

    private fun getItems() {
        val assetManager = resources.assets
        val inputStream = assetManager.open("word.json")
        val reader = inputStream.bufferedReader()
        val result =
            Gson().fromJson<ArrayList<Item>>(reader, object : TypeToken<ArrayList<Item>>() {}.type)

        mContext.items = result
        binding.btnStart.isEnabled = true
    }
}