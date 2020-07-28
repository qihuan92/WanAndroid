package com.qihuan.wanandroid.common.ktx

import android.widget.TextView
import androidx.core.view.isVisible

/**
 * TextExt
 * @author qi
 * @since 2020/7/28
 */
fun TextView.showText(value: CharSequence) {
    if (value.isBlank()) {
        isVisible = false
    } else {
        isVisible = true
        text = value
    }
}