package com.qihuan.wanandroid.biz.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.qihuan.wanandroid.bean.BannerList
import com.qihuan.wanandroid.databinding.ItemBannerLayoutBinding

/**
 * HomeBannerViewHolder
 * @author qi
 * @since 2020/9/8
 */
class HomeBannerViewHolder(
    binding: ItemBannerLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val cellAdapter by lazy { BannerCellAdapter() }

    init {
        binding.root.setAdapter(cellAdapter)
    }

    fun bind(item: BannerList) {
        cellAdapter.submitList(item.list)
    }
}

/**
 * HomeBannerAdapter
 * @author qi
 * @since 2020/9/8
 */
class HomeBannerAdapter : ListAdapter<BannerList, HomeBannerViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<BannerList>() {
        override fun areItemsTheSame(oldItem: BannerList, newItem: BannerList): Boolean {
            return oldItem.getUniqueId() == newItem.getUniqueId()
        }

        override fun areContentsTheSame(oldItem: BannerList, newItem: BannerList): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerViewHolder {
        val binding =
            ItemBannerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeBannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeBannerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

/**
 * HomeBannerViewBinder
 * @author qi
 * @since 2020/9/8
 */
class HomeBannerViewBinder : ItemViewBinder<BannerList, HomeBannerViewHolder>() {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): HomeBannerViewHolder {
        val binding = ItemBannerLayoutBinding.inflate(inflater, parent, false)
        return HomeBannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeBannerViewHolder, item: BannerList) {
        holder.bind(item)
    }
}