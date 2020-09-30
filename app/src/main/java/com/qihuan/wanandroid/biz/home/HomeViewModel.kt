package com.qihuan.wanandroid.biz.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.TitleType
import com.qihuan.wanandroid.common.adapter.DiffItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * HomeViewModel
 * @author qi
 * @since 2020/6/29
 */
class HomeViewModel @ViewModelInject constructor(
    private val repository: HomeRepository
) : ViewModel() {
    val listLiveData = MutableLiveData<MutableList<DiffItem>>(mutableListOf())
    val pageLiveData by lazy { MutableLiveData<PagingData<Article>>() }
    private var page: Int = 0

    init {
        refresh()
    }

    fun refresh() {
        page = 0
        viewModelScope.launch {
            val bannerList = repository.getBanner()
            val homeModuleList = repository.getHomeModuleList()
            val topArticleList = repository.getTopArticleList()

            withContext(Dispatchers.IO) {
                val list = mutableListOf<DiffItem>()
                list.add(bannerList)

                if (homeModuleList.list.isNotEmpty()) {
                    list.add(TitleType.MODULE.create())
                    list.add(homeModuleList)
                }

                if (topArticleList.isNotEmpty()) {
                    list.add(TitleType.TOP.create())
                    list.addAll(topArticleList)
                }

                list.add(TitleType.TIMELINE.create())
                listLiveData.postValue(list)
            }

            repository.getArticleList()
                .cachedIn(viewModelScope)
                .collectLatest {
                    pageLiveData.value = it
                }
        }
    }
}