package com.qihuan.wanandroid.bean

/**
 * ModuleBean
 * @author qi
 * @since 2020/8/13
 */
data class ModuleList(
    val list: List<ModuleBean>
)

data class ModuleBean(
    val backgroundImage: String,
    val icon: String,
    val title: String,
    val link: String
)