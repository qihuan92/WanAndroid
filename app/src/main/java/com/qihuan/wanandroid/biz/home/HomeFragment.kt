package com.qihuan.wanandroid.biz.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.drakeet.multitype.MultiTypeAdapter
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.BannerBean
import com.qihuan.wanandroid.bean.BannerList
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.FragmentHomeBinding
import com.qihuan.wanandroid.utils.LoadMoreDelegate
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
    private lateinit var adapter: MultiTypeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    private fun initView() {
        adapter = MultiTypeAdapter()
        adapter.register(HomeBannerViewDelegate(this))
        adapter.register(ArticleItemViewBinder())

        binding.apply {
            rvList.layoutManager = LinearLayoutManager(context)
//            rvList.addItemDecoration(RecyclerViewItemDecoration.Builder(context)
//                .paddingStart(15f.dp)
//                .thickness(1f.dp)
//                .color(ContextCompat.getColor(context, android.R.attr.dividerVertical))
//                .ignoreTypes(0)
//                .create())
            rvList.itemAnimator = DefaultItemAnimator()
            rvList.adapter = adapter
            refreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent
            )
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
            val (oldList, newList) = it
            if (binding.refreshLayout.isRefreshing) {
                binding.refreshLayout.isRefreshing = false
            }
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