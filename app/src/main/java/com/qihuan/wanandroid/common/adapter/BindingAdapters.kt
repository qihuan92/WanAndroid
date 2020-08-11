package com.qihuan.wanandroid.common.adapter

import android.annotation.SuppressLint
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.TextViewBindingAdapter


/**
 * BindingAdapters
 * @author qi
 * @since 2020/8/11
 */
object BindingAdapters {

    @JvmStatic
    @SuppressLint("RestrictedApi")
    @BindingAdapter("android:text")
    fun setText(view: EditText, oldText: String?, text: String?) {
        TextViewBindingAdapter.setText(view, text)
        if (text == null) {
            return
        }
        if (text == oldText || oldText == null) {
            view.setSelection(text.length)
        }
    }
}