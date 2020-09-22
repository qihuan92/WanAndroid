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

    suspend fun login(model: LoginModel): ApiResult<User> {
        return handleRequest { service.login(model.userName, model.password) }
    }

    suspend fun register(model: RegisterModel): ApiResult<User> {
        return handleRequest {
            service.register(
                model.userName,
                model.password,
                model.confirmPassword
            )
        }
    }

    suspend fun logout(): ApiResult<Any> {
        return handleRequest { service.logOut() }
    }
}