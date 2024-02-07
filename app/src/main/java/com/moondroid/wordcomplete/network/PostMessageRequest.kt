package com.moondroid.wordcomplete.network

data class PostMessageRequest(
    private val channel: String,
    private val text: String
)
