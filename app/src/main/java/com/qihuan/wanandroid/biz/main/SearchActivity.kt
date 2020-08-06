package com.qihuan.wanandroid.biz.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qihuan.wanandroid.common.ktx.transparentStatusBar
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * SearchActivity
 * @author qi
 * @since 2020/8/6
 */
@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySearchBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusBar()
        setContentView(binding.root)
        initSearchView()
    }

    private fun initSearchView() {
        binding.btnBack.setOnClickListener { onBackPressed() }
        binding.etSearch.requestFocus()
    }
}