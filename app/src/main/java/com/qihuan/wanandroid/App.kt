package com.qihuan.wanandroid

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * App
 * @author qi
 * @date 2020/5/19
 */
@HiltAndroidApp
class App : Application() {
    companion object {
        lateinit var instance: App private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}