package com.qihuan.wanandroid.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * SystemNode
 * @author qi
 * @since 2020/6/29
 */
@Parcelize
data class SystemNode(
    val children: List<SystemNode>,
    val courseId: Int,
    val id: Long,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val visible: Int,
    val userControlSetTop: Boolean
) : Parcelable