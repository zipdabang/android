package com.zipdabang.zipdabang_android.module.recipes.data.preview

import kotlinx.serialization.Serializable

@Serializable
data class RecipeItem(
    val categoryId: List<Int>,
    val comments: Int,
    val createdAt: String,
    var isLiked: Boolean,
    var isScrapped: Boolean,
    var likes: Int,
    val owner: String,
    val recipeId: Int,
    val recipeName: String,
    var scraps: Int,
    val thumbnailUrl: String
)