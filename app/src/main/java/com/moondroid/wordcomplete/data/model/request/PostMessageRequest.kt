package com.moondroid.wordcomplete.data.model.request

import androidx.annotation.Keep

@Keep
data class PostMessageRequest(
    private val channel: String,
    private val text: String
)
