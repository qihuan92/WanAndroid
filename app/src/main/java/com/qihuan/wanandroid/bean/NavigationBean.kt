package com.qihuan.wanandroid.bean

import com.qihuan.wanandroid.common.adapter.DiffItem

data class NavigationBean(
    val articles: List<Article>,
    val cid: Int,
    val name: String
) : DiffItem {
    override fun getUniqueId(): Any {
        return cid
    }
}