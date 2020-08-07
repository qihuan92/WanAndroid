package com.qihuan.wanandroid.biz.search

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qihuan.wanandroid.bean.SearchKey
import kotlinx.coroutines.launch

/**
 * SearchViewModel
 * @author qi
 * @since 2020/8/6
 */
class SearchViewModel @ViewModelInject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    val hotKeys by lazy {
        MutableLiveData<List<SearchKey>>(emptyList())
    }
    val searchText by lazy {
        ObservableField<String>("")
    }

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val keyList = repository.getHotSearchKey()
            hotKeys.value = keyList
        }
    }
}