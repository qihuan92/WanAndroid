package com.qihuan.wanandroid

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

/**
 * App
 * @author qi
 * @date 2020/5/19
 */
class App : Application() {

    companion object {
        var context: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}