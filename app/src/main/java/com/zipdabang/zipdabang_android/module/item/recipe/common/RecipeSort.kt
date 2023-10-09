package com.zipdabang.zipdabang_android.module.item.recipe.common

enum class RecipeSort(val type: String, val text: String) {
    LATEST("latest", "최신순"),
    FOLLOW("follow", "팔로우순"),
    LIKES("likes", "인기순");
}