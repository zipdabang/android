package com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.temp

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants

@Entity(tableName = Constants.MY_TEMPED_RECIPES_TABLE)
data class TempRecipe(
    val recipeName: String,
    @PrimaryKey(autoGenerate = false)
    val tempId: Int,
    val thumbnailUrl: String,
    val updatedAt: String
)