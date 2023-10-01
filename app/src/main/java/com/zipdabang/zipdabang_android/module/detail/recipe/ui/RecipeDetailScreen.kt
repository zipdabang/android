package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.common.TogglePreferenceException
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.module.comment.ui.CommentReportState
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentViewModel
import com.zipdabang.zipdabang_android.module.recipes.common.ReportContent
import com.zipdabang.zipdabang_android.ui.component.AppBarCollapsing
import com.zipdabang.zipdabang_android.ui.component.CommentReportDialog
import com.zipdabang.zipdabang_android.ui.component.Pager
import com.zipdabang.zipdabang_android.ui.component.RecipeReportDialog
import com.zipdabang.zipdabang_android.ui.component.loadXmlDrawable
import com.zipdabang.zipdabang_android.ui.theme.DialogBackground

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RecipeDetailScreen(
    navController: NavController,
    recipeId: Int?,
    onClickBackIcon: () -> Unit,
    onClickProfile: (Int) -> Unit,
    // onClickCart: (String) -> Unit,
    onClickDelete: () -> Unit,
    onClickEdit: () -> Unit,
    onClickCommentReport: (Int, Int, Int) -> Unit,
    onClickCommentBlock: (Int) -> Unit,
    onClickCommentEdit: (Int, Int, String) -> Unit,
    onClickCommentDelete: (Int, Int) -> Unit
) {

    Log.d("RecipeDetail", "$recipeId")

    //drawer에 필요한 drawerState랑 scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val recipeDetailViewModel = hiltViewModel<RecipeDetailViewModel>()
    val commentViewModel = hiltViewModel<RecipeCommentViewModel>()

    recipeId?.let {
        recipeDetailViewModel.getRecipeDetail(it)
    }

    val recipeDetailState by remember {
        derivedStateOf { recipeDetailViewModel.recipeDetailState.value }
    }

    val toggleLikeState = recipeDetailViewModel.toggleLikeState
    val toggleScrapState = recipeDetailViewModel.toggleScrapState

    if (toggleLikeState.collectAsState().value.errorMessage != null
        || toggleScrapState.collectAsState().value.errorMessage != null) {
        throw TogglePreferenceException
    }

    var isLikeChecked by rememberSaveable {
        mutableStateOf(
            recipeDetailState.recipeDetailData?.recipeInfo?.isLiked ?: false
        )
    }

    var isScrapChecked by rememberSaveable {
        mutableStateOf(
            recipeDetailState.recipeDetailData?.recipeInfo?.isScrapped ?: false
        )
    }

    var likes by rememberSaveable {
        mutableStateOf(recipeDetailState.recipeDetailData?.recipeInfo?.likes ?: 0)
    }

    var scraps by rememberSaveable {
        mutableStateOf(recipeDetailState.recipeDetailData?.recipeInfo?.scraps ?: 0)
    }

    val currentPlatform = recipeDetailViewModel.currentPlatform.value
    val isOwner = recipeDetailState.recipeDetailData?.isOwner

    var isExpandedForOwner by remember { mutableStateOf(false) }
    var isExpandedForNotOwner by remember { mutableStateOf(false) }

    val pagerState = rememberPagerState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(modifier = Modifier.align(Alignment.TopEnd).padding(vertical = 20.dp,horizontal = 12.dp)) {
            DropdownMenu(
                modifier = Modifier.background(Color.White),
                expanded = isExpandedForOwner,
                onDismissRequest = {
                    isExpandedForOwner = false
                    isExpandedForNotOwner = false
                },
            ) {
                DropdownMenuItem(
                    text = { Text("게시글 수정하기") },
                    onClick = {
                        onClickEdit()
                        isExpandedForOwner = false
                        isExpandedForNotOwner = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("게시글 삭제하기") },
                    onClick = {
                        onClickDelete()
                        isExpandedForOwner = false
                        isExpandedForNotOwner = false
                    }
                )
            }

            DropdownMenu(
                modifier = Modifier.background(Color.White),
                expanded = isExpandedForNotOwner,
                onDismissRequest = {
                    isExpandedForOwner = false
                    isExpandedForNotOwner = false
                },
            ) {
                DropdownMenuItem(
                    text = { Text("게시글 신고하기") },
                    onClick = {
                        // showCommentReport(recipeId, commentItem.commentId, 1)
                        recipeDetailViewModel.setRecipeReportDialogStatus(true)
                        isExpandedForOwner = false
                        isExpandedForNotOwner = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("글쓴이 차단하기") },
                    onClick = {
                        isExpandedForOwner = false
                        isExpandedForNotOwner = false
                    }
                )
            }
        }

        AppBarCollapsing(
            startIcon = loadXmlDrawable(resId = R.drawable.recipe_arrow_left),
            endIcon = loadXmlDrawable(resId = R.drawable.recipe_more_white),
            imageUrl = recipeDetailState.recipeDetailData?.recipeInfo?.thumbnailUrl ?: "null",
            onClickStartIcon = onClickBackIcon,
            onClickEndIcon = {
                if (currentPlatform == CurrentPlatform.NONE
                    || currentPlatform == CurrentPlatform.TEMP) {
                    // 온보딩으로 이동

                } else {
                    if (isOwner == true) {
                        isExpandedForOwner = !isExpandedForOwner
                    } else {
                        isExpandedForNotOwner = !isExpandedForNotOwner
                    }
                }
            },
            onDismissEndIcon = {
                isExpandedForOwner = false
                isExpandedForNotOwner = false
            },
            isExpandedForOwner = isExpandedForOwner,
            isExpandedForNotOwner = isExpandedForNotOwner,
            deviceSize = recipeDetailViewModel.getDeviceSize(),
            onDeleteClick = onClickDelete,
            onEditClick = onClickEdit,
            onReportClick = { reportRecipeId, reportId ->
                recipeDetailViewModel.reportRecipe(reportRecipeId, reportId)
            },
            onBlockClick = { ownerId ->
                recipeDetailViewModel.blockUser(ownerId)
            },
            showRecipeReport = { reportRecipeId, reportId ->
                recipeDetailViewModel.setRecipeReportDialogStatus(true)
                recipeDetailViewModel.setRecipeReportState(
                    RecipeReportState(
                        recipeId = reportRecipeId,
                        reportId = reportId
                    )
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                item {
                    RecipeIntro(
                        profileUrl = recipeDetailState.recipeDetailData?.profileUrl ?: "null",
                        recipeTitle = recipeDetailState.recipeDetailData?.recipeInfo?.recipeName ?: "",
                        recipeOwner = recipeDetailState.recipeDetailData?.recipeInfo?.nickname ?: "",
                        recipeIntro = recipeDetailState.recipeDetailData?.recipeInfo?.intro ?: "",
                        onClickProfile = onClickProfile
                    )
                }

                item {
                    recipeDetailState.recipeDetailData?.recipeInfo?.let {
                        RecipeDetailPreference(
                            recipeId = it.recipeId,
                            isOwner = recipeDetailState.recipeDetailData!!.isOwner ?: false,
                            likes = likes,
                            scraps = scraps,
                            isLikeChecked = isLikeChecked,
                            isScrapChecked = isScrapChecked,
                            likeStateFlow = toggleLikeState,
                            scrapStateFlow = toggleScrapState,
                            onLikeClick = { changedState ->
                                try {
                                    recipeDetailViewModel.toggleLike(it.recipeId)
                                    it.isLiked = !it.isLiked
                                    isLikeChecked = it.isLiked
                                    it.likes = if (isLikeChecked) it.likes + 1
                                    else it.likes - 1
                                    likes = it.likes
                                } catch (e: Exception) {
                                    Log.d("RecipeDetail", "${e.message}")
                                } catch (e: java.lang.Exception) {
                                    Log.d("RecipeDetail", "${e.message}")
                                }
                            },
                            onScrapClick = { changedState ->
                                try {
                                    recipeDetailViewModel.toggleScrap(it.recipeId)
                                    it.isScrapped = changedState
                                    isScrapChecked = it.isScrapped
                                    it.scraps = if (isScrapChecked) it.scraps + 1
                                    else it.scraps - 1
                                    scraps = it.scraps
                                } catch (e: Exception) {
                                    Log.d("RecipeDetail", "${e.message}")
                                } catch (e: java.lang.Exception) {
                                    Log.d("RecipeDetail", "${e.message}")
                                }
                            },
                            onDeleteClick = onClickDelete,
                            onEditClick = onClickEdit
                        )
                    }
                }

                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .background(DialogBackground)
                    )
                }

                Pager(
                    tabsList = listOf(
                        TabItem.RecipeInfo(recipeDetailState),
                        TabItem.Comment(
                            comments = recipeDetailState.recipeDetailData?.recipeInfo?.comments ?: 0,
                            recipeId = recipeId ?: -1,
                            onClickReport = onClickCommentReport,
                            onClickBlock = onClickCommentBlock,
                            onClickDelete = onClickCommentDelete,
                            // 제출
                            onClickEdit = onClickCommentEdit,
                            showCommentReport = { recipeId, commentId, reportId ->
                                commentViewModel.setCommentReportDialogStatus(true)
                                commentViewModel.setCommentReportState(
                                    CommentReportState(
                                        recipeId = recipeId,
                                        commentId = commentId,
                                        reportId = reportId
                                    )
                                )
                            }
                        )
                    ),
                    pagerState = pagerState,
                    deviceSize = recipeDetailViewModel.getDeviceSize()
                )

            }
        }
    }

    if (commentViewModel.isCommentReportActivated.value) {
        CommentReportDialog(
            title = "댓글 신고하기",
            recipeId = recipeId ?: 0,
            commentId = commentViewModel.commentReportState.value.commentId ?: 0,
            reportId = commentViewModel.commentReportState.value.reportId,
            declineText = "취소",
            acceptText = "신고하기",
            reportContentList = ReportContent.contents,
            setShowDialog = { changedState ->
                commentViewModel.setCommentReportDialogStatus(changedState)
            },
            onReportClick = { reportRecipeId, commentId, reportId ->
                commentViewModel.reportComment(
                    recipeId = reportRecipeId,
                    commentId = commentId,
                    reportId = reportId
                )
            },
            onReportContentChange = { changedId ->
                commentViewModel.changeReportContent(changedId)
            }
        )
    }
    
    if (recipeDetailViewModel.isRecipeReportActivated.value) {
        RecipeReportDialog(
            title = "게시글 신고하기",
            recipeId = recipeId ?: 0,
            reportId = recipeDetailViewModel.recipeReportState.value.reportId,
            declineText = "취소",
            acceptText = "신고하기",
            reportContentList = ReportContent.contents,
            setShowDialog = { changedState ->
                recipeDetailViewModel.setRecipeReportDialogStatus(changedState)
            },
            onReportClick = { reportRecipeId, reportId ->
                recipeDetailViewModel.reportRecipe(
                    recipeId = reportRecipeId,
                    reportId = reportId
                )
            },
            onReportContentChange = { changedId ->
                recipeDetailViewModel.changeReportContent(changedId)
            }
        )
    }
}