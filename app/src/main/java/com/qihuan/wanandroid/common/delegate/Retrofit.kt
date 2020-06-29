package com.qihuan.wanandroid.common.delegate

import com.qihuan.wanandroid.common.net.RetrofitClient
import kotlin.reflect.KProperty

/**
 * Retrofit
 * @author qi
 * @since 2020/6/28
 */
class Retrofit<T : Any> {
    private lateinit var value: T

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return RetrofitClient.instance.getApi(value::class.java)
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }
}