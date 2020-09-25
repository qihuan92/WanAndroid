package com.qihuan.wanandroid.common.ktx

import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.text.inSpans
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

inline fun SpannableStringBuilder.click(
    crossinline onClick: (View) -> Unit,
    isUnderlineText: Boolean = true,
    builderAction: SpannableStringBuilder.() -> Unit
) = inSpans(object : ClickableSpan() {
    override fun onClick(widget: View) {
        onClick.invoke(widget)
    }

    override fun updateDrawState(ds: TextPaint) {
        ds.color = ds.linkColor
        ds.isUnderlineText = isUnderlineText
    }
}, builderAction = builderAction)