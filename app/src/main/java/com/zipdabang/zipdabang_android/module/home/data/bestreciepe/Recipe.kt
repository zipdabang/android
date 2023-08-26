package com.zipdabang.zipdabang_android.module.home.data.bestreciepe

data class Recipe(
    val categoryId: List<Int>,
    val comments: Int,
    val createdAt: String,
    val isLiked: Boolean,
    val isScrapped: Boolean,
    val likes: Int,
    val owner: String,
    val recipeId: Int,
    val recipeName: String,
    val scraps: Int,
    val thumbnailUrl: String
)