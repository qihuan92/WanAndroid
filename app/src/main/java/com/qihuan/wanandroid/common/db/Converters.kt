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

    @TypeConverter
    fun revertStringList(value: String): List<String>? {
        return value.split(",")
    }

    @TypeConverter
    fun converterStringList(value: List<String>): String {
        return value.joinToString(separator = ",")
    }

    @TypeConverter
    fun revertIntList(value: String): List<Int>? {
        return value.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun converterIntList(value: List<Int>): String {
        return value.joinToString(separator = ",")
    }

    @TypeConverter
    fun revertLongList(value: String): List<Long>? {
        return value.split(",").map { it.toLong() }
    }

    @TypeConverter
    fun converterLongList(value: List<Long>): String {
        return value.joinToString(separator = ",")
    }
}