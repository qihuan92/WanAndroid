package com.qihuan.wanandroid.common.net

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
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
    fun wanService(retrofit: Retrofit): WanService {
        return retrofit.create(WanService::class.java)
    }
}