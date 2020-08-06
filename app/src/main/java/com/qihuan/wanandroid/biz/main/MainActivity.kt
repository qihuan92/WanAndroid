package com.qihuan.wanandroid.biz.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.biz.home.HomeFragment
import com.qihuan.wanandroid.common.ktx.transparentStatusBar
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusBar()
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        supportFragmentManager.commit {
            replace(R.id.layout_content, obtainViewFragment())
        }
    }

    private fun obtainViewFragment() =
        supportFragmentManager.findFragmentById(R.id.layout_content) ?: HomeFragment()
}
