package com.qihuan.wanandroid.common.bean

data class WanResponse<out T>(
    val errorCode: Int,
    val errorMsg: String,
    val data: T
)