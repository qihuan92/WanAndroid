package com.qihuan.wanandroid.biz.search

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialFadeThrough
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.biz.home.adapter.ArticlePageAdapter
import com.qihuan.wanandroid.common.adapter.DefaultLoadStateAdapter
import com.qihuan.wanandroid.common.ktx.hideKeyboard
import com.qihuan.wanandroid.common.ktx.setDefaultColors
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.FragmentSearchResultBinding
import dagger.hilt.android.AndroidEntryPoint
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
@AndroidEntryPoint
class SearchResultFragment : Fragment(R.layout.fragment_search_result) {

    private val binding by viewBinding(FragmentSearchResultBinding::bind)
    private val viewModel by viewModels<SearchResultViewModel>()
    private val args by navArgs<SearchResultFragmentArgs>()
    private lateinit var adapter: ArticlePageAdapter
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        }
        reenterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adaptBars()
        initView()
        bindView()
        hideKeyboard(binding.root)
    }

    private fun adaptBars() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.rvList) { view, insets ->
            val barInsets = insets.getInsets(
                WindowInsetsCompat.Type.ime() or WindowInsetsCompat.Type.navigationBars()
            )
            view.updatePadding(bottom = barInsets.bottom)
            insets
        }
    }

    private fun bindView() {
        search()
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
    }

    private fun search() {
        binding.rvList.scheduleLayoutAnimation()
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.search(args.searchText).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}