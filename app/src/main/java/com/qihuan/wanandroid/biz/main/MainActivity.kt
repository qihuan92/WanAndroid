package com.qihuan.wanandroid.biz.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.biz.home.HomeFragment
import com.qihuan.wanandroid.common.ktx.isDarkTheme
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStatusBar()
        setContentView(binding.root)
        initView()
    }

    private fun initStatusBar() {
        window.apply {
            statusBarColor = Color.TRANSPARENT
            if (isDarkTheme()) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    private fun initView() {
        supportFragmentManager.commit {
            replace(R.id.layout_content, obtainViewFragment())
        }
    }

    private fun obtainViewFragment() =
        supportFragmentManager.findFragmentById(R.id.layout_content) ?: HomeFragment()
}
