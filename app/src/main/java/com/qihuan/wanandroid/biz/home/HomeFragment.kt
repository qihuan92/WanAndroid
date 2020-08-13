package com.qihuan.wanandroid.biz.home

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.biz.search.SearchActivity
import com.qihuan.wanandroid.common.adapter.PageMultiTypeAdapter
import com.qihuan.wanandroid.common.ktx.dp
import com.qihuan.wanandroid.common.ktx.hideInvisible
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * HomeFragment
 * @author qi
 * @since 2020/6/28
 */
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var adapter: PageMultiTypeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindView()
    }

    private fun initView() {
        adapter = PageMultiTypeAdapter()
        adapter.register(HomeBannerViewDelegate(this))
        adapter.register(ArticleItemViewBinder())
        adapter.register(HomeTitleViewBinder())

        binding.apply {
            val layoutManager = LinearLayoutManager(context)
            rvList.layoutManager = layoutManager
            rvList.itemAnimator = DefaultItemAnimator()
            rvList.adapter = adapter
            rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                        fabTop.hideInvisible()
                    }
                }
            })

            refreshLayout.apply {
                setProgressViewOffset(true, 50f.dp, 100f.dp)

                setColorSchemeResources(
                    R.color.colorPrimary,
                    R.color.colorPrimaryDark,
                    R.color.colorAccent
                )

                setOnRefreshListener {
                    viewModel.refresh()
                }
            }

            adapter.setOnLoadMoreListener {
                viewModel.loadMore()
            }

            fabTop.setOnClickListener {
                layoutManager.scrollToPositionWithOffset(0, 0)
            }

            layoutSearch.setOnClickListener {
                startActivity(
                    Intent(context, SearchActivity::class.java),
                    ActivityOptions.makeSceneTransitionAnimation(
                        activity,
                        binding.layoutSearch,
                        getString(R.string.transition_name_search)
                    ).toBundle()
                )
            }
        }
    }

    private fun bindView() {
        viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            binding.refreshLayout.isRefreshing = false
            adapter.loadMoreComplete()
            adapter.setData(it)
        })
    }
}