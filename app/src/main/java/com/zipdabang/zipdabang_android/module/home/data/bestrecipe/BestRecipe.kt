package com.zipdabang.zipdabang_android.module.home.data.bestrecipe

data class BestRecipe(
    val recipeId : Int,
    val recipeName : String,
    val nickname : String,
    val thumbnailUrl : String,
    val createdAt : String,
    val updateAt : String,
    var likes : Int,
    val comments : Int,
    val scraps : Int,
    var isLiked : Boolean,
    val isScrapped : Boolean,
    val rank : Int
)