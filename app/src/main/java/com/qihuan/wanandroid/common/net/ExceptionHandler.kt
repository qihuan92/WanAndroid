package com.qihuan.wanandroid.common.net

import com.jeremyliao.liveeventbus.LiveEventBus
import com.jeremyliao.liveeventbus.core.Observable
import com.qihuan.wanandroid.bean.WanResponse
import com.qihuan.wanandroid.common.ApiResult
import kotlin.reflect.KClass

/**
 * ExceptionHandler
 * @author qi
 * @since 2020/8/12
 */
suspend fun <T : Any> handleRequest(requestFunc: suspend () -> WanResponse<T>): ApiResult<T> {
    val resp: WanResponse<T>?
    try {
        resp = requestFunc.invoke()
    } catch (e: Exception) {
        val error = ApiResult.Error(e)
        postEvent(error)
        return error
    }

    if (!resp.isSuccess()) {
        return ApiResult.Error(ApiException(resp.errorMsg))
    }

    return ApiResult.Success(resp.data)
}

class ApiException(override val message: String?) : Exception()

fun <T : Any> postEvent(event: T) {
    LiveEventBus.get(event.javaClass.simpleName, event.javaClass).post(event)
}

fun <T : Any> handleEvent(clazz: KClass<T>): Observable<T> {
    return LiveEventBus.get(clazz.java.simpleName, clazz.java)
}