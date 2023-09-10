package com.zipdabang.zipdabang_android.module.detail.recipe.data

import kotlinx.serialization.Serializable

@Serializable
data class RecipeInfo(
    val ownerImage: String?,
    val categoryId: List<Int>,
    val comments: Int,
    val createdAt: String,
    val intro: String,
    var isLiked: Boolean,
    var isScrapped: Boolean,
    var likes: Int,
    val nickname: String,
    val recipeId: Int,
    val recipeName: String,
    val recipeTip: String,
    var scraps: Int,
    val thumbnailUrl: String,
    val time: String
)