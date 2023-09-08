package com.zipdabang.zipdabang_android.common

import androidx.compose.runtime.Composable
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentPage
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeDetailState
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeInfoPage

typealias ComposableFun = @Composable () -> Unit
sealed class TabItem(val tabTitle: String, val screen: ComposableFun) {

    class RecipeInfo(
        private val recipeDetailState: RecipeDetailState,
        private val onClickCart: (String) -> Unit
    ): TabItem(
        tabTitle = "상세 정보",
        screen = { RecipeInfoPage(recipeDetailState, onClickCart) }
    )
    class Comment(val comments: Int)
        : TabItem(tabTitle = "댓글 ${comments}개", screen = { RecipeCommentPage() })
}