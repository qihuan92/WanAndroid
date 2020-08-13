package com.qihuan.wanandroid.biz.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.drakeet.multitype.MultiTypeAdapter
import com.qihuan.wanandroid.bean.ModuleList
import com.qihuan.wanandroid.databinding.ItemHomeModuleBinding

/**
 * ModuleViewDelegate
 * @author qi
 * @since 2020/8/13
 */
class ModuleItemViewBinder : ItemViewBinder<ModuleList, ModuleItemViewBinder.ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val binding = ItemHomeModuleBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: ModuleList) {
        holder.bind(item)
    }

    class ViewHolder(
        private val binding: ItemHomeModuleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val adapter by lazy { MultiTypeAdapter() }

        init {
            binding.rvList.layoutManager = LinearLayoutManager(
                binding.rvList.context, LinearLayoutManager.HORIZONTAL, false
            )
            PagerSnapHelper().attachToRecyclerView(binding.rvList)

            adapter.register(ModuleCellItemViewBinder())
            binding.rvList.adapter = adapter
        }

        fun bind(item: ModuleList) {
            binding.apply {
                adapter.items = item.list
                adapter.notifyDataSetChanged()
            }
        }
    }
}