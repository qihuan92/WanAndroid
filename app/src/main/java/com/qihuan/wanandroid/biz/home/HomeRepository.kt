package com.qihuan.wanandroid.biz.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.BannerList
import com.qihuan.wanandroid.bean.ModuleBean
import com.qihuan.wanandroid.bean.ModuleList
import com.qihuan.wanandroid.common.ApiResult
import com.qihuan.wanandroid.common.net.WanService
import com.qihuan.wanandroid.common.net.handleRequest
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
            return resp.data.orEmpty().onEach {
                it.isTop = true
            }
        }
        return mutableListOf()
    }

    suspend fun getArticleList(page: Int = 0): List<Article> {
        var list = mutableListOf<Article>()
        val resp = handleRequest { service.getHomeArticles(page) }
        if (resp is ApiResult.Success) {
            list = resp.data?.datas?.toMutableList() ?: mutableListOf()
        }
        return list
    }

    fun getArticleList() = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 1),
        pagingSourceFactory = { HomeArticlePagingSource(service) }
    ).flow

    suspend fun getHomeModuleList(): ModuleList {
        return withContext(Dispatchers.IO) {
            // todo 获取数据
            val list = listOf(
                ModuleBean("", R.drawable.ic_round_account_tree_24, "体系", "open://wanandroid/tree"),
                ModuleBean("", R.drawable.ic_round_explore_24, "导航", "open://wanandroid/navigation"),
                ModuleBean("", R.drawable.ic_round_question_answer_24, "问答", ""),
            )
            return@withContext ModuleList(list)
        }
    }
}