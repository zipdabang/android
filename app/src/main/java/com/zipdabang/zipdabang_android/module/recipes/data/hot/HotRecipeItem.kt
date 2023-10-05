package com.zipdabang.zipdabang_android.module.recipes.data.hot

import kotlinx.serialization.Serializable

@Serializable
data class HotRecipeItem(
    var isLiked: Boolean,
    var isScrapped: Boolean,
    var likes: Int,
    val nickname: String,
    val recipeId: Int,
    val recipeName: String,
    var scraps: Int,
    val thumbnailUrl: String?
)