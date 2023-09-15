package com.moondroid.wordcomplete.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moondroid.wordcomplete.data.model.Item
import com.moondroid.wordcomplete.utils.Extension.debug

object ItemHelper {
    private var items: ArrayList<Item>? = null

    fun init(context: Context) {
        debug("item init")
        val assetManager = context.resources.assets
        val inputStream = assetManager.open("word.json")
        val reader = inputStream.bufferedReader()
        items = Gson().fromJson<ArrayList<Item>>(reader, object : TypeToken<ArrayList<Item>>() {}.type)
        debug("items: $items")
    }

    fun getItems(context: Context): ArrayList<Item> {
        return if (items.isNullOrEmpty()) {
            val assetManager = context.resources.assets
            val inputStream = assetManager.open("word.json")
            val reader = inputStream.bufferedReader()
            val result =
                Gson().fromJson<ArrayList<Item>>(reader, object : TypeToken<ArrayList<Item>>() {}.type)
            if (result.isNullOrEmpty()) {
                ArrayList()
            } else {
                items = result
                result
            }
        } else {
            items!!
        }

    }
}