package com.qihuan.wanandroid.common.delegate

import com.qihuan.wanandroid.common.net.RetrofitClient
import kotlin.reflect.KProperty

/**
 * Retrofit
 * @author qi
 * @since 2020/6/28
 */
class Retrofit {
    inline operator fun <reified T : Any> getValue(thisRef: Any?, property: KProperty<*>): T {
        return RetrofitClient.instance.getApi(T::class.java)
    }
}