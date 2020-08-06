package com.qihuan.wanandroid.biz.home

import android.content.Context
import android.graphics.Color
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.google.android.material.chip.Chip
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.ArticleTag
import com.qihuan.wanandroid.common.ktx.*
import com.qihuan.wanandroid.databinding.ItemArticleBinding

/**
 * ArticleItemViewBinder
 * @author qi
 * @since 2020/7/21
 */
class ArticleItemViewBinder : ItemViewBinder<Article, ArticleItemViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val binding = ItemArticleBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Article) {
        holder.bind(item)
    }

    class ViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) {
            binding.apply {
                ivPic.clipRound()
                if (item.envelopePic.isNotBlank()) {
                    ivPic.isGone = false
                    ivPic.load(url = item.envelopePic)
                } else {
                    ivPic.isGone = true
                }

                tvTitle.text = Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY)
                tvDescription.showText(Html.fromHtml(item.desc, Html.FROM_HTML_MODE_LEGACY))
                tvAuthor.showText(item.author)
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
            }
        }

        private fun bindTags(tags: List<ArticleTag>) {
            binding.apply {
                cgTags.removeAllViews()
                for (tag in tags) {
                    cgTags.addView(tagView(cgTags.context, tag))
                }
            }
        }

        private fun tagView(context: Context, tag: ArticleTag): Chip {
            val chip = Chip(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, 20f.dp
                )
                setChipBackgroundColorResource(R.color.colorAccent)
                setEnsureMinTouchTargetSize(false)
                ensureAccessibleTouchTarget(0)
                setPadding(paddingLeft, 0, paddingRight, 0)
                chipStartPadding = 0f
                chipEndPadding = 0f
                isCheckable = false
                text = tag.name
                textSize = 12f
                setTextColor(Color.WHITE)
            }
            chip.setOnClickListener {
                // todo 跳转分类列表
            }
            return chip
        }
    }
}