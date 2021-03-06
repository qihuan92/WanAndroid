package com.qihuan.wanandroid.bean

import android.os.Parcelable
import androidx.core.text.buildSpannedString
import androidx.core.text.parseAsHtml
import com.qihuan.wanandroid.common.adapter.DiffItem
import com.qihuan.wanandroid.common.ktx.click
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(
    val id: Int,
    val originId: Int,
    val title: String,
    val chapterId: Int,
    val chapterName: String,
    val envelopePic: String,
    val link: String,
    val author: String,
    val origin: String,
    val publishTime: Long,
    val zan: Int,
    val desc: String,
    val visible: Int,
    val niceDate: String,
    val niceShareDate: String,
    val courseId: Int,
    var collect: Boolean,
    val apkLink: String,
    val projectLink: String,
    val superChapterId: Int,
    val superChapterName: String?,
    val type: Int,
    val fresh: Boolean,
    val audit: Int,
    val prefix: String,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val tags: List<ArticleTag>,
    val userId: Int,
    var isTop: Boolean = false,

    // For UI
    var titleHtml: CharSequence? = null,
    var descHtml: CharSequence? = null,
    var categoryText: String? = null,
    var tagsText: CharSequence? = null,
) : Parcelable, DiffItem {
    override fun getUniqueId(): Any {
        return id
    }

    fun handleData() {
        titleHtml = title.parseAsHtml()
        descHtml = desc.parseAsHtml().ifEmpty { "..." }
        categoryText = "${superChapterName}·${chapterName}"
        tagsText = buildSpannedString {
            for (tag in tags) {
                click(
                    onClick = {
                        // todo 标签点击事件
                    },
                    isUnderlineText = false
                ) {
                    append("#${tag.name}#")
                }
                append(" ")
            }
        }
    }
}