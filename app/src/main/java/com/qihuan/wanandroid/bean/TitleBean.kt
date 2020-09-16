package com.qihuan.wanandroid.bean

import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.common.adapter.DiffItem

/**
 * TitleBean
 * @author qi
 * @since 2020/8/12
 */
data class TitleBean(
    val title: CharSequence,
    val icon: Int = 0
) : DiffItem {
    override fun getUniqueId(): Any {
        return title
    }
}

enum class TitleType(
    private val title: CharSequence,
    private val icon: Int
) {
    TOP("置顶", R.drawable.ic_sharp_vertical_align_top_24),
    TIMELINE("时间线", R.drawable.ic_round_access_time),
    MODULE("模块", R.drawable.ic_round_widgets_24),
    ;

    fun create(): TitleBean {
        return TitleBean(title, icon)
    }
}