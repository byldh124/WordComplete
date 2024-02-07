package com.moondroid.wordcomplete.domain.respository

import com.moondroid.wordcomplete.domain.model.ApiResult
import com.moondroid.wordcomplete.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun checkAppVersion(
        versionCode: Int,
        versionName: String,
        packageName: String,
    ): Flow<ApiResult<Int>>

    suspend fun getItem(
    ): Flow<ApiResult<List<Item>>>

    suspend fun postMessage(token: String, message: String)
}