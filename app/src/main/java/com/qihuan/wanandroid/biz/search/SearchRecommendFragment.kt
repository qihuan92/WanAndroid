package com.qihuan.wanandroid.biz.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.bean.HistorySearchKey
import com.qihuan.wanandroid.bean.SearchKey
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.FragmentSearchRecommendBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * SearchRecommendFragment
 * @author qi
 * @since 2020/8/6
 */
@AndroidEntryPoint
class SearchRecommendFragment : Fragment(R.layout.fragment_search_recommend) {
    private val binding by viewBinding(FragmentSearchRecommendBinding::bind)
    private val viewModel by activityViewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        viewModel.hotKeys.observe(viewLifecycleOwner, Observer {
            bindHotKeys(it)
        })
        viewModel.historyKeys.observe(viewLifecycleOwner, Observer {
            bindHistoryKeys(it)
        })
    }

    private fun bindHotKeys(keys: List<SearchKey>) {
        binding.cgHotSearch.apply {
            removeAllViews()
            for (key in keys) {
                addView(buildHotChip(key.name))
            }
        }
    }

    private fun buildHotChip(key: String): Chip {
        return Chip(context).apply {
            text = key
            setOnClickListener {
                viewModel.searchText.set(key)
                findNavController().navigate(
                    SearchRecommendFragmentDirections.actionSearchRecommendFragmentToSearchResultFragment()
                )
            }
        }
    }

    private fun bindHistoryKeys(keys: List<HistorySearchKey>) {
        binding.tvClearHistory.isGone = keys.isNullOrEmpty()
        binding.tvTitleHistory.isGone = keys.isNullOrEmpty()
        binding.tvClearHistory.setOnClickListener {
            context?.let { context ->
                MaterialAlertDialogBuilder(context)
                    .setTitle(R.string.alert)
                    .setMessage(R.string.msg_delete_all_history_keys)
                    .setPositiveButton(R.string.confirm) { dialog, _ ->
                        viewModel.deleteAll()
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.cancel) { dialog, _ ->
                        dialog.cancel()
                    }
                    .show()
            }
        }
        binding.cgHistorySearch.apply {
            removeAllViews()
            for (key in keys) {
                addView(buildHistoryChip(key.name))
            }
        }
    }

    private fun buildHistoryChip(key: String): Chip {
        return Chip(context).apply {
            text = key
            isCloseIconVisible = true
            setOnCloseIconClickListener {
                viewModel.deleteKey(key)
            }
            setOnClickListener {
                viewModel.searchText.set(key)
                findNavController().navigate(
                    SearchRecommendFragmentDirections.actionSearchRecommendFragmentToSearchResultFragment()
                )
            }
        }
    }
}