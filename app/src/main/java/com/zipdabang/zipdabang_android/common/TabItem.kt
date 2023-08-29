package com.zipdabang.zipdabang_android.common

import androidx.compose.runtime.Composable
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeCommentPage
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeDetailState
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeInfoPage

typealias ComposableFun = @Composable () -> Unit
sealed class TabItem(val tabTitle: String, val screen: ComposableFun) {

    class RecipeInfo(
        private val recipeDetailState: RecipeDetailState
    ): TabItem(
        tabTitle = "레시피",
        screen = { RecipeInfoPage(recipeDetailState) }
    )
    class Comment(val comments: Int)
        : TabItem(tabTitle = "댓글 ${comments}개", screen = { RecipeCommentPage() })
}