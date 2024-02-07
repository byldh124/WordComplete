package com.moondroid.wordcomplete.data.api

import com.moondroid.wordcomplete.data.model.request.PostMessageRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SlackApiService {
    @POST("chat.postMessage")
    suspend fun postMessage(
        @Header("Authorization") token: String,
        @Body postMessageRequest: PostMessageRequest,
    ): Response<Unit>
}