package com.qihuan.wanandroid.common.di

import com.drakeet.multitype.MultiTypeAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * AdapterModule
 * @author qi
 * @since 2020/7/21
 */
@Module
@InstallIn(ActivityComponent::class)
class AdapterModule {

    @Provides
    fun provideMultiTypeAdapter(): MultiTypeAdapter {
        return MultiTypeAdapter()
    }
}