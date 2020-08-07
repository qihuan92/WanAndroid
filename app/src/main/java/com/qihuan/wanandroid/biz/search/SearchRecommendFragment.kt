package com.qihuan.wanandroid.biz.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.bean.SearchKey
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.FragemtSearchRecommendBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * SearchRecommendFragment
 * @author qi
 * @since 2020/8/6
 */
@AndroidEntryPoint
class SearchRecommendFragment : Fragment(R.layout.fragemt_search_recommend) {
    private val binding: FragemtSearchRecommendBinding by viewBinding(FragemtSearchRecommendBinding::bind)
    private val viewModel by activityViewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        viewModel.hotKeys.observe(viewLifecycleOwner, Observer {
            bindHotKeys(it)
        })
    }

    private fun bindHotKeys(keys: List<SearchKey>) {
        binding.cgHotSearch.apply {
            removeAllViews()
            for (key in keys) {
                addView(buildChip(key))
            }
        }
    }

    private fun buildChip(key: SearchKey): Chip {
        return Chip(context).apply {
            text = key.name
            setOnClickListener {
                viewModel.searchText.set(key.name)
            }
        }
    }
}