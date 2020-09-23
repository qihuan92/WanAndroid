package com.qihuan.wanandroid.biz.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.transition.MaterialSharedAxis
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.common.ktx.hideKeyboard
import com.qihuan.wanandroid.common.ktx.showKeyboard
import com.qihuan.wanandroid.common.ktx.viewBinding
import com.qihuan.wanandroid.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * SearchFragment
 * @author qi
 * @since 2020/8/6
 */
@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val navController: NavController by lazy {
        binding.layoutContent.findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchView()
    }

    private fun initSearchView() {
        binding.btnBack.setOnClickListener {
            hideKeyboard(binding.etSearch)
            requireActivity().onBackPressed()
        }
        binding.etSearch.post {
            binding.etSearch.requestFocus()
            showKeyboard(binding.etSearch)
        }

        binding.etSearch.addTextChangedListener {
            val searchText = it?.toString().orEmpty()
            if (searchText.isEmpty()) {
                if (navController.currentDestination?.id == R.id.searchResultFragment) {
                    requireActivity().onBackPressed()
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