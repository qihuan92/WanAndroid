package com.qihuan.wanandroid.biz.search

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.biz.home.ArticleItemViewBinder
import com.qihuan.wanandroid.biz.home.HomeBannerViewDelegate
import com.qihuan.wanandroid.common.adapter.PageMultiTypeAdapter
import com.qihuan.wanandroid.common.ktx.dp
import com.qihuan.wanandroid.common.ktx.extra
import com.qihuan.wanandroid.common.ktx.transparentStatusBar
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.ActivitySearchResultBinding
import com.qihuan.wanandroid.widget.MultiTypeDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint

/**
 * SearchResultActivity
 * @author qi
 * @since 2020/8/10
 */
@AndroidEntryPoint
class SearchResultActivity : AppCompatActivity() {

    companion object Extra {
        const val SEARCH_TEXT = "searchText"
    }

    private val binding by viewBinding(ActivitySearchResultBinding::inflate)
    private val viewModel by viewModels<SearchResultViewModel>()
    private val searchText by extra<String>(SEARCH_TEXT)

    private lateinit var adapter: PageMultiTypeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusBar()
        setContentView(binding.root)
        initData()
        initTitleBar()
        initList()
    }

    private fun initData() {
        viewModel.searchText = searchText.orEmpty()
    }

    private fun initTitleBar() {
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.toolbar.setOnClickListener { onBackPressed() }
        binding.toolbar.title = searchText
        viewModel.refresh()
    }

    private fun initList() {
        adapter = PageMultiTypeAdapter()
        adapter.register(HomeBannerViewDelegate(this))
        adapter.register(ArticleItemViewBinder())
        adapter.setOnLoadMoreListener {
            viewModel.loadMore()
        }

        binding.apply {
            val layoutManager = LinearLayoutManager(this@SearchResultActivity)
            rvList.layoutManager = layoutManager
            rvList.addItemDecoration(
                MultiTypeDividerItemDecoration(
                    this@SearchResultActivity,
                    LinearLayout.VERTICAL,
                    15f.dp,
                    15f.dp,
                    adapter,
                    arrayOf(Article::class.java)
                )
            )
            rvList.itemAnimator = DefaultItemAnimator()
            rvList.adapter = adapter

            refreshLayout.apply {
                setColorSchemeResources(
                    R.color.colorPrimary,
                    R.color.colorPrimaryDark,
                    R.color.colorAccent
                )

                setOnRefreshListener {
                    viewModel.refresh()
                }
            }
        }

        viewModel.listLiveData.observe(this, Observer {
            binding.refreshLayout.isRefreshing = false
            adapter.loadMoreComplete()
            adapter.items = it
            adapter.notifyDataSetChanged()
        })
    }
}