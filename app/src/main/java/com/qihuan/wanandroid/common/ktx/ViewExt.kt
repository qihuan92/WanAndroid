package com.qihuan.wanandroid.common.ktx

import android.graphics.Outline
import android.util.TypedValue
import android.view.View
import android.view.ViewOutlineProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton

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

fun FloatingActionButton.hideInvisible() {
    hide(object : FloatingActionButton.OnVisibilityChangedListener() {
        override fun onHidden(fab: FloatingActionButton?) {
            super.onHidden(fab)
            fab?.visibility = View.INVISIBLE
        }
    })
}

fun View.clipRound(radius: Int = 5f.dp) {
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline?) {
            outline?.setRoundRect(0, 0, view.width, view.height, radius.toFloat())
        }
    }
    clipToOutline = true
}

fun View.clipCircle() {
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline?) {
            outline?.setOval(0, 0, view.width, view.height)
        }
    }
    clipToOutline = true
}