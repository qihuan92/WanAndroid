package com.qihuan.wanandroid.biz.home.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qihuan.wanandroid.bean.ModuleBean
import com.qihuan.wanandroid.common.ktx.buildIntent
import com.qihuan.wanandroid.common.ktx.load
import com.qihuan.wanandroid.common.ktx.openBrowser
import com.qihuan.wanandroid.databinding.ItemHomeModuleCellBinding

/**
 * ModuleCellViewHolder
 * @author qi
 * @since 2020/8/13
 */
class ModuleCellViewHolder(
    private val binding: ItemHomeModuleCellBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ModuleBean) {
        binding.ivBackground.load(item.backgroundImage)
        binding.ivIcon.load(item.icon)
        binding.tvTitle.text = item.title
        binding.root.setOnClickListener {
            if (item.link.isEmpty()) {
                return@setOnClickListener
            }
            if (item.link.startsWith("http")) {
                binding.root.openBrowser(item.link)
            } else {
                binding.root.context.startActivity(
                    buildIntent {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse(item.link)
                    }
                )
            }
        }
    }
}

/**
 * ModuleCellAdapter
 * @author qi
 * @since 2020/8/13
 */
class ModuleCellAdapter : ListAdapter<ModuleBean, ModuleCellViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<ModuleBean>() {
        override fun areItemsTheSame(oldItem: ModuleBean, newItem: ModuleBean): Boolean {
            return oldItem.getUniqueId() == newItem.getUniqueId()
        }

        override fun areContentsTheSame(oldItem: ModuleBean, newItem: ModuleBean): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuleCellViewHolder {
        val binding =
            ItemHomeModuleCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ModuleCellViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModuleCellViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}