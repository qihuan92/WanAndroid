package com.qihuan.wanandroid.biz.tree

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.qihuan.wanandroid.bean.SystemNode

/**
 * TreeArticleFragmentAdapter
 * @author qi
 * @since 2020/9/16
 */
class TreeArticleFragmentAdapter(
    fragment: Fragment,
    private val treeList: List<SystemNode> = emptyList()
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return treeList.size
    }

    override fun createFragment(position: Int): Fragment {
        val systemNode = treeList[position]
        val treeId = systemNode.id

        val fragment = TreeArticleListFragment()
        fragment.arguments = Bundle().apply {
            putLong("treeId", treeId)
        }
        return fragment
    }
}