package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.follow.search

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants

data class FollowInfo(
    val createdAt: String,
    val memberId: Int,
    val nickname: String,
    val profileUrl: String
)

@Entity(tableName = Constants.SEARCH_FOLLOW_TABLE)
data class FollowInfoDB(
    val index : Int,
    val createdAt: String,
    @PrimaryKey(autoGenerate = false)
    val memberId: Int,
    val nickname: String,
    val profileUrl: String
)