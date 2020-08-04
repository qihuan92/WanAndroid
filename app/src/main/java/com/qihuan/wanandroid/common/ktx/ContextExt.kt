package com.qihuan.wanandroid.common.ktx

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

@Suppress("UNUSED_PARAMETER")
fun Context.openBrowser(url: String, title: String = url) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    customTabsIntent.launchUrl(this, Uri.parse(url))
}