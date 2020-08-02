package com.qihuan.wanandroid.common.ktx

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.isDarkTheme() =
    resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES