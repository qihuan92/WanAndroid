package com.qihuan.wanandroid.bean

data class WanResponse<out T>(
    val errorCode: Int,
    val errorMsg: String,
    val data: T
) {
    fun isSuccess(): Boolean {
        return errorCode == 0
    }
}