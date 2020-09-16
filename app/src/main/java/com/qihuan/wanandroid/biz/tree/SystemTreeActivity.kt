package com.qihuan.wanandroid.biz.tree

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.qihuan.wanandroid.common.ktx.dp
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.ActivityTreeBinding
import com.qihuan.wanandroid.widget.DividerItemDecoration
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
    private val firstAdapter by lazy { SystemTreeFirstAdapter() }
    private val secondAdapter by lazy { SystemTreeSecondAdapter() }
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

        initFirstList()
        initSecondList()
    }

    private fun initFirstList() {
        binding.rvListFirst.adapter = firstAdapter
        selectionTracker = SelectionTracker.Builder(
            SystemTreeActivity::javaClass.name,
            binding.rvListFirst,
            StableIdKeyProvider(binding.rvListFirst),
            SystemTreeFirstAdapter.ItemLookup(binding.rvListFirst),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            object : SelectionTracker.SelectionPredicate<Long>() {
                override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean {
                    return nextState
                }

                override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
                    return nextState
                }

                override fun canSelectMultiple(): Boolean {
                    return false
                }
            }
        ).build().apply {
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

    private fun initSecondList() {
        binding.rvListSecond.adapter = secondAdapter
        binding.rvListSecond.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL,
                10f.dp,
                10f.dp,
            )
        )
    }

    private fun bindView() {
        viewModel.treeList.observe(this) {
            if (it.isNotEmpty()) {
                firstAdapter.submitList(it) {
                    selectionTracker?.select(0)
                }
            }
        }
    }

    private fun onFirstSelected(position: Int?) {
        position?.let {
            val item = firstAdapter.currentList[position]
            val children = item.children
            secondAdapter.submitList(children)
        }
    }
}