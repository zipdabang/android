package com.zipdabang.zipdabang_android.module.my.data.remote.recipeedit.complete

data class GetCompleteRecipeInfo(
    val categoryId: List<Int>,
    val comments: Int,
    val createdAt: String,
    val intro: String,
    val isLiked: Boolean,
    val isScrapped: Boolean,
    val likes: Int,
    val nickname: String,
    val ownerImage: String,
    val recipeId: Int,
    val recipeName: String,
    val recipeTip: String,
    val scraps: Int,
    val thumbnailUrl: String,
    val time: String,
    val updatedAt: String
)