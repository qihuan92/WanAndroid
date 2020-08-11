package com.qihuan.wanandroid.biz.search

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qihuan.wanandroid.bean.HistorySearchKey

/**
 * HistorySearchKeyDao
 * @author qi
 * @since 2020/8/11
 */
@Dao
interface HistorySearchKeyDao {

    @Query("select * from history_search_key order by createTime desc")
    fun selectAll(): LiveData<List<HistorySearchKey>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(key: HistorySearchKey)

    @Query("delete from history_search_key where name = :name")
    suspend fun deleteByName(name: String)

    @Query("delete from history_search_key")
    suspend fun deleteAll()
}