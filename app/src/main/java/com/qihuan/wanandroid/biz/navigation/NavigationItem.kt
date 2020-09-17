package com.qihuan.wanandroid.biz.navigation

import androidx.recyclerview.widget.RecyclerView
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.TitleBean
import com.qihuan.wanandroid.common.ktx.openBrowser
import com.qihuan.wanandroid.databinding.ItemNavigationTitleBinding
import com.qihuan.wanandroid.databinding.ItemSubNavigationBinding

/**
 * NavigationItem
 * @author qi
 * @since 2020/9/16
 */
class NavigationSubItemViewHolder(
    private val binding: ItemSubNavigationBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Article) {
        binding.tvContent.text = item.title
        binding.root.setOnClickListener {
            it.openBrowser(item.link, item.title)
        }
    }
}

class NavigationTitleItemViewHolder(
    private val binding: ItemNavigationTitleBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: TitleBean) {
        binding.tvTitle.text = item.title
    }
}