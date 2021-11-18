package com.qihuan.wanandroid.common.di

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.qihuan.wanandroid.BuildConfig
import com.qihuan.wanandroid.common.net.interceptor.HttpLoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * NetworkModule
 * @author qi
 * @since 2020/7/16
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun cookieJar(@ApplicationContext context: Context): CookieJar {
        return PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))
    }

    @Singleton
    @Provides
    fun client(cookieJar: CookieJar): OkHttpClient {
        with(OkHttpClient.Builder()) {
            if (BuildConfig.DEBUG) {
                addInterceptor(loggingInterceptor())
            }
            readTimeout(30, TimeUnit.SECONDS)
            connectTimeout(30, TimeUnit.SECONDS)
            cookieJar(cookieJar)
            return this.build()
        }
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
    }
}