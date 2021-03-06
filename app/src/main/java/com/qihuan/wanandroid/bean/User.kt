package com.qihuan.wanandroid.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val id: Int,
    val admin: Boolean,
    val chapterTops: List<String>,
    val collectIds: List<Long>,
    val email: String,
    val icon: String,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
) {

    override fun equals(other: Any?): Boolean {
        return if (other is User) {
            this.id == other.id
        } else false
    }

    override fun hashCode(): Int {
        return id
    }
}