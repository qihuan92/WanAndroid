package com.qihuan.wanandroid.biz.tree

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.common.ktx.adaptStatusBar
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.FragmentTreeArticleBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * TreeArticleFragment
 * @author qi
 * @since 2020/9/16
 */
@AndroidEntryPoint
class TreeArticleFragment : Fragment(R.layout.fragment_tree_article) {

    private val binding by viewBinding(FragmentTreeArticleBinding::bind)
    private val args by navArgs<TreeArticleFragmentArgs>()
    private lateinit var adapter: TreeArticleFragmentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val systemNode = args.systemNode
        val currentTreeId = args.currentTreeId

        binding.root.adaptStatusBar()
        binding.toolbar.title = systemNode.name
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        val children = systemNode.children
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