package com.qihuan.wanandroid.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * ArticleTag
 * @author qi
 * @since 2020/6/29
 */
@Parcelize
data class ArticleTag(
    val name: String,
    val url: String
) : Parcelable