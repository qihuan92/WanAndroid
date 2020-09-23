package com.qihuan.wanandroid.common.ktx

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.graphics.Color
import android.net.Uri
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.res.use

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

@ColorInt
@SuppressLint("Recycle")
fun Context.themeColor(
    @AttrRes themeAttrId: Int
): Int {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getColor(0, Color.MAGENTA)
    }
}