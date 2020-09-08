package com.qihuan.wanandroid.biz.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.qihuan.wanandroid.bean.TitleType
import com.qihuan.wanandroid.common.adapter.DiffItem
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * HomeViewModel
 * @author qi
 * @since 2020/6/29
 */
class HomeViewModel @ViewModelInject constructor(
    private val repository: HomeRepository
) : ViewModel() {
    val listLiveData = MutableLiveData<MutableList<DiffItem>>(mutableListOf())
    private var page: Int = 0

    init {
        refresh()
    }

    fun refresh() {
        page = 0
        viewModelScope.launch {
            val list = mutableListOf<DiffItem>()

            val bannerList = repository.getBanner()
            list.add(bannerList)

            val homeModuleList = repository.getHomeModuleList()
            if (homeModuleList.list.isNotEmpty()) {
                list.add(TitleType.MODULE.create())
                list.add(homeModuleList)
            }

            val topArticleList = repository.getTopArticleList()
            if (topArticleList.isNotEmpty()) {
                list.add(TitleType.TOP.create())
                list.addAll(topArticleList)
            }

            list.add(TitleType.TIMELINE.create())

            listLiveData.value = list
        }
    }

    fun getArticleList() = liveData {
        repository.getArticleList()
            .cachedIn(viewModelScope)
            .collectLatest {
                emit(it)
            }
    }
}