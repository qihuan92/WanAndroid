package com.qihuan.wanandroid.biz.qa

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.qihuan.wanandroid.bean.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * QaViewModel
 * @author qi
 * @since 2020/9/17
 */
@HiltViewModel
class QaViewModel @Inject constructor(
    private val repository: QaRepository
) : ViewModel() {

    val qaData by lazy { MutableLiveData<PagingData<Article>>() }

    init {
        getQaList()
    }

    private fun getQaList() {
        viewModelScope.launch {
            repository.getQaList()
                .cachedIn(viewModelScope)
                .collectLatest {
                    qaData.value = it
                }
        }
    }
}