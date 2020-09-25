package com.qihuan.wanandroid.common.net

import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.jeremyliao.liveeventbus.LiveEventBus
import com.jeremyliao.liveeventbus.core.Observable
import com.qihuan.wanandroid.bean.WanResponse
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLHandshakeException
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
        val fixedException = when (e) {
            is SSLHandshakeException, is UnknownHostException, is ConnectException -> {
                ApiException(ResultEnum.NET_CONN_ERROR.getDescription())
            }
            is TimeoutException, is HttpException -> {
                ApiException(ResultEnum.IO_ERROR.getDescription())
            }
            is JsonIOException, is JsonSyntaxException -> {
                ApiException(ResultEnum.JSON_ERROR.getDescription())
            }
            else -> {
                e
            }
        }

        val error = ApiResult.Error(error = fixedException)
        postEvent(error)
        return error
    }

    if (!resp.isSuccess()) {
        return ApiResult.Error(error = ApiException(resp.errorMsg))
    }

    return ApiResult.Success(resp.data)
}

class ApiException(override val message: String) : Exception() {
    override fun toString(): String {
        return message
    }
}

fun <T : Any> postEvent(event: T) {
    LiveEventBus.get(event.javaClass.simpleName, event.javaClass).post(event)
}

fun <T : Any> handleEvent(clazz: KClass<T>): Observable<T> {
    return LiveEventBus.get(clazz.java.simpleName, clazz.java)
}