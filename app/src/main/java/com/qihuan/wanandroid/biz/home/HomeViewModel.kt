package com.qihuan.wanandroid.biz.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.BannerList
import com.qihuan.wanandroid.bean.WanPage
import com.qihuan.wanandroid.common.net.WanService
import kotlinx.coroutines.launch

/**
 * HomeViewModel
 * @author qi
 * @since 2020/6/29
 */
class HomeViewModel @ViewModelInject constructor(private val service: WanService) : ViewModel() {
    val listLiveData = MutableLiveData<MutableList<Any>>(mutableListOf())
    private var list = mutableListOf<Any>()
    private var page: Int = 0

    fun refresh() {
        viewModelScope.launch {
            list.clear()

            val bannerResp = service.getBanner()
            if (bannerResp.isSuccess()) {
                list.add(BannerList(bannerResp.data))
            }

            val articlePage = refreshArticle()
            if (articlePage.datas.isNotEmpty()) {
                list.addAll(articlePage.datas)
            }

            listLiveData.value = list
        }
    }

    fun loadMore() {
        viewModelScope.launch {
            val articlePage = loadArticle()
            if (articlePage.datas.isNotEmpty()) {
                list.addAll(articlePage.datas)
            }

            listLiveData.value = list
        }
    }

    private suspend fun refreshArticle(): WanPage<Article> {
        page = 0
        return getArticle()
    }

    private suspend fun loadArticle(): WanPage<Article> {
        page += 1
        return getArticle()
    }

    private suspend fun getArticle(): WanPage<Article> {
        val resp = service.getHomeArticles(page)
        return if (resp.isSuccess()) {
            resp.data
        } else {
            WanPage(
                curPage = page,
                datas = emptyList()
            )
        }
    }
}