package com.qihuan.wanandroid.common.db

import androidx.room.TypeConverter
import java.util.*

/**
 * Converters
 * @author qi
 * @since 2020/8/11
 */
class Converters {

    @TypeConverter
    fun revertDate(value: Long): Date? {
        return Date(value)
    }

    @TypeConverter
    fun converterDate(value: Date): Long {
        return value.time
    }
}