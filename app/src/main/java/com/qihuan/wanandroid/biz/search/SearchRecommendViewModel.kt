package com.qihuan.wanandroid.biz.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qihuan.wanandroid.bean.SearchKey
import kotlinx.coroutines.launch

/**
 * SearchRecommendViewModel
 * @author qi
 * @since 2020/8/6
 */
class SearchRecommendViewModel @ViewModelInject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    val hotKeys by lazy { MutableLiveData<List<SearchKey>>(emptyList()) }

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