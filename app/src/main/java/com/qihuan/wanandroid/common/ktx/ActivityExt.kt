package com.qihuan.wanandroid.common.ktx

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

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