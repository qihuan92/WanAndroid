package com.qihuan.wanandroid.common.ktx

import android.util.TypedValue
import android.view.View

fun View.openBrowser(url: String, title: String = url) {
    this.context.openBrowser(url, title)
}

fun View.addRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, this, true)
    setBackgroundResource(resourceId)
}

fun View.addCircleRipple() = with(TypedValue()) {
    context.theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, this, true)
    setBackgroundResource(resourceId)
}