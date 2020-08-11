package com.qihuan.wanandroid.biz.search

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qihuan.wanandroid.bean.SearchKey
import com.qihuan.wanandroid.common.SingleLiveEvent
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
    val searchText by lazy { ObservableField<String>("") }
    val searchEvent by lazy { SingleLiveEvent<Any>() }
    val listLiveData by lazy { MutableLiveData<MutableList<Any>>(mutableListOf()) }
    private var page = 0

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val keyList = repository.getHotSearchKey()
            hotKeys.value = keyList
        }
    }

    private fun search() {
        val searchText = searchText.get()
        if (searchText.isNullOrEmpty()) {
            return
        }
        viewModelScope.launch {
            val list = repository.getSearchResult(page, searchText)
            val value = listLiveData.value
            if (page == 0) {
                value?.clear()
            }
            value?.addAll(list)
            listLiveData.value = value
        }
    }

    fun refresh() {
        page = 0
        search()
    }

    fun loadMore() {
        page += 1
        search()
    }
}