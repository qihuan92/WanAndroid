package com.qihuan.wanandroid.biz.home

import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.BannerList
import com.qihuan.wanandroid.common.net.WanService
import javax.inject.Inject

/**
 * HomeRepository
 * @author qi
 * @since 2020/7/22
 */
class HomeRepository @Inject constructor(private val wanService: WanService) {

    suspend fun getBanner(): BannerList {
        val resp = wanService.getBanner()
        if (resp.isSuccess()) {
            resp.data
        }
        return BannerList(resp.data.orEmpty())
    }

    suspend fun getTopArticleList(): List<Article> {
        val resp = wanService.getTopArticles()
        if (resp.isSuccess()) {
            return resp.data.orEmpty()
        }
        return mutableListOf()
    }

    suspend fun getArticleList(page: Int = 0): List<Article> {
        var list = mutableListOf<Article>()
        val resp = wanService.getHomeArticles(page)
        if (resp.isSuccess()) {
            list = resp.data?.datas?.toMutableList() ?: mutableListOf()
        }
        return list;
    }
}