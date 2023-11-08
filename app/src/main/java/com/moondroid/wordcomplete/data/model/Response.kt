package com.moondroid.wordcomplete.data.model

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("message")
    val msg: String,
    @SerializedName("code")
    val code: Int
)

data class ItemResponse(
    @SerializedName("result")
    val body: List<Item>,
    @SerializedName("message")
    val msg: String,
    @SerializedName("code")
    val code: Int
)