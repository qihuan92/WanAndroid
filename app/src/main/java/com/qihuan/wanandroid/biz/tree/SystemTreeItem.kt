package com.qihuan.wanandroid.biz.tree

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.qihuan.wanandroid.bean.SystemNode
import com.qihuan.wanandroid.databinding.ItemTreeBinding

/**
 * TreeItem
 * @author qi
 * @since 2020/9/14
 */
class TreeViewHolder(
    private val binding: ItemTreeBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SystemNode) {
        binding.layoutTitle.tvTitle.text = item.name
        binding.cgNodes.removeAllViews()
        for (child in item.children) {
            binding.cgNodes.addView(createChip(binding.cgNodes.context, child))
        }
    }

    private fun createChip(context: Context, node: SystemNode): Chip {
        return Chip(context).apply {
            text = node.name
            setOnCloseIconClickListener {
                // todo 打开链接
            }
        }
    }
}

class SystemTreeAdapter : ListAdapter<SystemNode, TreeViewHolder>(DiffCallback()) {

    private class DiffCallback : DiffUtil.ItemCallback<SystemNode>() {
        override fun areItemsTheSame(oldItem: SystemNode, newItem: SystemNode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SystemNode, newItem: SystemNode): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreeViewHolder {
        val binding = ItemTreeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TreeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TreeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}