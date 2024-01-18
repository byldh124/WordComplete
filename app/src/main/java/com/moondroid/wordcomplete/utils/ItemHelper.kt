package com.moondroid.wordcomplete.utils

import com.moondroid.wordcomplete.data.model.Item

object ItemHelper {
    var items: List<Item> = emptyList()
    fun shuffle() {
        items = items.shuffled()
    }
}