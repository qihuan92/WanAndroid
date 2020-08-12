package com.qihuan.wanandroid.biz.home

import androidx.core.text.buildSpannedString
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qihuan.wanandroid.bean.TitleType
import kotlinx.coroutines.launch

/**
 * HomeViewModel
 * @author qi
 * @since 2020/6/29
 */
class HomeViewModel @ViewModelInject constructor(
    private val repository: HomeRepository
) : ViewModel() {
    val listLiveData = MutableLiveData<MutableList<Any>>(mutableListOf())
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
            if (topArticleList.isNotEmpty()) {
                buildSpannedString {

                }
                list.add(TitleType.TOP.create())
                list.addAll(topArticleList)
            }

            val articleList = repository.getArticleList(page)
            if (articleList.isNotEmpty()) {
                list.add(TitleType.TIMELINE.create())
                list.addAll(articleList)
            }

            listLiveData.value = list
        }
    }

    fun loadMore() {
        page += 1
        viewModelScope.launch {
            val list = mutableListOf<Any>()
            listLiveData.value?.apply {
                list.addAll(this)
            }

            val articleList = repository.getArticleList(page)
            list.addAll(articleList)

            listLiveData.value = list
        }
    }
}