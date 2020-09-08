package com.qihuan.wanandroid.biz.main

import android.app.ActivityOptions
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.biz.home.HomeViewModel
import com.qihuan.wanandroid.biz.home.adapter.ArticlePageAdapter
import com.qihuan.wanandroid.biz.home.adapter.HomeHeadAdapter
import com.qihuan.wanandroid.biz.search.SearchActivity
import com.qihuan.wanandroid.common.ApiResult
import com.qihuan.wanandroid.common.adapter.DefaultLoadStateAdapter
import com.qihuan.wanandroid.common.ktx.*
import com.qihuan.wanandroid.common.net.handleEvent
import com.qihuan.wanandroid.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by viewModels<HomeViewModel>()

    private val adapter by lazy { ConcatAdapter() }
    private val headAdapter by lazy { HomeHeadAdapter() }
    private val pageAdapter by lazy { ArticlePageAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyEdgeToEdge()
        setContentView(binding.root)
        initView()
        bindView()
        bindEvent()
    }

    private fun initView() {
        adaptNavigationBar()

        adapter.addAdapter(headAdapter)
        adapter.addAdapter(pageAdapter.withLoadStateFooter(DefaultLoadStateAdapter(pageAdapter)))

        val layoutManager = LinearLayoutManager(this)
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
                buildIntent<SearchActivity>(this) {},
                ActivityOptions.makeSceneTransitionAnimation(
                    this,
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
        viewModel.listLiveData.observe(this, {
            binding.refreshLayout.isRefreshing = false
            headAdapter.submitList(it)
        })

        viewModel.getArticleList().observe(this, {
            pageAdapter.submitData(lifecycle, it)
        })
    }

    private fun bindEvent() {
        // todo 全局异常处理
        handleEvent(ApiResult.Error::class).observe(this, {
            Toast.makeText(this, it.error.message, Toast.LENGTH_SHORT).show()
        })
    }
}
