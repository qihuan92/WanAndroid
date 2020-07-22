package com.qihuan.wanandroid.common.di

import com.qihuan.wanandroid.common.net.WanService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

/**
 * ServiceModule
 * @author qi
 * @since 2020/7/16
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
class ServiceModule {

    @Provides
    fun wanService(client: OkHttpClient, converterFactory: Converter.Factory): WanService {
        return Retrofit.Builder()
            .baseUrl(WanService.BASE_URL)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
            .create(WanService::class.java)
    }
}