package com.moondroid.wordcomplete.data.repository

import com.moondroid.wordcomplete.data.api.MyApiService
import com.moondroid.wordcomplete.data.api.SlackApiService
import com.moondroid.wordcomplete.data.model.request.PostMessageRequest
import com.moondroid.wordcomplete.domain.model.ApiResult
import com.moondroid.wordcomplete.domain.model.Item
import com.moondroid.wordcomplete.domain.respository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val mApiService: MyApiService,
    private val slackApiService: SlackApiService,
) : Repository {

    override suspend fun checkAppVersion(
        versionCode: Int,
        versionName: String,
        packageName: String,
    ): Flow<ApiResult<Int>> {
        return flow<ApiResult<Int>> {
            try {
                val response = mApiService.checkAppVersion(versionCode, versionName, packageName)
                if (response.isSuccessful && response.body() != null) {
                    emit(ApiResult.Success(response.body()!!.code))

                } else {
                    emit(ApiResult.Fail(response.message()))
                }
            } catch (e: Exception) {
                emit(ApiResult.Error(e))
            }


        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getItem(): Flow<ApiResult<List<Item>>> {
        return flow {
            try {
                val response = mApiService.getItems()
                if (response.isSuccessful && response.body() != null) {
                    emit(ApiResult.Success(response.body()!!.body))
                } else {
                    emit(ApiResult.Fail(response.message()))
                }
            } catch (e: Exception) {
                emit(ApiResult.Error(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun postMessage(token: String, message: String) {
        slackApiService.postMessage(token, PostMessageRequest("C06H55PNMSN", message))
    }
}