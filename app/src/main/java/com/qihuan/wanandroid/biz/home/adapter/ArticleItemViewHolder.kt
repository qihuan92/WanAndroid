package com.qihuan.wanandroid.biz.home.adapter

import android.text.method.LinkMovementMethod
import android.widget.Toast
import androidx.core.text.buildSpannedString
import androidx.core.text.parseAsHtml
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.ArticleTag
import com.qihuan.wanandroid.common.ktx.*
import com.qihuan.wanandroid.databinding.ItemArticleBinding

class ArticleItemViewHolder(
    private val binding: ItemArticleBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Article) {
        binding.apply {
            if (item.envelopePic.isNotBlank()) {
                ivPic.isGone = false
                ivPic.load(url = item.envelopePic)
            } else {
                ivPic.isGone = true
            }

            tvTitle.text = item.title.parseAsHtml()
            tvDescription.showText(item.desc.parseAsHtml())
            tvAuthor.showText(item.author)
            tvShareUser.showText(item.shareUser)
            tvTime.showText(item.niceDate)
            groupTop.isVisible = item.isTop
            tvCategory.showText("${item.superChapterName}·${item.chapterName}")
            bindTags(item.tags)
            if (item.collect) {
                btnCollect.setImageResource(R.drawable.ic_round_turned_in_24)
            } else {
                btnCollect.setImageResource(R.drawable.ic_round_turned_in_not_24)
            }

            btnCollect.setOnClickListener {
                // todo 收藏
            }

            itemView.setOnClickListener {
                it.openBrowser(item.link)
            }
            itemView.setOnLongClickListener {
                it.openBrowserNewTask(item.link)
                true
            }
        }
    }

    private fun bindTags(tags: List<ArticleTag>) {
        val tagsText = buildSpannedString {
            for (tag in tags) {
                click(
                    onClick = {
                        // todo 标签点击事件
                        Toast.makeText(itemView.context, tag.name, Toast.LENGTH_SHORT).show()
                    },
                    isUnderlineText = false
                ) {
                    append("#${tag.name}#")
                }
                append(" ")
            }
        }
        binding.tvTags.movementMethod = LinkMovementMethod.getInstance()
        binding.tvTags.text = tagsText
    }
}