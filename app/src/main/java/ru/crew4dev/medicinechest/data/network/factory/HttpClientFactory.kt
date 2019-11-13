package ru.crew4dev.medicinechest.data.network.factory

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.crew4dev.medicinechest.BuildConfig
import ru.crew4dev.medicinechest.data.network.error.ErrorInterceptor
import java.util.concurrent.TimeUnit

object HttpClientFactory {

    private const val CONNECT_TIMEOUT_SECONDS = 300L
    private const val READ_TIMEOUT_SECONDS = 300L
    private const val WRITE_TIMEOUT_SECONDS = 300L
    private const val USER_AGENT = "Android"

    fun okHttpClient(builder: OkHttpClient.Builder.() -> Unit = {}): OkHttpClient {
        return clientBuilder()
            .apply {
                builder(this)
                addInterceptor(getUserAgentInterceptor())
                addInterceptor(getLoggingInterceptor())
                addInterceptor(ErrorInterceptor())
            }
            .build()
    }

    private fun clientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        }
    }

    private fun getUserAgentInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestWithUserAgent = originalRequest.newBuilder()
                .header("User-Agent", USER_AGENT)
                .build()
            chain.proceed(requestWithUserAgent)
        }
    }

    private fun getLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}