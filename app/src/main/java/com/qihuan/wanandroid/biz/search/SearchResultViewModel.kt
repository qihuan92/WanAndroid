package com.qihuan.wanandroid.biz.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.qihuan.wanandroid.bean.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * SearchResultViewModel
 * @author qi
 * @since 2020/8/6
 */
@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private var currentSearchResult: Flow<PagingData<Article>>? = null

    fun search(searchText: String): Flow<PagingData<Article>> {
        if (searchText.isNotBlank()) {
            viewModelScope.launch {
                repository.saveHistoryKey(searchText)
            }
        }

        val newResult: Flow<PagingData<Article>> =
            repository.getSearchResult(searchText).cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}