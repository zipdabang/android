package com.zipdabang.zipdabang_android.module.my.ui.state.myrecipe.preview

import com.zipdabang.zipdabang_android.module.my.data.remote.myrecipes.preview.CompleteRecipesWithImgPreviewRecipe
import com.zipdabang.zipdabang_android.module.my.ui.state.myrecipe.write.Ingredient
import com.zipdabang.zipdabang_android.module.my.ui.state.myrecipe.write.Step

data class CompleteRecipePreview(
    val isLoading : Boolean = false,

    val recipeList: List<CompleteRecipesWithImgPreviewRecipe>,
    val totalElements: Int,

    val error : String = ""
)
