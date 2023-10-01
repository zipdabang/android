package com.zipdabang.zipdabang_android.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentPage
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeDetailState
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeInfoPage

typealias ComposableFun = @Composable () -> Unit
sealed class TabItem(val tabTitle: String, val screen: ComposableFun) {

    class RecipeInfo(
        private val recipeDetailState: RecipeDetailState,
        // private val onClickCart: (String) -> Unit
    ): TabItem(
        tabTitle = "상세 정보",
        screen = { RecipeInfoPage(recipeDetailState) }
    )
    class Comment(
        val comments: Int,
        val recipeId: Int,
        private val onClickReport: (Int, Int, Int) -> Unit,
        private val onClickBlock: (Int) -> Unit,
        private val onClickEdit: (Int, Int, String) -> Unit,
        private val onClickDelete: (Int, Int) -> Unit,
        private val showCommentReport: (Int, Int, Int) -> Unit
    ) : TabItem(
        tabTitle = "댓글 ${comments}개",
        screen = {
            RecipeCommentPage(
                commentCount = comments,
                recipeId = recipeId,
                onClickReport = onClickReport,
                onClickBlock = onClickBlock,
                onClickDelete = onClickDelete,
                onClickEdit = onClickEdit,
                showCommentReport = showCommentReport
            )
        }
    )
}