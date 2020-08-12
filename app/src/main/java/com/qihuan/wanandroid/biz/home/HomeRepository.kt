package com.qihuan.wanandroid.biz.home

import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.BannerList
import com.qihuan.wanandroid.common.ApiResult
import com.qihuan.wanandroid.common.net.WanService
import com.qihuan.wanandroid.common.net.handleRequest
import javax.inject.Inject

/**
 * HomeRepository
 * @author qi
 * @since 2020/7/22
 */
class HomeRepository @Inject constructor(private val wanService: WanService) {

    suspend fun getBanner(): BannerList {
        val result = handleRequest { wanService.getBanner() }
        val bannerList = BannerList(emptyList())
        if (result is ApiResult.Success) {
            bannerList.list = result.data.orEmpty()
        }
        return bannerList
    }

    suspend fun getTopArticleList(): List<Article> {
        val resp = handleRequest { wanService.getTopArticles() }
        if (resp is ApiResult.Success) {
            return resp.data.orEmpty().onEach {
                it.isTop = true
            }
        }
        return mutableListOf()
    }

    suspend fun getArticleList(page: Int = 0): List<Article> {
        var list = mutableListOf<Article>()
        val resp = handleRequest { wanService.getHomeArticles(page) }
        if (resp is ApiResult.Success) {
            list = resp.data?.datas?.toMutableList() ?: mutableListOf()
        }
        return list
    }
}