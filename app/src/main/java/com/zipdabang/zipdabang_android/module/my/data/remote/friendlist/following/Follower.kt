package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist.following

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants

@Entity(tableName = Constants.FOLLOWING_TABLE)
data class Follower(
    val caption: String?,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val imageUrl: String,
    val isFollowing: Boolean,
    val nickname: String
)