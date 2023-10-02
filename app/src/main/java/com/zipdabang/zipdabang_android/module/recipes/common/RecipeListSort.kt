package com.zipdabang.zipdabang_android.module.recipes.common

enum class RecipeListSort(val type: String) {
    LIKES(type = "likes"),
    LATEST(type = "latest"),
    // TODO 집다방추천 기준 type 변경하기
    RECOMMEND(type = "recommend"),
    NAME(type = "name");
}