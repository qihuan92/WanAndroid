package com.qihuan.wanandroid.bean

/**
 * WanPage
 * @author qi
 * @since 2020/6/29
 */
data class WanPage<T>(
    val offset: Int = 0,
    val size: Int = 0,
    val total: Int = 0,
    val pageCount: Int = 0,
    val curPage: Int,
    val over: Boolean = true,
    val datas: List<T>
)