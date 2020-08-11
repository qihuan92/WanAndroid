package com.qihuan.wanandroid.biz.search

import androidx.lifecycle.LiveData
import com.qihuan.wanandroid.bean.Article
import com.qihuan.wanandroid.bean.HistorySearchKey
import com.qihuan.wanandroid.bean.SearchKey
import com.qihuan.wanandroid.common.net.WanService
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
        val resp = service.hotKey()
        if (resp.isSuccess()) {
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

    suspend fun getSearchResult(page: Int = 0, searchText: String): List<Article> {
        val resp = service.search(page, searchText)
        if (resp.isSuccess()) {
            return resp.data?.datas.orEmpty()
        }
        return emptyList()
    }

    suspend fun deleteKey(key: String) {
        historySearchKeyDao.deleteByName(key)
    }

    suspend fun deleteAllKey() {
        historySearchKeyDao.deleteAll()
    }
}