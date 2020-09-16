package com.qihuan.wanandroid.biz.tree

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.qihuan.wanandroid.bean.SystemNode
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.ActivityTreeArticleBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * TreeArticleActivity
 * @author qi
 * @since 2020/9/16
 */
@AndroidEntryPoint
class TreeArticleActivity : AppCompatActivity() {

    private var systemNode: SystemNode? = null
    private var currentTreeId: Long? = -1
    private val binding by viewBinding(ActivityTreeArticleBinding::inflate)
    private lateinit var adapter: TreeArticleFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        systemNode = intent.getParcelableExtra("systemNode")
        requireNotNull(systemNode)

        currentTreeId = intent.getLongExtra("currentTreeId", -1)

        initView()
    }

    private fun initView() {
        binding.toolbar.title = systemNode?.name
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        val children = systemNode?.children.orEmpty()
        adapter = TreeArticleFragmentAdapter(this, children)
        binding.vpContent.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.vpContent) { tab, position ->
            tab.text = children[position].name
        }.attach()

        val index = children.indexOfFirst {
            currentTreeId == it.id
        }
        binding.vpContent.setCurrentItem(index, false)
    }
}