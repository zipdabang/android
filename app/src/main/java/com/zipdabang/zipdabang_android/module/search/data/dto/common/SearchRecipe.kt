package com.zipdabang.zipdabang_android.module.search.data.dto.common

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zipdabang.zipdabang_android.common.Constants

@Entity(tableName = Constants.SEARCH_RECIPE_TABLE)
data class SearchRecipe(
    val index : Int,
    val categoryId: List<Int>,
    val comments: Int,
    val createdAt: String,
    val isLiked: Boolean,
    val isScrapped: Boolean,
    val likes: Int,
    val nickname: String,
    @PrimaryKey(autoGenerate = false)
    val recipeId: Int,
    val recipeName: String,
    val scraps: Int,
    val thumbnailUrl: String
)


data class SearchRecipes(
    val categoryId: List<Int>,
    val comments: Int,
    val createdAt: String,
    val isLiked: Boolean,
    val isScrapped: Boolean,
    val likes: Int,
    val nickname: String,
    val recipeId: Int,
    val recipeName: String,
    val scraps: Int,
    val thumbnailUrl: String
)