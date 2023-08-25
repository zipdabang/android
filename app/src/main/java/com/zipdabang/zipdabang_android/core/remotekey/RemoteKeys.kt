package com.zipdabang.zipdabang_android.core.remotekey

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants.REMOTE_KEY_DATABASE


@Entity(tableName = REMOTE_KEY_DATABASE)
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)
