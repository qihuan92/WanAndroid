package com.qihuan.wanandroid.biz.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * LoginActivity
 * @author qi
 * @since 2020/9/21
 */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityLoginBinding::inflate)
    private val tabs by lazy {
        mutableListOf(
            object : TabContainer {
                override fun title() = "登录"
                override fun fragment() = LoginFragment()
            },
            object : TabContainer {
                override fun title() = "注册"
                override fun fragment() = RegisterFragment()
            },
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        binding.vpContent.adapter = FragmentAdapter(this, tabs)

        TabLayoutMediator(binding.tabLayout, binding.vpContent) { tab, position ->
            tab.text = tabs[position].title()
        }.attach()
    }

    interface TabContainer {
        fun title(): String
        fun fragment(): Fragment
    }

    class FragmentAdapter(
        fa: FragmentActivity,
        private val tabs: MutableList<TabContainer>
    ) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return tabs.size
        }

        override fun createFragment(position: Int): Fragment {
            return tabs[position].fragment()
        }
    }
}