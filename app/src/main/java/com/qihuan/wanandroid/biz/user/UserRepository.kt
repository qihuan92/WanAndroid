package com.qihuan.wanandroid.biz.user

import com.qihuan.wanandroid.bean.User
import com.qihuan.wanandroid.common.net.ApiException
import com.qihuan.wanandroid.common.net.ApiResult
import com.qihuan.wanandroid.common.net.WanService
import com.qihuan.wanandroid.common.net.handleRequest
import javax.inject.Inject

/**
 * UserRepository
 * @author qi
 * @since 2020/9/21
 */
class UserRepository @Inject constructor(
    private val service: WanService,
    private val userDao: UserDao,
) {

    suspend fun login(userName: String, password: String): ApiResult<User> {
        val result = handleRequest { service.login(userName, password) }
        if (result is ApiResult.Success) {
            if (result.data == null) {
                return ApiResult.Error(ApiException("用户为空"))
            }
            userDao.saveLoginUser(result.data)
        }
        return result
    }

    suspend fun register(
        userName: String,
        password: String,
        confirmPassword: String
    ): ApiResult<User> {
        val result = handleRequest {
            service.register(
                userName,
                password,
                confirmPassword
            )
        }
        if (result is ApiResult.Success) {
            if (result.data == null) {
                return ApiResult.Error(ApiException("用户为空"))
            }
            userDao.saveLoginUser(result.data)
        }
        return result
    }

    suspend fun logout(): ApiResult<Any> {
        val result = handleRequest { service.logOut() }
        if (result is ApiResult.Success) {
            userDao.deleteLoginUser()
        }
        return result
    }
}