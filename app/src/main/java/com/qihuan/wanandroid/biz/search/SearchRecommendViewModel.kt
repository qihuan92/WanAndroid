package com.qihuan.wanandroid.biz.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qihuan.wanandroid.bean.HistorySearchKey
import com.qihuan.wanandroid.bean.SearchKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * SearchRecommendViewModel
 * @author qi
 * @since 2020/8/6
 */
@HiltViewModel
class SearchRecommendViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    val hotKeys by lazy { MutableLiveData<List<SearchKey>>(emptyList()) }
    var historyKeys: LiveData<List<HistorySearchKey>> = MutableLiveData(emptyList())

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