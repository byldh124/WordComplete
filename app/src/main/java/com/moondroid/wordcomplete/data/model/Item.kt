package com.moondroid.wordcomplete.data.model

data class Item(
    val eng: String,
    val kor: String,
) {
    fun getImage(): String = "http://moondroid.dothome.co.kr/wordcomplete/image/${eng}.png"
}