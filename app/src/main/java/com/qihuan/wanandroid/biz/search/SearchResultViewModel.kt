package com.qihuan.wanandroid.biz.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * SearchViewModel
 * @author qi
 * @since 2020/8/6
 */
class SearchResultViewModel @ViewModelInject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    var searchText = ""
    val listLiveData by lazy { MutableLiveData<MutableList<Any>>(mutableListOf()) }
    private var page = 0

    private fun search() {
        if (searchText.isEmpty()) {
            return
        }
        viewModelScope.launch {
            val fetchList = repository.getSearchResult(page, searchText)
            val list = listLiveData.value
            if (page == 0) {
                list?.apply {
                    clear()
                    addAll(fetchList)
                }
            } else {
                list?.addAll(fetchList)
            }
            listLiveData.value = list
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