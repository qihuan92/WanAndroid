package com.qihuan.wanandroid.biz.qa

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.LoadState
import com.qihuan.wanandroid.biz.home.adapter.ArticlePageAdapter
import com.qihuan.wanandroid.common.adapter.DefaultLoadStateAdapter
import com.qihuan.wanandroid.common.ktx.setDefaultColors
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.ActivityQaBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * QaActivity
 * @author qi
 * @since 2020/9/17
 */
@AndroidEntryPoint
class QaActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityQaBinding::inflate)
    private val viewModel by viewModels<QaViewModel>()
    private val adapter by lazy { ArticlePageAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        bindView()
    }

    private fun initView() {
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        binding.refreshLayout.setDefaultColors()
        binding.refreshLayout.setOnRefreshListener {
            adapter.refresh()
        }

        binding.rvList.adapter = adapter.withLoadStateFooter(DefaultLoadStateAdapter(adapter))
    }

    private fun bindView() {
        viewModel.qaData.observe(this) {
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