package com.qihuan.wanandroid.biz.search

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.HistorySearchKey
import com.qihuan.wanandroid.bean.SearchKey
import com.qihuan.wanandroid.common.SingleLiveEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * SearchViewModel
 * @author qi
 * @since 2020/8/6
 */
class SearchViewModel @ViewModelInject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    val hotKeys by lazy { MutableLiveData<List<SearchKey>>(emptyList()) }
    val searchText by lazy { ObservableField("") }
    val searchEvent by lazy { SingleLiveEvent<Any>() }
    var historyKeys: LiveData<List<HistorySearchKey>> = MutableLiveData(emptyList())
    private var currentSearchResult: Flow<PagingData<Article>>? = null

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val keyList = repository.getHotSearchKey()
            hotKeys.value = keyList
        }

        historyKeys = repository.getHistoryKey()
    }

    fun search(): Flow<PagingData<Article>> {
        val searchText = searchText.get().orEmpty()
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

    fun deleteKey(key: String) {
        viewModelScope.launch {
            repository.deleteKey(key)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAllKey()
        }
    }
}