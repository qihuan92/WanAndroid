package com.qihuan.wanandroid.biz.user

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.common.SingleLiveEvent
import com.qihuan.wanandroid.common.net.ApiResult
import com.qihuan.wanandroid.utils.Strings
import kotlinx.coroutines.launch

/**
 * RegisterViewModel
 * @author qi
 * @since 2020/9/24
 */
class RegisterViewModel @ViewModelInject constructor(
    private val repository: UserRepository
) : ViewModel() {
    val userName by lazy { ObservableField<String>() }
    val password by lazy { ObservableField<String>() }
    val confirmPassword by lazy { ObservableField<String>() }
    val userNameError by lazy { ObservableField<String>() }
    val passwordError by lazy { ObservableField<String>() }
    val confirmPasswordError by lazy { ObservableField<String>() }
    val loginResult by lazy { SingleLiveEvent<ApiResult<Any>>() }

    fun register() {
        userNameError.set(null)
        val userNameValue = userName.get()
        if (userNameValue.isNullOrBlank()) {
            userNameError.set(Strings.get(R.string.placeholder_user_name))
            return
        }

        passwordError.set(null)
        val passwordValue = password.get()
        if (passwordValue.isNullOrBlank()) {
            passwordError.set(Strings.get(R.string.placeholder_text_password))
            return
        }

        confirmPasswordError.set(null)
        val confirmPasswordValue = confirmPassword.get()
        if (confirmPasswordValue.isNullOrBlank()) {
            confirmPasswordError.set(Strings.get(R.string.placeholder_text_confirm_password))
            return
        }

        viewModelScope.launch {
            loginResult.value =
                repository.register(userNameValue, passwordValue, confirmPasswordValue)
        }
    }
}