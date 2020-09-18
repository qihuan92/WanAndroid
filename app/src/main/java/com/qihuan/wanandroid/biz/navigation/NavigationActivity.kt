package com.qihuan.wanandroid.biz.navigation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.qihuan.wanandroid.common.ktx.setDefaultColors
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.ActivityNavigationBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * NavigationActivity
 * @author qi
 * @since 2020/9/16
 */
@AndroidEntryPoint
class NavigationActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityNavigationBinding::inflate)
    private val viewModel by viewModels<NavigationViewModel>()
    private val adapter by lazy { NavigationAdapter() }

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
            viewModel.getNavigation()
        }

        binding.rvList.adapter = adapter
    }

    private fun bindView() {
        viewModel.navigationData.observe(this) {
            binding.rvList.scheduleLayoutAnimation()
            binding.refreshLayout.isRefreshing = false
            adapter.submitList(it)
        }
    }
}