package com.qihuan.wanandroid.biz.home

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.BannerBean
import com.qihuan.wanandroid.bean.BannerList
import com.qihuan.wanandroid.biz.search.SearchActivity
import com.qihuan.wanandroid.common.adapter.PageMultiTypeAdapter
import com.qihuan.wanandroid.common.ktx.dp
import com.qihuan.wanandroid.common.ktx.hideInvisible
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.FragmentHomeBinding
import com.qihuan.wanandroid.widget.MultiTypeDividerItemDecoration
import com.qihuan.wanandroid.widget.TabContainer
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
    private lateinit var adapter: PageMultiTypeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView() {
        adapter = PageMultiTypeAdapter()
        adapter.register(HomeBannerViewDelegate(this))
        adapter.register(ArticleItemViewBinder())
        adapter.setOnLoadMoreListener {
            viewModel.loadMore()
        }

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

            fabTop.setOnClickListener {
                val threshold = 50
                if (adapter.itemCount <= threshold) {
                    rvList.smoothScrollToPosition(0)
                } else {
                    layoutManager.scrollToPositionWithOffset(0, 0)
                }
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

    private fun initListener() {
        viewModel.listLiveData.observe(viewLifecycleOwner, Observer {
            val (oldList, newList) = it
            binding.refreshLayout.isRefreshing = false
            adapter.loadMoreComplete()
            DiffUtil.calculateDiff(ItemDiffCallback(oldList, newList)).dispatchUpdatesTo(adapter)
            adapter.items = newList
        })
    }

    private class ItemDiffCallback(
        private val oldList: List<Any>,
        private val newList: List<Any>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return if (oldItem is Article && newItem is Article) {
                oldItem.id == newItem.id
            } else if (oldItem is BannerList && newItem is BannerList) {
                bannerListEqual(oldItem.list, newItem.list)
            } else {
                oldItem == newItem
            }
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return if (oldItem is Article && newItem is Article) {
                oldItem.title == newItem.title
            } else if (oldItem is BannerList && newItem is BannerList) {
                bannerListEqual(oldItem.list, newItem.list)
            } else {
                oldItem == newItem
            }
        }

        private fun bannerListEqual(oldList: List<BannerBean>, newList: List<BannerBean>): Boolean {
            if (oldList.size != newList.size) {
                return false
            }
            val zip = oldList.zip(newList)
            return zip.all { (oldItem, newItem) ->
                oldItem.id == newItem.id
            }
        }
    }

    @Suppress("unused")
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