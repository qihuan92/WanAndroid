package com.qihuan.wanandroid.common

/**
 * Result
 * @author qi
 * @since 2020/7/22
 */
sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data: T?) : ApiResult<T>()
    data class Error(val error: Exception) : ApiResult<Nothing>()
}