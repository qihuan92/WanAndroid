package com.qihuan.wanandroid.biz.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.qihuan.wanandroid.bean.TitleBean
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
        binding.apply {
            tvTitle.text = item.title
            ivIcon.setImageResource(item.icon)
        }
    }
}