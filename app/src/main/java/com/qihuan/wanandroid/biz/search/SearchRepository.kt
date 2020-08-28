package com.qihuan.wanandroid.biz.search

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.HistorySearchKey
import com.qihuan.wanandroid.bean.SearchKey
import com.qihuan.wanandroid.common.ApiResult
import com.qihuan.wanandroid.common.net.WanService
import com.qihuan.wanandroid.common.net.handleRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * SearchRepository
 * @author qi
 * @since 2020/8/6
 */
class SearchRepository @Inject constructor(
    private val service: WanService,
    private val historySearchKeyDao: HistorySearchKeyDao
) {
    suspend fun getHotSearchKey(): List<SearchKey> {
        val resp = handleRequest { service.hotKey() }
        if (resp is ApiResult.Success) {
            return resp.data.orEmpty()
        }
        return emptyList()
    }

    fun getHistoryKey(): LiveData<List<HistorySearchKey>> {
        return historySearchKeyDao.selectAll()
    }

    suspend fun saveHistoryKey(name: String) {
        historySearchKeyDao.save(HistorySearchKey(name))
    }

    fun getSearchResult(searchText: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {SearchArticlePagingSource(service, searchText)}
        ).flow
    }

    suspend fun deleteKey(key: String) {
        historySearchKeyDao.deleteByName(key)
    }

    suspend fun deleteAllKey() {
        historySearchKeyDao.deleteAll()
    }
}