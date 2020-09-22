package com.qihuan.wanandroid.biz.tree

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.common.ktx.adaptBars
import com.qihuan.wanandroid.common.ktx.dp
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.FragmentTreeBinding
import com.qihuan.wanandroid.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint

/**
 * SystemTreeFragment
 * @author qi
 * @since 2020/9/14
 */
@AndroidEntryPoint
class SystemTreeFragment : Fragment(R.layout.fragment_tree) {

    private val binding by viewBinding(FragmentTreeBinding::bind)
    private val viewModel by viewModels<SystemTreeViewModel>()
    private val firstAdapter by lazy { SystemTreeFirstAdapter() }
    private val secondAdapter by lazy { SystemTreeSecondAdapter() }
    private var selectionTracker: SelectionTracker<Long>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        selectionTracker?.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        selectionTracker?.onRestoreInstanceState(savedInstanceState)
    }

    private fun initView() {
        binding.root.adaptBars()
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        initFirstList()
        initSecondList()
    }

    private fun initFirstList() {
        binding.rvListFirst.adapter = firstAdapter
        selectionTracker = SelectionTracker.Builder(
            SystemTreeFragment::javaClass.name,
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
                    val position = getCurrentSelectionPosition()
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
                requireContext(),
                DividerItemDecoration.VERTICAL,
                10f.dp,
                10f.dp,
            )
        )

        secondAdapter.setOnItemClickListener { secondItem ->
            val currentPosition = getCurrentSelectionPosition() ?: return@setOnItemClickListener
            val firstItem = firstAdapter.currentList[currentPosition]

            findNavController().navigate(
                SystemTreeFragmentDirections.actionSystemTreeFragmentToTreeArticleFragment(
                    currentTreeId = secondItem.id,
                    systemNode = firstItem,
                )
            )
        }
    }

    private fun bindView() {
        viewModel.treeList.observe(viewLifecycleOwner) {
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

    private fun getCurrentSelectionPosition(): Int? {
        return selectionTracker?.selection?.firstOrNull()?.toInt()
    }
}