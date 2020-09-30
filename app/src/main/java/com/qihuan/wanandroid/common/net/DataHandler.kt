package com.qihuan.wanandroid.common.net

import com.qihuan.wanandroid.bean.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * DataHandler
 * @author qi
 * @since 2020/9/30
 */
suspend fun List<Article>?.uiData(): List<Article> {
    if (this == null) {
        return emptyList()
    }
    withContext(Dispatchers.IO) {
        forEach {
            it.handleData()
        }
    }
    return this
}