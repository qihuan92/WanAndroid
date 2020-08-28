package com.qihuan.wanandroid.biz.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.databinding.ItemArticleBinding

/**
 * ArticlePageAdapter
 * @author qi
 * @since 2020/8/27
 */
class ArticlePageAdapter : PagingDataAdapter<Article, ArticleItemViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleItemViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleItemViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    private class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}