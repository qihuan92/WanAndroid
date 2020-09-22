package com.qihuan.wanandroid.biz.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.ActivityLoginBinding

/**
 * LoginAActivity
 * @author qi
 * @since 2020/9/21
 */
class LoginAActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityLoginBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        TODO("Not yet implemented")
    }
}