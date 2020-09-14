package com.qihuan.wanandroid.bean

import androidx.annotation.DrawableRes
import com.qihuan.wanandroid.common.adapter.DiffItem

/**
 * ModuleBean
 * @author qi
 * @since 2020/8/13
 */
data class ModuleList(
    val list: List<ModuleBean>
) : DiffItem {
    override fun getUniqueId(): Any {
        return list.joinToString { it.title }
    }
}

data class ModuleBean(
    val backgroundImage: String,
    @DrawableRes val icon: Int,
    val title: String,
    val link: String
) : DiffItem {
    override fun getUniqueId(): Any {
        return title
    }
}