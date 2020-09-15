package com.qihuan.wanandroid.biz.tree

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.ActivityTreeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * TreeActivity
 * @author qi
 * @since 2020/9/14
 */
@AndroidEntryPoint
class SystemTreeActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityTreeBinding::inflate)
    private val viewModel by viewModels<SystemTreeViewModel>()
    private val adapter by lazy { SystemTreeAdapter() }
    private val firstAdapter by lazy { SystemTreeFirstAdapter() }
    private var selectionTracker: SelectionTracker<Long>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        bindView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        selectionTracker?.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        selectionTracker?.onRestoreInstanceState(savedInstanceState)
    }

    private fun initView() {
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }

        binding.rvListFirst.adapter = firstAdapter
        selectionTracker = SelectionTracker.Builder(
            SystemTreeActivity::javaClass.name,
            binding.rvListFirst,
            StableIdKeyProvider(binding.rvListFirst),
            SystemTreeFirstAdapter.ItemLookup(binding.rvListFirst),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectSingleAnything()
        ).build().apply {
            select(0)
            addObserver(object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    val position = selectionTracker?.selection?.firstOrNull()?.toInt()
                    onFirstSelected(position)
                }
            })
        }
        firstAdapter.tracker = selectionTracker
    }

    private fun bindView() {
        viewModel.treeList.observe(this) {
            firstAdapter.submitList(it)
        }
    }

    private fun onFirstSelected(position: Int?) {
        position?.let {
            // todo 刷新二级页面
        }
    }
}