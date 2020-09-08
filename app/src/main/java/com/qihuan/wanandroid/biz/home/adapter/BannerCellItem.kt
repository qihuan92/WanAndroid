package com.qihuan.wanandroid.biz.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qihuan.wanandroid.bean.BannerBean
import com.qihuan.wanandroid.common.ktx.load
import com.qihuan.wanandroid.common.ktx.openBrowser
import com.qihuan.wanandroid.databinding.ItemHomeBannerCellBinding

/**
 * BannerCellViewHolder
 * @author qi
 * @since 2020/9/8
 */
class BannerCellViewHolder(
    private val binding: ItemHomeBannerCellBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BannerBean) {
        binding.ivBanner.load(item.imagePath)
        binding.root.setOnClickListener { it.openBrowser(item.url) }
    }
}

/**
 * BannerCellAdapter
 * @author qi
 * @since 2020/9/8
 */
class BannerCellAdapter : ListAdapter<BannerBean, BannerCellViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<BannerBean>() {
        override fun areItemsTheSame(oldItem: BannerBean, newItem: BannerBean): Boolean {
            return oldItem.getUniqueId() == newItem.getUniqueId()
        }

        override fun areContentsTheSame(oldItem: BannerBean, newItem: BannerBean): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerCellViewHolder {
        val binding =
            ItemHomeBannerCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerCellViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerCellViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}