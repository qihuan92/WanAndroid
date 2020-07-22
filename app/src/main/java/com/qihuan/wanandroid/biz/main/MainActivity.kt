package com.qihuan.wanandroid.biz.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.qihuan.wanandroid.biz.home.HomeFragment
import com.qihuan.wanandroid.biz.knowledge.KnowledgeHierarchyFragment
import com.qihuan.wanandroid.biz.project.ProjectFragment
import com.qihuan.wanandroid.biz.user.UserFragment
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val tabList: List<TabContainer> by lazy {
        listOf(
            HomeFragment.Tab(),
            KnowledgeHierarchyFragment.Tab(),
            ProjectFragment.Tab(),
            UserFragment.Tab()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        binding.vpContent.adapter =
            MainPagerAdapter(
                this,
                tabList
            )
        TabLayoutMediator(
            binding.tabLayout,
            binding.vpContent,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.setIcon(tabList[position].icon())
            }).attach()
    }

    class MainPagerAdapter(
        fa: FragmentActivity,
        private val tabList: List<TabContainer>
    ) : FragmentStateAdapter(fa) {

        override fun getItemCount(): Int {
            return tabList.size
        }

        override fun createFragment(position: Int): Fragment {
            return tabList[position].createFragment()
        }
    }
}
