package com.qihuan.wanandroid.biz.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.biz.home.ArticleItemViewBinder
import com.qihuan.wanandroid.biz.home.HomeBannerViewDelegate
import com.qihuan.wanandroid.common.adapter.PageMultiTypeAdapter
import com.qihuan.wanandroid.common.ktx.hideKeyboard
import com.qihuan.wanandroid.common.ktx.setDefaultColors
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.FragmentSearchResultBinding

/**
 * SearchResultFragment
 * @author qi
 * @since 2020/8/7
 */
class SearchResultFragment : Fragment(R.layout.fragment_search_result) {

    private val binding by viewBinding(FragmentSearchResultBinding::bind)
    private val viewModel by activityViewModels<SearchViewModel>()
    private lateinit var adapter: PageMultiTypeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindView()
    }

    private fun bindView() {
        viewModel.refresh()
        viewModel.searchEvent.observe(viewLifecycleOwner, Observer {
            viewModel.refresh()
        })

        viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            binding.refreshLayout.isRefreshing = false
            adapter.loadMoreComplete()
            adapter.items = it
        })
    }

    private fun initView() {
        adapter = PageMultiTypeAdapter()
        adapter.register(HomeBannerViewDelegate(this))
        adapter.register(ArticleItemViewBinder())
        adapter.setOnLoadMoreListener {
            viewModel.loadMore()
        }

        binding.apply {
            val layoutManager = LinearLayoutManager(context)
            rvList.layoutManager = layoutManager
            rvList.itemAnimator = DefaultItemAnimator()
            rvList.adapter = adapter

            refreshLayout.setDefaultColors()
            refreshLayout.setOnRefreshListener {
                viewModel.refresh()
            }
        }

        hideKeyboard()
    }
}