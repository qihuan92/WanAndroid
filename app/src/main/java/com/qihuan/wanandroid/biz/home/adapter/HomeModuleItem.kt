package com.qihuan.wanandroid.biz.home.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.qihuan.wanandroid.bean.ModuleList
import com.qihuan.wanandroid.databinding.ItemHomeModuleBinding

/**
 * HomeModuleViewHolder
 * @author qi
 * @since 2020/8/13
 */
class HomeModuleViewHolder(binding: ItemHomeModuleBinding) : RecyclerView.ViewHolder(binding.root) {

    private val adapter by lazy { ModuleCellAdapter() }

    init {
        binding.rvList.layoutManager = LinearLayoutManager(
            binding.rvList.context, LinearLayoutManager.HORIZONTAL, false
        )
        PagerSnapHelper().attachToRecyclerView(binding.rvList)

        binding.rvList.adapter = adapter
    }

    fun bind(item: ModuleList) {
        adapter.submitList(item.list)
    }
}