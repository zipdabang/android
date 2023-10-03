package com.zipdabang.zipdabang_android.common

import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.paging.compose.LazyPagingItems
import com.zipdabang.zipdabang_android.module.comment.ui.PostCommentState
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentPage
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentState
import com.zipdabang.zipdabang_android.module.detail.recipe.domain.RecipeDetailDomain
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeDetailState
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeInfoPage
import com.zipdabang.zipdabang_android.module.my.ui.FollowScreen
import com.zipdabang.zipdabang_android.module.my.ui.FollowingScreen

typealias ComposableFun = @Composable () -> Unit
sealed class TabItem(val tabTitle: String, val screen: ComposableFun) {

    class RecipeInfo(
        private val recipeDetailData: RecipeDetailDomain?,
        // private val onClickCart: (String) -> Unit
    ): TabItem(
        tabTitle = "상세 정보",
        screen = { RecipeInfoPage(recipeDetailData) }
    )
    class Comment(
        val comments: Int,
        val recipeId: Int,
        private val postResult: PostCommentState,
        private val commentItems: LazyPagingItems<RecipeCommentState>,
        private val onClickEdit: (Int, String) -> Unit,
        private val onClickDelete: (Int) -> Unit,
        private val showCommentReport: (Int, Int, Int) -> Unit,
        private val showCommentBlock: (Int) -> Unit
    ) : TabItem(
        tabTitle = "댓글 ${comments}개",
        screen = {
            RecipeCommentPage(
                commentCount = comments,
                recipeId = recipeId,
                onClickDelete = onClickDelete,
                onClickEdit = onClickEdit,
                showCommentReport = showCommentReport,
                showCommentBlock = showCommentBlock,
                postResult = postResult,
                comments = commentItems
            )
        }
    )

    class followList : TabItem(
        tabTitle =  "팔로우",
        screen = {
            FollowScreen()
        }
    )

    class followingList : TabItem(
        tabTitle = "팔로잉",
        screen = {
            FollowingScreen()
        }
    )
}