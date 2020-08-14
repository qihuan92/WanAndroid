package com.qihuan.wanandroid.common.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.atomic.AtomicBoolean

/**
 * PageMultiTypeAdapter
 * @author qi
 * @since 2020/8/7
 */
class PageMultiTypeAdapter : DiffMultiTypeAdapter() {

    private var loadMoreListener: (() -> Unit)? = null
    private var recyclerView: RecyclerView? = null
    private var isLoading = AtomicBoolean(false)
    private var tempSize = 0

    init {
        register(LoadMoreViewDelegate())
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        attachRecyclerView(recyclerView)
    }

    fun loadMoreComplete() {
        if (items.isEmpty()) {
            return
        }
        val mutableList = items.toMutableList()
        val loadMorePosition = items.size - 1
        val item = mutableList[loadMorePosition]
        if (item is LoadMoreItem) {
            mutableList.remove(loadMorePosition)
            items = mutableList
        }
        if (isLoading.get()) {
            isLoading.set(false)
        }
    }

    fun setOnLoadMoreListener(loadMoreListener: () -> Unit) {
        this.loadMoreListener = loadMoreListener
    }

    private fun attachRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(
            EndlessScrollListener(recyclerView.layoutManager as LinearLayoutManager)
        )
    }

    private fun loadMore() {
        isLoading.set(true)
        val loadMorePosition = items.size
        val mutableList = items.toMutableList()
        mutableList.add(loadMorePosition, LoadMoreItem())
        items = mutableList
        loadMoreListener?.invoke()

        tempSize = items.size - 1
    }

    private inner class EndlessScrollListener constructor(
        private val layoutManager: LinearLayoutManager
    ) : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (tempSize >= items.size) {
                return
            }
            if (dy < 0 || isLoading.get()) {
                return
            }
            val itemCount = layoutManager.itemCount
            val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
            val isBottom = lastVisiblePosition >= itemCount - 1
            if (isBottom) {
                recyclerView.post { loadMore() }
            }
        }
    }
}