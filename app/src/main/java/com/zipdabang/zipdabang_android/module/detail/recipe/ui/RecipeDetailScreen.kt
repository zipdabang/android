package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.module.comment.ui.PostCommentState
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentState
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import com.zipdabang.zipdabang_android.module.recipes.ui.state.PreferenceToggleState
import com.zipdabang.zipdabang_android.ui.component.AppBarCollapsing
import com.zipdabang.zipdabang_android.ui.component.Pager
import com.zipdabang.zipdabang_android.ui.component.RecipeDeleteDialog
import com.zipdabang.zipdabang_android.ui.component.Separator
import com.zipdabang.zipdabang_android.ui.component.loadXmlDrawable

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RecipeDetailScreen(
    recipeId: Int,
    ownerId: Int,
    onClickBackIcon: () -> Unit,
    onClickMenuIcon: () -> Unit,
    onClickProfile: (Int) -> Unit,
    // onClickCart: (String) -> Unit,
    onClickRecipeDelete: () -> Unit,
    onClickRecipeEdit: () -> Unit,
    onClickRecipeReport: (Int) -> Unit,
    onClickRecipeBlock: () -> Unit,
    onClickCommentReport: (Int, Int) -> Unit,
    onClickCommentBlock: () -> Unit,
    onClickCommentEdit: (Int, String) -> Unit,
    onClickCommentDelete: (Int) -> Unit,
    onClickCommentSubmit: (Int, String) -> Unit,
    onClickRecipeLike: (Boolean) -> Unit,
    onClickRecipeScrap: (Boolean) -> Unit,
    showSnackBar: (String) -> Unit,
    recipeDetailState: RecipeDetailState,
    likes: Int,
    scraps: Int,
    isLikeChecked: Boolean,
    isScrapChecked: Boolean,
    toggleLikeState: PreferenceToggleState,
    toggleScrapState: PreferenceToggleState,
    pagerState: PagerState,
    onDismissDropdown: () -> Unit,
    isDropdownExpandedForOwner: Boolean,
    isDropdownExpandedForNotOwner: Boolean,
    deviceSize: DeviceScreenSize,
    showCommentReport: (Int, Int, Int) -> Unit,
    showCommentBlock: (Int) -> Unit,
    setRecipeReport: (Boolean) -> Unit,
    setRecipeBlock: (Boolean) -> Unit,
    recipeReportActivated: Boolean,
    recipeBlockActivated: Boolean,
    commentReportActivated: Boolean,
    commentBlockActivated: Boolean,
    recipeReportId: Int,
    commentReportId: Int,
    commentIdForReport: Int,
    onRecipeReportContentChange: (Int) -> Unit,
    onCommentReportContentChange: (Int) -> Unit,
    setCommentReport: (Boolean) -> Unit,
    setCommentBlock: (Boolean) -> Unit,
    commentItems: LazyPagingItems<RecipeCommentState>,
    postCommentResult: PostCommentState
) {

    Log.i("RecipeDetail", "recipeId : $recipeId")

    val isOwner = recipeDetailState.recipeDetailData?.isOwner ?: false

    val numOfComments = recipeDetailState.recipeDetailData?.recipeInfo?.comments ?: 0
    val thumbnailUrl = recipeDetailState.recipeDetailData?.recipeInfo?.thumbnailUrl ?: ""
    val profileUrl = recipeDetailState.recipeDetailData?.profileUrl ?: ""
    val recipeTitle = recipeDetailState.recipeDetailData?.recipeInfo?.recipeName ?: ""
    val recipeOwner = recipeDetailState.recipeDetailData?.recipeInfo?.nickname ?: ""
    val recipeIntro = recipeDetailState.recipeDetailData?.recipeInfo?.intro ?: ""
    val recipeDetailData = recipeDetailState.recipeDetailData

    val tabsList = listOf(
        TabItem.RecipeInfo(recipeDetailData = recipeDetailData),
        TabItem.Comment(
            comments = numOfComments,
            recipeId = recipeId,
            onClickDelete = onClickCommentDelete,
            onClickEdit = onClickCommentEdit, // 수정한 댓글 제출
            onClickSubmit = onClickCommentSubmit,
            onClickProfile = onClickProfile,
            showCommentReport = showCommentReport,
            showCommentBlock = showCommentBlock,
            commentItems = commentItems,
            postResult = postCommentResult
        )
    )

    AppBarCollapsing(
        title = "레시피",
        startIcon = loadXmlDrawable(resId = R.drawable.recipe_arrow_left),
        endIcon = loadXmlDrawable(resId = R.drawable.recipe_more_white),
        imageUrl = thumbnailUrl,
        onClickStartIcon = onClickBackIcon,
        onClickEndIcon = onClickMenuIcon,
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                RecipeIntro(
                    ownerId = ownerId,
                    profileUrl = profileUrl,
                    recipeTitle = recipeTitle,
                    recipeOwner = recipeOwner,
                    recipeIntro =  recipeIntro,
                    onClickProfile = onClickProfile
                )

                recipeDetailState.recipeDetailData?.recipeInfo?.let {
                    RecipeDetailPreference(
                        recipeId = recipeId,
                        isOwner = isOwner,
                        likes = likes,
                        scraps = scraps,
                        isLikeChecked = isLikeChecked,
                        isScrapChecked = isScrapChecked,
                        likeStateFlow = toggleLikeState,
                        scrapStateFlow = toggleScrapState,
                        onLikeClick = onClickRecipeLike,
                        onScrapClick = onClickRecipeScrap,
                        onDeleteClick = onClickRecipeDelete,
                        onEditClick = onClickRecipeEdit
                    )
                }

                Separator()

            }

            Pager(
                tabsList = tabsList,
                pagerState = pagerState,
                deviceSize = deviceSize
            )
        }
    }

    RecipeDetailDropdown(
        isDropdownExpandedForNotOwner = isDropdownExpandedForNotOwner,
        isDropdownExpandedForOwner = isDropdownExpandedForOwner,
        onDismissDropdown = onDismissDropdown,
        onClickRecipeEdit = onClickRecipeEdit,
        onClickRecipeDelete = onClickRecipeDelete,
        showRecipeReport = setRecipeReport,
        showRecipeBlock = setRecipeBlock
    )

    RecipeDetailDialog(
        recipeReportActivated = recipeReportActivated,
        recipeId = recipeId,
        recipeReportId = recipeReportId,
        setRecipeReport = setRecipeReport,
        onClickRecipeReport = onClickRecipeReport,
        onRecipeReportContentChange = onRecipeReportContentChange,
        recipeBlockActivated = recipeBlockActivated,
        setRecipeBlock = setRecipeBlock,
        onClickRecipeBlock = onClickRecipeBlock,
        commentReportActivated = commentReportActivated,
        commentIdForReport = commentIdForReport,
        commentReportId = commentReportId,
        setCommentReport = setCommentReport,
        onClickCommentReport = onClickCommentReport,
        onCommentReportContentChange = onCommentReportContentChange,
        commentBlockActivated = commentBlockActivated,
        setCommentBlock = setCommentBlock,
        onClickCommentBlock = onClickCommentBlock
    )
}