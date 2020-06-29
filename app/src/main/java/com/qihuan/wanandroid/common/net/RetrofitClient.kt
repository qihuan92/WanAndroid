package com.qihuan.wanandroid.common.net

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.qihuan.wanandroid.App
import com.qihuan.wanandroid.BuildConfig
import com.qihuan.wanandroid.common.net.interceptor.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * RetrofitClient
 * @author qi
 * @since 2020/6/28
 */
class RetrofitClient private constructor() {
    private val retrofit: Retrofit
    private val cookieJar by lazy {
        PersistentCookieJar(
            SetCookieCache(),
            SharedPrefsCookiePersistor(App.context)
        )
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(APP_DEFAULT_DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client().build())
            .build()
    }

    fun <T> getApi(t: Class<T>): T {
        return retrofit.create(t)
    }

    private fun client(): OkHttpClient.Builder {
        with(OkHttpClient.Builder()) {
            if (BuildConfig.DEBUG) {
                addInterceptor(loggingInterceptor())
            }
            readTimeout(30, TimeUnit.SECONDS)
            connectTimeout(30, TimeUnit.SECONDS)
            cookieJar(cookieJar)
            return this
        }
    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    companion object {
        private const val APP_DEFAULT_DOMAIN = "https://www.wanandroid.com"
        val instance: RetrofitClient by lazy { RetrofitClient() }
    }
}