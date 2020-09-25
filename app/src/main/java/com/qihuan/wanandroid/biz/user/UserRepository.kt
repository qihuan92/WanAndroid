package com.qihuan.wanandroid.biz.user

import com.qihuan.wanandroid.bean.User
import com.qihuan.wanandroid.common.net.ApiResult
import com.qihuan.wanandroid.common.net.WanService
import com.qihuan.wanandroid.common.net.handleRequest
import javax.inject.Inject

/**
 * UserRepository
 * @author qi
 * @since 2020/9/21
 */
class UserRepository @Inject constructor(private val service: WanService) {

    suspend fun login(userName: String, password: String): ApiResult<User> {
        return handleRequest { service.login(userName, password) }
    }

    suspend fun register(
        userName: String,
        password: String,
        confirmPassword: String
    ): ApiResult<User> {
        return handleRequest {
            service.register(
                userName,
                password,
                confirmPassword
            )
        }
    }

    suspend fun logout(): ApiResult<Any> {
        return handleRequest { service.logOut() }
    }
}