package com.qihuan.wanandroid.common.adapter

import androidx.recyclerview.widget.DiffUtil
import com.drakeet.multitype.MultiTypeAdapter
import com.qihuan.wanandroid.bean.Article

/**
 * DiffMultiTypeAdapter
 * @author qi
 * @since 2020/8/11
 */
open class DiffMultiTypeAdapter : MultiTypeAdapter() {

    private lateinit var diffCallback: DiffUtil.Callback
    private var oldList = mutableListOf<Any>()

    override var items: List<Any>
        get() = super.items
        set(value) {
            diffCallback = DefaultDiffCallback(oldList, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            oldList.clear()
            oldList.addAll(value)
            diffResult.dispatchUpdatesTo(this)
            super.items = value
        }
}

interface DiffItem {
    fun getUniqueId(): Any
}

class DefaultDiffCallback(
    private val oldList: List<Any>,
    private val newList: List<Any>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return if (oldItem is DiffItem && newItem is DiffItem) {
            oldItem.getUniqueId() == newItem.getUniqueId()
        } else {
            oldItem == newItem
        }
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return if (oldItem is Article && newItem is Article) {
            oldItem.title == newItem.title
        } else {
            oldItem == newItem
        }
    }
}