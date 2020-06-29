package com.qihuan.wanandroid.common.bean

/**
 * WanPage
 * @author qi
 * @since 2020/6/29
 */
data class WanPage<T>(
    val offset: Int,
    val size: Int,
    val total: Int,
    val pageCount: Int,
    val curPage: Int,
    val over: Boolean,
    val datas: List<T>
)