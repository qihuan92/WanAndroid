package com.qihuan.wanandroid.common.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.qihuan.wanandroid.bean.HistorySearchKey
import com.qihuan.wanandroid.biz.search.HistorySearchKeyDao

/**
 * AppDatabase
 * @author qi
 * @since 2020/8/11
 */
@Database(
    entities = [
        HistorySearchKey::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun historySearchKeyDao(): HistorySearchKeyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "wan_android"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}