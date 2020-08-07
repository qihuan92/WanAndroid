package com.qihuan.wanandroid.biz.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

/**
 * HomeViewModel
 * @author qi
 * @since 2020/6/29
 */
class HomeViewModel @ViewModelInject constructor(
    private val repository: HomeRepository
) : ViewModel() {
    val listLiveData = MutableLiveData<ChangeList<Any>>(ChangeList())
    private var list by Delegates.observable(mutableListOf<Any>()) { _, oldList, newList ->
        listLiveData.value = ChangeList(oldList, newList)
    }
    private var page: Int = 0

    init {
        refresh()
    }

    fun refresh() {
        page = 0
        viewModelScope.launch {
            val list = mutableListOf<Any>()

            val bannerList = repository.getBanner()
            list.add(bannerList)

            val topArticleList = repository.getTopArticleList()
            list.addAll(topArticleList)

            val articleList = repository.getArticleList(page)
            list.addAll(articleList)

            this@HomeViewModel.list = list
        }
    }

    fun loadMore() {
        page += 1
        viewModelScope.launch {
            val list = mutableListOf<Any>()
            list.addAll(this@HomeViewModel.list)

            val articleList = repository.getArticleList(page)
            list.addAll(articleList)

            this@HomeViewModel.list = list
        }
    }

    data class ChangeList<T>(
        val oldList: List<T> = emptyList(),
        val newList: List<T> = emptyList()
    )
}