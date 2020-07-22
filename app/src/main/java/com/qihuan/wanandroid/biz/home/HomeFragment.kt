package com.qihuan.wanandroid.biz.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.biz.main.TabContainer
import com.qihuan.wanandroid.common.UIResult
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.FragmentHomeBinding
import com.qihuan.wanandroid.utils.LoadMoreDelegate
import dagger.hilt.android.AndroidEntryPoint

/**
 * HomeFragment
 * @author qi
 * @since 2020/6/28
 */
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: MultiTypeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView() {
        adapter = MultiTypeAdapter()
        adapter.register(HomeBannerViewDelegate())
        adapter.register(ArticleItemViewBinder())

        binding.apply {
            rvList.layoutManager = LinearLayoutManager(context)
            rvList.adapter = adapter
            refreshLayout.setOnRefreshListener {
                viewModel.refresh()
            }
            LoadMoreDelegate(
                isLoading = { return@LoadMoreDelegate viewModel.isLoading.get() },
                onLoadMore = { viewModel.loadMore() }
            ).attach(rvList)
        }
    }

    private fun initListener() {
        viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is UIResult.Success -> {
                    if (binding.refreshLayout.isRefreshing) {
                        binding.refreshLayout.isRefreshing = false
                    }
                    adapter.items = it.data
                    adapter.notifyDataSetChanged()
                }
                else -> {

                }
            }
        })
    }

    class Tab : TabContainer {
        override fun title(): String {
            return "首页"
        }

        override fun icon(): Int {
            return R.drawable.ic_tab_home
        }

        override fun createFragment(): Fragment {
            return HomeFragment()
        }
    }
}