package com.zipdabang.zipdabang_android.module.home.ui

import com.zipdabang.zipdabang_android.module.home.data.banner.BannerDto
import com.zipdabang.zipdabang_android.module.home.data.bestrecipe.BestRecipe

data class HomeBannerState(
    val isLoading: Boolean = false,
    val bannerList: List<BannerDto> = emptyList(),
    val isError : Boolean = false,
    val error: String = ""
)

data class HomeRecipeState(
    val isLoading: Boolean = false,
   val recipeList: List<BestRecipe> = emptyList(),
    val isError : Boolean = false,
    val error : String = ""
)
