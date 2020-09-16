package com.qihuan.wanandroid.biz.navigation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.TitleBean
import com.qihuan.wanandroid.biz.home.adapter.HomeTitleViewHolder
import com.qihuan.wanandroid.common.adapter.DiffItem
import com.qihuan.wanandroid.databinding.ItemHomeTitleBinding
import com.qihuan.wanandroid.databinding.ItemSubNavigationBinding

/**
 * NavigationAdapter
 * @author qi
 * @since 2020/9/8
 */
class NavigationAdapter : ListAdapter<DiffItem, RecyclerView.ViewHolder>(DiffCallback()) {

    companion object {
        const val TYPE_TITLE = R.layout.item_home_title
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
                HomeTitleViewHolder(ItemHomeTitleBinding.inflate(inflater, parent, false))
            }
            TYPE_ARTICLE -> {
                NavigationSubItemViewHolder(ItemSubNavigationBinding.inflate(inflater, parent, false))
            }
            else -> {
                EmptyViewHolder(View(parent.context))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is HomeTitleViewHolder -> holder.bind(item as TitleBean)
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