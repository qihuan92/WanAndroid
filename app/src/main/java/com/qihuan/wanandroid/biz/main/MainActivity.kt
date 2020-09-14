package com.qihuan.wanandroid.biz.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.qihuan.wanandroid.common.ApiResult
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.common.net.handleEvent
import com.qihuan.wanandroid.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)
        bindEvent()
    }

    private fun bindEvent() {
        // todo 全局异常处理
        handleEvent(ApiResult.Error::class).observe(this, {
            Toast.makeText(this, it.error.message, Toast.LENGTH_SHORT).show()
        })
    }
}
