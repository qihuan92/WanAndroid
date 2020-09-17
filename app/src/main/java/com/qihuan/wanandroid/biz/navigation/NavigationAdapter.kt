package com.qihuan.wanandroid.biz.navigation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.TitleBean
import com.qihuan.wanandroid.common.adapter.DiffItem
import com.qihuan.wanandroid.databinding.ItemNavigationTitleBinding
import com.qihuan.wanandroid.databinding.ItemSubNavigationBinding

/**
 * NavigationAdapter
 * @author qi
 * @since 2020/9/8
 */
class NavigationAdapter : ListAdapter<DiffItem, RecyclerView.ViewHolder>(DiffCallback()) {

    companion object {
        const val TYPE_TITLE = R.layout.item_navigation_title
        const val TYPE_ARTICLE = R.layout.item_sub_navigation
    }

    private class DiffCallback : DiffUtil.ItemCallback<DiffItem>() {
        override fun areItemsTheSame(oldItem: DiffItem, newItem: DiffItem): Boolean {
            return oldItem.getUniqueId() == newItem.getUniqueId()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: DiffItem, newItem: DiffItem): Boolean {
            return oldItem == newItem
        }
    }

    private class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_TITLE -> {
                val binding = ItemNavigationTitleBinding.inflate(inflater, parent, false)
                NavigationTitleItemViewHolder(binding).apply {
                    val itemLayoutParams = itemView.layoutParams
                    if (itemLayoutParams is StaggeredGridLayoutManager.LayoutParams) {
                        itemLayoutParams.isFullSpan = true
                    }
                }
            }
            TYPE_ARTICLE -> {
                val binding = ItemSubNavigationBinding.inflate(inflater, parent, false)
                NavigationSubItemViewHolder(binding)
            }
            else -> {
                EmptyViewHolder(View(parent.context))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is NavigationTitleItemViewHolder -> holder.bind(item as TitleBean)
            is NavigationSubItemViewHolder -> holder.bind(item as Article)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TitleBean -> TYPE_TITLE
            is Article -> TYPE_ARTICLE
            else -> super.getItemViewType(position)
        }
    }
}