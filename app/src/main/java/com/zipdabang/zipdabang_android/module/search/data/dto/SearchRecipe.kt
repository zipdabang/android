package com.zipdabang.zipdabang_android.module.search.data.dto

data class SearchRecipe(
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