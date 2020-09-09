package com.qihuan.wanandroid.biz.home

import android.app.ActivityOptions
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.biz.home.adapter.ArticlePageAdapter
import com.qihuan.wanandroid.biz.home.adapter.HomeHeadAdapter
import com.qihuan.wanandroid.biz.search.SearchActivity
import com.qihuan.wanandroid.common.adapter.DefaultLoadStateAdapter
import com.qihuan.wanandroid.common.ktx.*
import com.qihuan.wanandroid.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * HomeFragment
 * @author qi
 * @since 2020/9/9
 */
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>()

    private val headAdapter by lazy { HomeHeadAdapter() }
    private val pageAdapter by lazy { ArticlePageAdapter() }
    private val adapter by lazy {
        ConcatAdapter().apply {
            addAdapter(headAdapter)
            addAdapter(pageAdapter.withLoadStateFooter(DefaultLoadStateAdapter(pageAdapter)))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindView()
    }

    private fun initView() {
        adaptNavigationBar()

        val layoutManager = LinearLayoutManager(context)
        binding.rvList.layoutManager = layoutManager
        binding.rvList.itemAnimator = DefaultItemAnimator()
        binding.rvList.adapter = adapter
        binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    binding.fabTop.hideInvisible()
                }
            }
        })

        binding.refreshLayout.apply {
            setProgressViewOffset(true, 50f.dp, 100f.dp)
            setDefaultColors()

            setOnRefreshListener {
                viewModel.refresh()
                pageAdapter.refresh()
            }
        }

        binding.fabTop.setOnClickListener {
            layoutManager.scrollToPositionWithOffset(0, 0)
        }

        binding.layoutSearch.setOnClickListener {
            startActivity(
                buildIntent<SearchActivity>(requireContext()) {},
                ActivityOptions.makeSceneTransitionAnimation(
                    requireActivity(),
                    binding.layoutSearch,
                    getString(R.string.transition_name_search)
                ).toBundle()
            )
        }
    }

    private fun adaptNavigationBar() {
        // 顶部 Padding 处理
        binding.root.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(top = insets.systemWindowInsetTop)
            insets
        }

        val fabTopMarginBottom = binding.fabTop.marginBottom
        // FAB 适配 NavigationBar
        binding.fabTop.setOnApplyWindowInsetsListener { view, insets ->
            view.updateLayoutParams {
                (this as ViewGroup.MarginLayoutParams).setMargins(
                    leftMargin,
                    topMargin,
                    rightMargin,
                    fabTopMarginBottom + insets.systemWindowInsetBottom
                )
            }
            insets
        }

        // 列表 Padding 处理
        binding.rvList.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(bottom = insets.systemWindowInsetBottom)
            insets
        }
    }

    private fun bindView() {
        viewModel.listLiveData.observe(viewLifecycleOwner, {
            binding.refreshLayout.isRefreshing = false
            headAdapter.submitList(it)
        })

        viewModel.getArticleList().observe(viewLifecycleOwner, {
            pageAdapter.submitData(lifecycle, it)
        })
    }
}