package com.zipdabang.zipdabang_android.module.home.data.bestrecipe

data class BestRecipe(
    val recipeId : Int,
    val recipeName : String,
    val nickname : String,
    val thumbnailUrl : String,
    val createdAt : String,
    val updateAt : String,
    val likes : Int,
    val comments : Int,
    val scraps : Int,
    val isLiked : Boolean,
    val isScrapped : Boolean,
    val rank : Int
)