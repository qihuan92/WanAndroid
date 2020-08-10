package com.qihuan.wanandroid.common.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * IntentExt
 * @author qi
 * @since 2020/8/10
 */
inline fun buildIntent(initializer: Intent.() -> Unit): Intent {
    return Intent().apply(initializer)
}

inline fun <reified T : Activity> buildIntent(
    context: Context,
    initializer: Intent.() -> Unit
): Intent {
    return Intent(context, T::class.java).apply(initializer)
}