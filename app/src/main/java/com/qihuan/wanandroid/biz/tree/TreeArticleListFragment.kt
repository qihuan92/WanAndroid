package com.qihuan.wanandroid.biz.tree

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.biz.home.adapter.ArticlePageAdapter
import com.qihuan.wanandroid.common.adapter.DefaultLoadStateAdapter
import com.qihuan.wanandroid.common.ktx.adaptBottomBar
import com.qihuan.wanandroid.common.ktx.setDefaultColors
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.FragmentTreeArticleListBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * TreeArticleListFragment
 * @author qi
 * @since 2020/9/16
 */
@AndroidEntryPoint
class TreeArticleListFragment : Fragment(R.layout.fragment_tree_article_list) {

    private var treeId: Long? = null
    private val binding by viewBinding(FragmentTreeArticleListBinding::bind)
    private val viewModel by viewModels<TreeArticleViewModel>()
    private val adapter by lazy { ArticlePageAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        treeId = arguments?.getLong("treeId")

        initView()
        bindView()
        binding.rvList.adaptBottomBar()
    }

    private fun initView() {
        binding.rvList.adapter = adapter.withLoadStateFooter(DefaultLoadStateAdapter(adapter))
        binding.refreshLayout.setDefaultColors()
        binding.refreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun bindView() {
        viewModel.getArticleList(treeId)
        viewModel.articlePage.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }

        adapter.addLoadStateListener {
            binding.refreshLayout.isRefreshing = it.refresh is LoadState.Loading
        }

        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading) {
                binding.rvList.scheduleLayoutAnimation()
            }
        }
    }
}