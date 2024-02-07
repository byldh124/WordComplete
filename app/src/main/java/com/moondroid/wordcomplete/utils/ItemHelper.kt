package com.moondroid.wordcomplete.utils

import com.moondroid.wordcomplete.domain.model.Item

object ItemHelper {
    var items: List<Item> = emptyList()
    fun shuffle() {
        items = items.shuffled()
    }
}