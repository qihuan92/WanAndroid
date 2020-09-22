package com.qihuan.wanandroid.biz.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.core.widget.addTextChangedListener
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.qihuan.wanandroid.R
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
    private val navController: NavController by lazy {
        findNavController(R.id.layout_content)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContentView(binding.root)
        initSearchView()
    }

    private fun adaptNavigationBar() {
        // 顶部 Padding 处理
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.updatePadding(top = statusBarInsets.top)
            insets
        }
    }

    private fun initSearchView() {
        adaptNavigationBar()
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
                val searchText = binding.etSearch.text?.toString()
                if (searchText == null || searchText.isBlank()) {
                    return@setOnEditorActionListener false
                }
                navController.navigate(
                    SearchRecommendFragmentDirections.actionGlobalSearchResultFragment(
                        searchText
                    )
                )
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    fun setSearchText(searchText: String) {
        binding.etSearch.setText(searchText)
        binding.etSearch.setSelection(searchText.length)
    }
}