package com.qihuan.wanandroid.biz.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.qihuan.wanandroid.bean.User

/**
 * UserDao
 * @author qi
 * @since 2020/9/25
 */
@Dao
interface UserDao {

    @Query("select * from user limit 0,1")
    suspend fun selectLoginUser(): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLoginUser(user: User)

    @Query("delete from user")
    suspend fun deleteLoginUser()
}