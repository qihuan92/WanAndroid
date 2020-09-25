package com.qihuan.wanandroid.biz.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

/**
 * RegisterViewModel
 * @author qi
 * @since 2020/9/24
 */
class RegisterViewModel @ViewModelInject constructor(
    private val repository: UserRepository
) : ViewModel() {
}