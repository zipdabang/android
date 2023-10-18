package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following.search

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants

data class FollowerInfo(
    val createdAt: String,
    val memberId: Int,
    val nickname: String,
    val profileUrl: String
)

@Entity(tableName = Constants.SEARCH_FOLLOWING_TABLE)
data class FollowerInfoDB(
    val index : Int,
    val createdAt: String,
    @PrimaryKey(autoGenerate = false)
    val memberId: Int,
    val nickname: String,
    val profileUrl: String
)