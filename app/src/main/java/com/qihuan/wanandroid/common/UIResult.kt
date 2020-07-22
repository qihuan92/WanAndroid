package com.qihuan.wanandroid.common

/**
 * UIResult
 * @author qi
 * @since 2020/7/22
 */
sealed class UIResult<out T : Any> {
    data class Success<out T : Any>(val data: T, val isLoadingMore: Boolean = false) : UIResult<T>()
    data class Error(val error: Exception) : UIResult<Nothing>()
    object Loading : UIResult<Nothing>()
}