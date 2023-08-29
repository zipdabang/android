package com.zipdabang.zipdabang_android.module.detail.recipe.data

import kotlinx.serialization.Serializable

@Serializable
data class RecipeInfo(
    val ownerImage: String,
    val categoryId: List<Int>,
    val comments: Int,
    val createdAt: String,
    val intro: String,
    val isLiked: Boolean,
    val isScrapped: Boolean,
    val likes: Int,
    val nickname: String,
    val recipeId: Int,
    val recipeName: String,
    val recipeTip: String,
    val scraps: Int,
    val thumbnailUrl: String,
    val time: String
)