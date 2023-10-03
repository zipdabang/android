package com.zipdabang.zipdabang_android.module.my.data.remote.friendlist

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants

@Entity(tableName = Constants.FOLLOW_TABLE)
data class Following(
    val caption: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val imageUrl: String,
    val nickname: String
)