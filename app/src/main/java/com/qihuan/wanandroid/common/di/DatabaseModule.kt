package com.qihuan.wanandroid.common.di

import android.content.Context
import com.qihuan.wanandroid.biz.search.HistorySearchKeyDao
import com.qihuan.wanandroid.common.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * DatabaseModule
 * @author qi
 * @since 2020/8/11
 */
@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideHistorySearchKeyDao(database: AppDatabase): HistorySearchKeyDao {
        return database.historySearchKeyDao()
    }
}