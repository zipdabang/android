package com.zipdabang.zipdabang_android.module.my.data.remote.otherinfo

data class OtherRecipePreviewResult(
    val recipeList: List<OtherRecipe>,
    val totalElements: Int
)


data class OtherRecipe(
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