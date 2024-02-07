package com.moondroid.wordcomplete.data.api

import com.moondroid.wordcomplete.data.model.response.BaseResponse
import com.moondroid.wordcomplete.data.model.response.ItemResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApiService {
    @GET("checkVersion.php")
    suspend fun checkAppVersion(
        @Query("versionCode") versionCode: Int,
        @Query("versionName") versionName: String,
        @Query("packageName") packageName: String,
    ): Response<BaseResponse>

    @GET("items.php")
    suspend fun getItems(): Response<ItemResponse>
}