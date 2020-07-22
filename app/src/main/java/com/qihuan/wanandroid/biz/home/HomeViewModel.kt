package com.qihuan.wanandroid.biz.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qihuan.wanandroid.common.UIResult
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

/**
 * HomeViewModel
 * @author qi
 * @since 2020/6/29
 */
class HomeViewModel @ViewModelInject constructor(
    private val repository: HomeRepository
) : ViewModel() {
    var isLoading: AtomicBoolean = AtomicBoolean(false)
    val listLiveData = MutableLiveData<UIResult<MutableList<Any>>>(UIResult.Loading)
    private var list = mutableListOf<Any>()
    private var page: Int = 0

    init {
        refresh()
    }

    fun refresh() {
        page = 0
        listLiveData.value = UIResult.Loading
        viewModelScope.launch {
            list.clear()

            val bannerList = repository.getBanner()
            list.add(bannerList)

            val topArticleList = repository.getTopArticleList()
            list.addAll(topArticleList)

            val articleList = repository.getArticleList(page)
            list.addAll(articleList)

            listLiveData.value = UIResult.Success(list)
        }
    }

    fun loadMore() {
        page += 1
        listLiveData.value = UIResult.Loading
        isLoading.set(true)
        viewModelScope.launch {
            val articleList = repository.getArticleList(page)
            list.addAll(articleList)

            listLiveData.value = UIResult.Success(list, isLoadingMore = true)
            isLoading.set(false)
        }
    }
}