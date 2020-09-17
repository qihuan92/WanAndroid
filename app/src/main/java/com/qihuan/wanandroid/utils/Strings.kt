package com.qihuan.wanandroid.utils

import androidx.annotation.StringRes
import com.qihuan.wanandroid.App

object Strings {
    fun get(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
        return App.instance.getString(stringRes, *formatArgs)
    }
}