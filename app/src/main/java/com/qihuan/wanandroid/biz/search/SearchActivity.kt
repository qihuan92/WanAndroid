package com.qihuan.wanandroid.biz.search

import android.app.ActivityOptions
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import com.qihuan.wanandroid.bean.SearchKey
import com.qihuan.wanandroid.common.ktx.buildIntent
import com.qihuan.wanandroid.common.ktx.transparentStatusBar
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * SearchActivity
 * @author qi
 * @since 2020/8/6
 */
@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySearchBinding::inflate)
    private val viewModel by viewModels<SearchRecommendViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusBar()
        setContentView(binding.root)
        initSearchView()
        bindView()
    }

    private fun initSearchView() {
        binding.btnBack.setOnClickListener { onBackPressed() }
        binding.etSearch.requestFocus()
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchText = binding.etSearch.text.toString().trim()
                if (searchText.isBlank()) {
                    return@setOnEditorActionListener false
                }

                navToResultPage()
            }
            return@setOnEditorActionListener false
        }
    }

    private fun bindView() {
        viewModel.hotKeys.observe(this, Observer {
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
        return Chip(this).apply {
            text = key.name
            setOnClickListener {
                binding.etSearch.setText(key.name)
                navToResultPage()
            }
        }
    }

    private fun navToResultPage() {
        val searchText = binding.etSearch.text.toString().trim()
        startActivity(
            buildIntent<SearchResultActivity>(this) {
                putExtra(SearchResultActivity.SEARCH_TEXT, searchText)
            },
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
        )
    }
}