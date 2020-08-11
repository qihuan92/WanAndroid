package com.qihuan.wanandroid.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * HistorySearchKey
 * @author qi
 * @since 2020/8/11
 */
@Entity(tableName = "history_search_key")
data class HistorySearchKey(
    @PrimaryKey
    val name: String,
    val createTime: Date = Date()
)