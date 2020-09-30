package com.qihuan.wanandroid.biz.home.adapter

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.qihuan.wanandroid.bean.TitleBean
import com.qihuan.wanandroid.common.ktx.load
import com.qihuan.wanandroid.databinding.ItemHomeTitleBinding

/**
 * HomeTitleViewHolder
 * @author qi
 * @since 2020/8/12
 */
class HomeTitleViewHolder(
    private val binding: ItemHomeTitleBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: TitleBean) {
        binding.tvTitle.text = item.title
        val icon = item.icon
        binding.ivIcon.isVisible = icon > 0
        binding.ivIcon.load(icon)
    }
}