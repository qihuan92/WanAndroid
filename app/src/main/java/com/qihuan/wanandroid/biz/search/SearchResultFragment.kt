package com.qihuan.wanandroid.biz.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.biz.home.ArticlePageAdapter
import com.qihuan.wanandroid.common.ktx.hideKeyboard
import com.qihuan.wanandroid.common.ktx.setDefaultColors
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.FragmentSearchResultBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
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
    }

    private fun initView() {
        adapter = ArticlePageAdapter()

        binding.apply {
            val layoutManager = LinearLayoutManager(context)
            rvList.layoutManager = layoutManager
            rvList.itemAnimator = DefaultItemAnimator()
            rvList.adapter = adapter

            refreshLayout.setDefaultColors()
            refreshLayout.setOnRefreshListener {
                search()
            }
        }

        hideKeyboard()
    }

    private fun search() {
        binding.refreshLayout.isRefreshing = false
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.search().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}