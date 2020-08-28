package com.qihuan.wanandroid.biz.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.drakeet.multitype.ItemViewBinder
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.databinding.ItemArticleBinding

/**
 * ArticleItemViewBinder
 * @author qi
 * @since 2020/7/21
 */
class ArticleItemViewBinder : ItemViewBinder<Article, ArticleItemViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ArticleItemViewHolder {
        val binding = ItemArticleBinding.inflate(inflater, parent, false)
        return ArticleItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleItemViewHolder, item: Article) {
        holder.bind(item)
    }
}