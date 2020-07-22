package com.qihuan.wanandroid.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author drakeet
 */
class LoadMoreDelegate(
    private val isLoading: () -> Boolean,
    private val onLoadMore: () -> Unit
) {

    /**
     * Should be called after recyclerView setup with its layoutManager and adapter
     *
     * @param recyclerView the RecyclerView
     */
    fun attach(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
        recyclerView.addOnScrollListener(
            EndlessScrollListener(
                layoutManager,
                isLoading,
                onLoadMore
            )
        )
    }

    private class EndlessScrollListener constructor(
        private val layoutManager: LinearLayoutManager?,
        private val isLoading: () -> Boolean,
        private val onLoadMore: () -> Unit
    ) : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dy < 0 || isLoading.invoke()) return
            val itemCount = layoutManager!!.itemCount
            val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
            val isBottom =
                lastVisiblePosition >= itemCount - VISIBLE_THRESHOLD
            if (isBottom) {
                onLoadMore.invoke()
            }
        }

        companion object {
            private const val VISIBLE_THRESHOLD = 6
        }

    }
}