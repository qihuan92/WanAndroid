package com.qihuan.wanandroid.common.ktx

import android.content.Context
import android.content.Intent

/**
 * IntentExt
 * @author qi
 * @since 2020/8/13
 */
inline fun buildIntent(initializer: Intent.() -> Unit) = Intent().apply { initializer() }

inline fun <reified T> buildIntent(
    context: Context,
    initializer: Intent.() -> Unit
) = Intent(context, T::class.java).apply { initializer() }