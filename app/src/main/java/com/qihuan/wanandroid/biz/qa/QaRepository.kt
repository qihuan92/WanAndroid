package com.qihuan.wanandroid.biz.qa

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.qihuan.wanandroid.common.net.WanService
import javax.inject.Inject

/**
 * QaRepository
 * @author qi
 * @since 2020/9/17
 */
class QaRepository @Inject constructor(private val service: WanService) {

    fun getQaList() = Pager(
        config = PagingConfig(pageSize = 20, prefetchDistance = 1),
        pagingSourceFactory = { QaArticlePagingSource(service) }
    ).flow
}