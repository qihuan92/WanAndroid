package com.qihuan.wanandroid.common.ktx

import android.app.Activity
import android.content.res.Configuration
import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.isDarkTheme() =
    resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

fun AppCompatActivity.transparentStatusBar() {
    window.apply {
        statusBarColor = Color.TRANSPARENT
        if (isDarkTheme()) {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        } else {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }
}

inline fun <reified T : Any> Activity.extra(name: String, default: T? = null) = lazy {
    val value = intent.extras?.get(name)
    if (value is T) {
        value
    } else {
        default
    }
}