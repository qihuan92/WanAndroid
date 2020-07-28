package com.qihuan.wanandroid.biz.home

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.common.ktx.showText
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
                // todo 条目信息
                tvTitle.text = Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY)
                tvDescription.showText(Html.fromHtml(item.desc, Html.FROM_HTML_MODE_LEGACY))
                tvAuthor.showText(item.author)
                tvTime.showText(item.niceDate)
            }
        }
    }
}