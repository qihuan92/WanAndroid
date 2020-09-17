package com.qihuan.wanandroid.common.net

import com.qihuan.wanandroid.R
import com.qihuan.wanandroid.utils.Strings

/**
 * ResultEnum
 * @author qi
 * @since 2020/9/17
 */
enum class ResultEnum(
    private val code: Int,
    private val description: String,
) {
    NET_CONN_ERROR(-1, Strings.get(R.string.net_conn_error)),
    IO_ERROR(-2, Strings.get(R.string.io_error)),
    JSON_ERROR(-3, Strings.get(R.string.json_error)),
    ;

    fun getCode() = code
    fun getDescription() = description
}