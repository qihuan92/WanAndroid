package com.qihuan.wanandroid.biz.tree

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
 * TreeArticleViewModel
 * @author qi
 * @since 2020/9/16
 */
@HiltViewModel
class TreeArticleViewModel @Inject constructor(
    private val repository: SystemTreeRepository
) : ViewModel() {

    val articlePage by lazy { MutableLiveData<PagingData<Article>>() }

    fun getArticleList(treeId: Long?) {
        if (treeId == null) {
            return
        }
        viewModelScope.launch {
            repository.getTreeArticleList(treeId)
                .cachedIn(viewModelScope)
                .collectLatest {
                    articlePage.value = it
                }
        }
    }
}