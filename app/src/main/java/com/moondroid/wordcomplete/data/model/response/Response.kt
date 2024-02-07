package com.moondroid.wordcomplete.data.model.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.moondroid.wordcomplete.domain.model.Item

@Keep
data class BaseResponse(
    @SerializedName("message")
    val msg: String,
    @SerializedName("code")
    val code: Int
)

@Keep
data class ItemResponse(
    @SerializedName("result")
    val body: List<Item>,
    @SerializedName("message")
    val msg: String,
    @SerializedName("code")
    val code: Int
)