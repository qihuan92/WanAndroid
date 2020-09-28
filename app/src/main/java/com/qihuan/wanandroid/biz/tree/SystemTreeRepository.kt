package com.qihuan.wanandroid.biz.tree

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.qihuan.wanandroid.bean.SystemNode
import com.qihuan.wanandroid.common.net.ApiResult
import com.qihuan.wanandroid.common.net.WanService
import com.qihuan.wanandroid.common.net.handleRequest
import javax.inject.Inject

/**
 * SystemTreeRepository
 * @author qi
 * @since 2020/9/14
 */
class SystemTreeRepository @Inject constructor(private val service: WanService) {

    suspend fun getTreeList(): List<SystemNode> {
        val resp = handleRequest { service.getSystemTreeList() }
        if (resp is ApiResult.Success) {
            return resp.data.orEmpty()
        }
        return emptyList()
    }

    fun getTreeArticleList(treeId: Long) = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { TreeArticlePagingSource(service, treeId) }
    ).flow
}