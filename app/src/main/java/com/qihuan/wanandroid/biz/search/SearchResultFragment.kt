package com.qihuan.wanandroid.biz.search

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.biz.home.ArticleItemViewBinder
import com.qihuan.wanandroid.biz.home.HomeBannerViewDelegate
import com.qihuan.wanandroid.common.ktx.dp
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.FragmentSearchResultBinding
import com.qihuan.wanandroid.utils.LoadMoreDelegate
import com.qihuan.wanandroid.widget.MultiTypeDividerItemDecoration

/**
 * SearchResultFragment
 * @author qi
 * @since 2020/8/7
 */
class SearchResultFragment : Fragment(R.layout.fragment_search_result) {

    private val binding by viewBinding(FragmentSearchResultBinding::bind)
    private val viewModel by activityViewModels<SearchViewModel>()
    private lateinit var adapter: MultiTypeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindView()
    }

    private fun bindView() {
        viewModel.searchEvent.observe(viewLifecycleOwner, Observer {
            viewModel.refresh()
        })

        viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            if (binding.refreshLayout.isRefreshing) {
                binding.refreshLayout.isRefreshing = false
            }
            adapter.items = it
            adapter.notifyDataSetChanged()
        })
    }

    private fun initView() {
        adapter = MultiTypeAdapter()
        adapter.register(HomeBannerViewDelegate(this))
        adapter.register(ArticleItemViewBinder())

        binding.apply {
            val layoutManager = LinearLayoutManager(context)
            rvList.layoutManager = layoutManager
            rvList.addItemDecoration(
                MultiTypeDividerItemDecoration(
                    context,
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

            LoadMoreDelegate(
                isLoading = { return@LoadMoreDelegate viewModel.isLoading.get() },
                onLoadMore = { viewModel.loadMore() }
            ).attach(rvList)
        }
    }
}