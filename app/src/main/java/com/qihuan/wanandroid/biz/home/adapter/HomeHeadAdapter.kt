package com.qihuan.wanandroid.biz.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.BannerList
import com.qihuan.wanandroid.bean.ModuleList
import com.qihuan.wanandroid.bean.TitleBean
import com.qihuan.wanandroid.common.adapter.DiffItem
import com.qihuan.wanandroid.databinding.ItemArticleBinding
import com.qihuan.wanandroid.databinding.ItemBannerLayoutBinding
import com.qihuan.wanandroid.databinding.ItemHomeModuleBinding
import com.qihuan.wanandroid.databinding.ItemHomeTitleBinding

/**
 * HomeHeadAdapter
 * @author qi
 * @since 2020/9/8
 */
class HomeHeadAdapter : ListAdapter<DiffItem, RecyclerView.ViewHolder>(DiffCallback()) {

    companion object {
        const val TYPE_BANNER = R.layout.item_banner_layout
        const val TYPE_TITLE = R.layout.item_home_title
        const val TYPE_MODULE = R.layout.item_home_module
        const val TYPE_ARTICLE = R.layout.item_article
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
            TYPE_BANNER -> {
                HomeBannerViewHolder(ItemBannerLayoutBinding.inflate(inflater, parent, false))
            }
            TYPE_TITLE -> {
                HomeTitleViewHolder(ItemHomeTitleBinding.inflate(inflater, parent, false))
            }
            TYPE_MODULE -> {
                HomeModuleViewHolder(ItemHomeModuleBinding.inflate(inflater, parent, false))
            }
            TYPE_ARTICLE -> {
                ArticleItemViewHolder(ItemArticleBinding.inflate(inflater, parent, false))
            }
            else -> {
                EmptyViewHolder(View(parent.context))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is HomeBannerViewHolder -> holder.bind(item as BannerList)
            is HomeTitleViewHolder -> holder.bind(item as TitleBean)
            is HomeModuleViewHolder -> holder.bind(item as ModuleList)
            is ArticleItemViewHolder -> holder.bind(item as Article)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is BannerList -> TYPE_BANNER
            is TitleBean -> TYPE_TITLE
            is ModuleList -> TYPE_MODULE
            is Article -> TYPE_ARTICLE
            else -> super.getItemViewType(position)
        }
    }
}