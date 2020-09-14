package com.qihuan.wanandroid.biz.tree

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.ActivityTreeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * TreeActivity
 * @author qi
 * @since 2020/9/14
 */
@AndroidEntryPoint
class SystemTreeActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityTreeBinding::inflate)
    private val viewModel by viewModels<SystemTreeViewModel>()
    private val adapter by lazy { SystemTreeAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        bindView()
    }

    private fun initView() {
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        binding.rvList.adapter = adapter
    }

    private fun bindView() {
        viewModel.treeList.observe(this) {
            adapter.submitList(it)
        }
    }
}