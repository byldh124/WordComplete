package com.moondroid.wordcomplete.network

import com.moondroid.wordcomplete.data.model.BaseResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitExService {
    @GET("app/checkVersion.php")
    fun checkAppVersion(
        @Query("versionCode") versionCode: Int,
        @Query("versionName") versionName: String,
        @Query("packageName") packageName: String,
    ): Call<BaseResponse>
}