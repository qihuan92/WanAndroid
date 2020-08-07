package com.qihuan.wanandroid.biz.search

import com.qihuan.wanandroid.bean.SearchKey
import com.qihuan.wanandroid.common.net.WanService
import javax.inject.Inject

/**
 * SearchRepository
 * @author qi
 * @since 2020/8/6
 */
class SearchRepository @Inject constructor(private val service: WanService) {
    suspend fun getHotSearchKey(): List<SearchKey> {
        val resp = service.hotKey()
        if (resp.isSuccess()) {
            return resp.data.orEmpty()
        }
        return emptyList()
    }

    suspend fun getHistoryKey() {
        // todo 获取搜索历史
    }
}