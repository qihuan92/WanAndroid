package com.qihuan.wanandroid.biz.navigation

import com.qihuan.wanandroid.bean.NavigationBean
import com.qihuan.wanandroid.common.net.ApiResult
import com.qihuan.wanandroid.common.net.WanService
import com.qihuan.wanandroid.common.net.handleRequest
import javax.inject.Inject

/**
 * NavigationRepository
 * @author qi
 * @since 2020/9/16
 */
class NavigationRepository @Inject constructor(private val service: WanService) {

    suspend fun getNavigation(): List<NavigationBean> {
        val resp = handleRequest { service.getNavigation() }
        if (resp is ApiResult.Success) {
            return resp.data.orEmpty()
        }
        return emptyList()
    }
}