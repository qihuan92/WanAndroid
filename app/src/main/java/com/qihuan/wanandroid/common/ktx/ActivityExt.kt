package com.qihuan.wanandroid.common.ktx

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import com.google.android.material.color.MaterialColors

private const val EDGE_TO_EDGE_FLAGS =
    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
private const val EDGE_TO_EDGE_BAR_ALPHA = 128

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

@SuppressLint("ObsoleteSdkInt")
fun AppCompatActivity.applyEdgeToEdge() {
    if (VERSION.SDK_INT < VERSION_CODES.LOLLIPOP) {
        return
    }

    val edgeToEdgeEnabled = true

    val statusBarColor: Int = getStatusBarColor(this, edgeToEdgeEnabled)
    val navBarColor = getNavBarColor(this, edgeToEdgeEnabled)

    val lightBackground = isColorLight(
        MaterialColors.getColor(this, android.R.attr.colorBackground, Color.BLACK)
    )

    val lightStatusBar = isColorLight(statusBarColor)
    val showDarkStatusBarIcons =
        lightStatusBar || statusBarColor == Color.TRANSPARENT && lightBackground

    val lightNavBar = isColorLight(navBarColor)
    val showDarkNavBarIcons = lightNavBar || navBarColor == Color.TRANSPARENT && lightBackground

    val decorView = window.decorView

    val currentStatusBar =
        if (VERSION.SDK_INT >= VERSION_CODES.M) {
            if (showDarkStatusBarIcons) {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                View.SYSTEM_UI_FLAG_VISIBLE
            }
        } else 0

    val currentNavBar =
        if (showDarkNavBarIcons && VERSION.SDK_INT >= VERSION_CODES.O) {
            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else 0

    val flags = if (edgeToEdgeEnabled) EDGE_TO_EDGE_FLAGS else View.SYSTEM_UI_FLAG_VISIBLE

    window.navigationBarColor = navBarColor
    window.statusBarColor = statusBarColor
    decorView.systemUiVisibility = flags or currentStatusBar or currentNavBar
}

private fun isColorLight(@ColorInt color: Int): Boolean {
    return color != Color.TRANSPARENT && ColorUtils.calculateLuminance(color) > 0.5
}

@Suppress("SameParameterValue")
@SuppressLint("ObsoleteSdkInt")
private fun getStatusBarColor(context: Context, isEdgeToEdgeEnabled: Boolean): Int {
    if (isEdgeToEdgeEnabled && (VERSION.SDK_INT < VERSION_CODES.M)) {
        val opaqueStatusBarColor: Int =
            MaterialColors.getColor(context, android.R.attr.statusBarColor, Color.BLACK)
        return ColorUtils.setAlphaComponent(opaqueStatusBarColor, EDGE_TO_EDGE_BAR_ALPHA)
    }
    return if (isEdgeToEdgeEnabled) {
        Color.TRANSPARENT
    } else MaterialColors.getColor(context, android.R.attr.statusBarColor, Color.BLACK)
}

@Suppress("SameParameterValue")
private fun getNavBarColor(context: Context, isEdgeToEdgeEnabled: Boolean): Int {
    if (isEdgeToEdgeEnabled && VERSION.SDK_INT < VERSION_CODES.O_MR1) {
        val opaqueNavBarColor: Int =
            MaterialColors.getColor(context, android.R.attr.navigationBarColor, Color.BLACK)
        return ColorUtils.setAlphaComponent(opaqueNavBarColor, EDGE_TO_EDGE_BAR_ALPHA)
    }
    return if (isEdgeToEdgeEnabled) {
        Color.TRANSPARENT
    } else {
        MaterialColors.getColor(context, android.R.attr.navigationBarColor, Color.BLACK)
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}