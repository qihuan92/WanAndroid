package com.qihuan.wanandroid.biz.qa

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.qihuan.wanandroid.bean.Article
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * QaViewModel
 * @author qi
 * @since 2020/9/17
 */
class QaViewModel @ViewModelInject constructor(
    private val repository: QaRepository
) : ViewModel() {

    val qaData by lazy { MutableLiveData<PagingData<Article>>() }

    init {
        getQaList()
    }

    fun getQaList() {
        viewModelScope.launch {
            repository.getQaList()
                .cachedIn(viewModelScope)
                .collectLatest {
                    qaData.value = it
                }
        }
    }
}