package com.zipdabang.zipdabang_android.entity.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants.RECIPE_REMOTE_KEY_TABLE
import com.zipdabang.zipdabang_android.common.Constants.REMOTE_KEY_DATABASE


@Entity(tableName = RECIPE_REMOTE_KEY_TABLE)
data class RemoteKeysEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val prevPage: Int?,
    val nextPage: Int?
)
