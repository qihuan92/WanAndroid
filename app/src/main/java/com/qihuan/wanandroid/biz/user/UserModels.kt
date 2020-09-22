package com.qihuan.wanandroid.biz.user

/**
 * UserModels
 * @author qi
 * @since 2020/9/21
 */
data class LoginModel(
    val userName: String,
    val password: String,
)

data class RegisterModel(
    val userName: String,
    val password: String,
    val confirmPassword: String,
)