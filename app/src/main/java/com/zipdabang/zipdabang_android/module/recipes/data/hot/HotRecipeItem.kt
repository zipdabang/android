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
    val comments: Int,
    val thumbnailUrl: String?,
    // TODO API 확정 시 변수명 수정
    val ownerId: Int,
    val isBlocked: Boolean
)