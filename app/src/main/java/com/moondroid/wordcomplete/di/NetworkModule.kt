package com.moondroid.wordcomplete.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.moondroid.wordcomplete.BuildConfig
import com.moondroid.wordcomplete.data.api.MyApiService
import com.moondroid.wordcomplete.data.api.SlackApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val MY_URL = "http://moondroid.dothome.co.kr/wordcomplete/"
    private const val SLACK_URL = "https://slack.com/api/"

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = if (true) {
        val loggingInterceptor = HttpLoggingInterceptor {
            val log = try {
                JSONObject(it).toString()
            } catch (e: JSONException) {
                //URLDecoder.decode(it, "UTF-8")
                it
            }
            Log.d("HttpClient", log)
        }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().setLenient().create())
    }

    @Singleton
    @Provides
    fun provideMyApiService(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): MyApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(MY_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
        return retrofit.create(MyApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideSlackApiService(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): SlackApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(SLACK_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
        return retrofit.create(SlackApiService::class.java)
    }
}