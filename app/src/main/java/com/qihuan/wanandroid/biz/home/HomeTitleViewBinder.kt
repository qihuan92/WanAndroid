package com.qihuan.wanandroid.biz.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.qihuan.wanandroid.bean.TitleBean
import com.qihuan.wanandroid.databinding.ItemHomeTitleBinding

/**
 * HomeTitleViewDelegate
 * @author qi
 * @since 2020/8/12
 */
class HomeTitleViewBinder : ItemViewBinder<TitleBean, HomeTitleViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val binding = ItemHomeTitleBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: TitleBean) {
        holder.bind(item)
    }

    class ViewHolder(
        private val binding: ItemHomeTitleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TitleBean) {
            binding.apply {
                tvTitle.text = item.title
                ivIcon.setImageResource(item.icon)
            }
        }
    }
}