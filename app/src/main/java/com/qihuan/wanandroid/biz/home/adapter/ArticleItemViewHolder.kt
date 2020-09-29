package com.qihuan.wanandroid.biz.home.adapter

import android.text.method.LinkMovementMethod
import android.widget.Toast
import androidx.core.text.buildSpannedString
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.ArticleTag
import com.qihuan.wanandroid.common.ktx.click
import com.qihuan.wanandroid.common.ktx.load
import com.qihuan.wanandroid.common.ktx.openBrowser
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

            tvTitle.text = item.titleHtml
            tvDescription.text = item.descHtml
            tvAuthor.text = item.author.ifEmpty { item.shareUser }
            tvTime.text = item.niceDate
            tvCategory.text = item.categoryText
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