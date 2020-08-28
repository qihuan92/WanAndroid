package com.qihuan.wanandroid.biz.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.biz.home.ArticlePageAdapter
import com.qihuan.wanandroid.common.adapter.DefaultLoadStateAdapter
import com.qihuan.wanandroid.common.ktx.hideKeyboard
import com.qihuan.wanandroid.common.ktx.setDefaultColors
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.FragmentSearchResultBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

/**
 * SearchResultFragment
 * @author qi
 * @since 2020/8/7
 */
class SearchResultFragment : Fragment(R.layout.fragment_search_result) {

    private val binding by viewBinding(FragmentSearchResultBinding::bind)
    private val viewModel by activityViewModels<SearchViewModel>()
    private lateinit var adapter: ArticlePageAdapter
    private var searchJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindView()
    }

    private fun bindView() {
        search()
        viewModel.searchEvent.observe(viewLifecycleOwner) {
            search()
        }
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest {
                binding.refreshLayout.isRefreshing = it.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rvList.scrollToPosition(0) }
        }
    }

    private fun initView() {
        adapter = ArticlePageAdapter()

        val layoutManager = LinearLayoutManager(context)
        binding.rvList.layoutManager = layoutManager
        binding.rvList.itemAnimator = DefaultItemAnimator()
        binding.rvList.adapter = adapter.withLoadStateFooter(DefaultLoadStateAdapter(adapter))

        binding.refreshLayout.setDefaultColors()
        binding.refreshLayout.setOnRefreshListener {
            adapter.refresh()
        }

        hideKeyboard()
    }

    private fun search() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.search().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}