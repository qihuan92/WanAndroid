package com.qihuan.wanandroid.common.ktx

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

/**
 * ImmExt
 * @author qi
 * @since 2020/9/22
 */
fun Context.showKeyboard(view: View) {
    val imm = getSystemService(InputMethodManager::class.java)
    imm.showSoftInput(view, 0)
}

fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(InputMethodManager::class.java)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.showKeyboard(view: View) {
    requireActivity().showKeyboard(view)
}

fun Fragment.hideKeyboard(view: View) {
    requireActivity().hideKeyboard(view)
}