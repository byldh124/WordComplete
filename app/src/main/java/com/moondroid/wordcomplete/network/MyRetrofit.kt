package com.moondroid.wordcomplete.network

import com.moondroid.wordcomplete.utils.Extension.debug
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

object MyRetrofit {

    private var okHttpClient: OkHttpClient? = null

    val retrofit: Retrofit
        get() {
            return Retrofit.Builder()
                .baseUrl("http://moondroid.dothome.co.kr/damoim/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build()
        }

    private fun initUnsafeOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.connectTimeout(60, TimeUnit.SECONDS)
        client.readTimeout(60, TimeUnit.SECONDS)
        client.writeTimeout(60, TimeUnit.SECONDS)
        // 참고: addInterceptor 사용하기 위해서 gradle 파일에 compileOptions, kotlinOptions 추가해 주어야 함
        return client.addInterceptor {
            val original = it.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            val request = requestBuilder.method(original.method, original.body).build()
            debug(request.toString())
            return@addInterceptor it.proceed(request)
        }.build()
    }

    private fun getUnsafeOkHttpClient(): OkHttpClient {
        if (okHttpClient == null) {
            okHttpClient = initUnsafeOkHttpClient()
        }
        return okHttpClient!!
    }
}

class NullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val delegate: Converter<ResponseBody, *> =
            retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return Converter { body ->
            if (body.contentLength() == 0L) {
                null
            } else delegate.convert(body)
        }
    }
}