package com.qihuan.wanandroid.biz.tree

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.qihuan.wanandroid.bean.SystemNode
import com.qihuan.wanandroid.databinding.ItemSystemTreeFirstBinding
import com.qihuan.wanandroid.databinding.ItemTreeBinding

/**
 * First category ViewHolder
 */
class SystemTreeFirstViewHolder(
    private val binding: ItemSystemTreeFirstBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SystemNode, isSelected: Boolean = false) {
        binding.tvContent.text = item.name
        itemView.isSelected = isSelected
    }

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = absoluteAdapterPosition
            override fun getSelectionKey(): Long? = itemId
            override fun inSelectionHotspot(e: MotionEvent): Boolean = true
        }
}

/**
 * First category adapter
 */
class SystemTreeFirstAdapter : ListAdapter<SystemNode, SystemTreeFirstViewHolder>(DiffCallback()) {

    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    private class DiffCallback : DiffUtil.ItemCallback<SystemNode>() {
        override fun areItemsTheSame(oldItem: SystemNode, newItem: SystemNode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SystemNode, newItem: SystemNode): Boolean {
            return oldItem == newItem
        }
    }

    class ItemLookup(
        private val recyclerView: RecyclerView
    ) : ItemDetailsLookup<Long>() {
        override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(e.x, e.y)
            if (view != null) {
                val viewHolder = recyclerView.getChildViewHolder(view) as SystemTreeFirstViewHolder
                return viewHolder.getItemDetails()
            }
            return null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SystemTreeFirstViewHolder {
        val binding =
            ItemSystemTreeFirstBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SystemTreeFirstViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SystemTreeFirstViewHolder, position: Int) {
        val isSelected = tracker?.isSelected(position.toLong()) ?: false
        holder.bind(getItem(position), isSelected)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}

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