package com.qihuan.wanandroid.common.ktx

import android.content.Context
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

@Suppress("UNUSED_PARAMETER")
fun Context.openBrowser(url: String, title: String = url) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder
        .setShowTitle(true)
        .build()
    customTabsIntent.launchUrl(this, Uri.parse(url))
}

@Suppress("UNUSED_PARAMETER")
fun Context.openBrowserNewTask(url: String, title: String = url) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder
        .setShowTitle(true)
        .build()
    customTabsIntent.intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
    customTabsIntent.launchUrl(this.applicationContext, Uri.parse(url))
}