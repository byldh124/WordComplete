package com.moondroid.wordcomplete.data.model

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("result")
    val body: JsonElement,
    @SerializedName("message")
    val msg: String,
    @SerializedName("code")
    val code: Int
)