package com.qihuan.wanandroid.biz.qa

import androidx.paging.PagingSource
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.common.net.ApiResult
import com.qihuan.wanandroid.common.net.WanService
import com.qihuan.wanandroid.common.net.handleRequest

/**
 * QaArticlePagingSource
 * @author qi
 * @since 2020/9/8
 */
class QaArticlePagingSource constructor(
    private val service: WanService
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 0
        return when (val result = handleRequest { service.getQAList(page) }) {
            is ApiResult.Success -> {
                val data = result.data
                LoadResult.Page(
                    data = data?.datas.orEmpty(),
                    prevKey = if (page == 0) null else page - 1,
                    nextKey = if (data == null || data.over) null else page + 1
                )
            }
            is ApiResult.Error -> {
                LoadResult.Error(result.error)
            }
        }
    }
}