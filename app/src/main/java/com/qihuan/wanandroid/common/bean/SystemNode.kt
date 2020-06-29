package com.qihuan.wanandroid.common.bean

/**
 * SystemNode
 * @author qi
 * @since 2020/6/29
 */
data class SystemNode(
    val children: List<SystemNode>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val visible: Int,
    val userControlSetTop: Boolean
)