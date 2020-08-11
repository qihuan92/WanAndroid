package com.qihuan.wanandroid.biz.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.qihuan.wanandroid.R
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
    private val viewModel by viewModels<SearchViewModel>()
    private val navController: NavController by lazy {
        findNavController(R.id.layout_content)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusBar()
        setContentView(binding.root)
        binding.viewModel = viewModel
        initSearchView()
    }

    private fun initSearchView() {
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.etSearch.requestFocus()
        binding.etSearch.addTextChangedListener {
            val searchText = it?.toString().orEmpty()
            if (searchText.isEmpty()) {
                if (navController.currentDestination?.id == R.id.searchResultFragment) {
                    onBackPressed()
                }
            }
        }
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchText = viewModel.searchText.get()
                if (searchText == null || searchText.isBlank()) {
                    return@setOnEditorActionListener false
                }

                if (navController.currentDestination?.id == R.id.searchRecommendFragment) {
                    navController.navigate(
                        SearchRecommendFragmentDirections.actionSearchRecommendFragmentToSearchResultFragment()
                    )
                }
                viewModel.searchEvent.call()
            }
            return@setOnEditorActionListener false
        }
    }
}