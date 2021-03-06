package com.qihuan.wanandroid.biz.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.BannerList
import com.qihuan.wanandroid.bean.ModuleBean
import com.qihuan.wanandroid.bean.ModuleList
import com.qihuan.wanandroid.common.net.ApiResult
import com.qihuan.wanandroid.common.net.WanService
import com.qihuan.wanandroid.common.net.handleRequest
import com.qihuan.wanandroid.common.net.uiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * HomeRepository
 * @author qi
 * @since 2020/7/22
 */
class HomeRepository @Inject constructor(private val service: WanService) {

    suspend fun getBanner(): BannerList {
        val result = handleRequest { service.getBanner() }
        val bannerList = BannerList(emptyList())
        if (result is ApiResult.Success) {
            bannerList.list = result.data.orEmpty()
        }
        return bannerList
    }

    suspend fun getTopArticleList(): List<Article> {
        val resp = handleRequest { service.getTopArticles() }
        if (resp is ApiResult.Success) {
            return resp.data.uiData()
        }
        return mutableListOf()
    }

    fun getArticleList() = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { HomeArticlePagingSource(service) }
    ).flow

    suspend fun getHomeModuleList(): ModuleList {
        return withContext(Dispatchers.IO) {
            val list = listOf(
                ModuleBean(
                    "",
                    R.drawable.ic_round_account_tree_24,
                    "体系",
                    "open://wanandroid/tree"
                ),
                ModuleBean(
                    "",
                    R.drawable.ic_round_explore_24,
                    "导航",
                    "open://wanandroid/navigation"
                ),
                ModuleBean(
                    "",
                    R.drawable.ic_round_question_answer_24,
                    "问答",
                    "open://wanandroid/qa"
                ),
            )
            return@withContext ModuleList(list)
        }
    }
}