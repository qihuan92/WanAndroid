package com.qihuan.wanandroid.biz.home

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialSharedAxis
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.biz.home.adapter.ArticlePageAdapter
import com.qihuan.wanandroid.biz.home.adapter.HomeHeadAdapter
import com.qihuan.wanandroid.biz.user.LoginActivity
import com.qihuan.wanandroid.common.adapter.DefaultLoadStateAdapter
import com.qihuan.wanandroid.common.ktx.*
import com.qihuan.wanandroid.databinding.FragmentHomeBinding
import com.qihuan.wanandroid.widget.animation.SpringAddItemAnimator
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
        ConcatAdapter(
            ConcatAdapter.Config.Builder()
                .setIsolateViewTypes(false)
                .build()
        ).apply {
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
        binding.rvList.adapter = adapter
        binding.rvList.itemAnimator = SpringAddItemAnimator()
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
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
                duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
            }
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
                duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
            }

            val directions = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            findNavController().navigate(directions)
        }

        // todo 测试
        binding.ivHead.setOnClickListener {
            startActivity(
                buildIntent<LoginActivity>(requireContext()) { }
            )
        }
    }

    private fun adaptNavigationBar() {
        // 顶部 Padding 处理
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val statusBarInserts = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.updatePadding(top = statusBarInserts.top)
            insets
        }

        val fabTopMarginBottom = binding.fabTop.marginBottom
        // FAB 适配 NavigationBar
        ViewCompat.setOnApplyWindowInsetsListener(binding.fabTop) { view, insets ->
            val navigationBarInserts = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            view.updateLayoutParams {
                (this as ViewGroup.MarginLayoutParams).setMargins(
                    leftMargin,
                    topMargin,
                    rightMargin,
                    fabTopMarginBottom + navigationBarInserts.bottom
                )
            }
            insets
        }

        // 列表 Padding 处理
        ViewCompat.setOnApplyWindowInsetsListener(binding.rvList) { view, insets ->
            val navigationBarInserts = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            view.updatePadding(bottom = navigationBarInserts.bottom)
            insets
        }
    }

    private fun bindView() {
        viewModel.listLiveData.observe(viewLifecycleOwner, {
            binding.refreshLayout.isRefreshing = false
            headAdapter.submitList(it)
        })

        viewModel.pageLiveData.observe(viewLifecycleOwner, {
            pageAdapter.submitData(lifecycle, it)
        })
    }
}