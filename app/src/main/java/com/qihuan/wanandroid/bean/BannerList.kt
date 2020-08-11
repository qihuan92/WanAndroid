package com.qihuan.wanandroid.bean

import com.qihuan.wanandroid.common.adapter.DiffItem

/**
 * BannerList
 * @author qi
 * @since 2020/7/16
 */
data class BannerList(
    val list: List<BannerBean>
) : DiffItem {
    override fun getUniqueId(): Any {
        return list.map { it.id }.joinToString()
    }
}