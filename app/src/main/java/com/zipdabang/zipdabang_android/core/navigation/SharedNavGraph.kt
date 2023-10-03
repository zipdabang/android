package com.zipdabang.zipdabang_android.core.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zipdabang.zipdabang_android.common.TogglePreferenceException
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentViewModel
import com.zipdabang.zipdabang_android.module.comment.ui.ReportCommentInfoState
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeDetailScreen
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeDetailViewModel
import com.zipdabang.zipdabang_android.module.search.ui.SearchCategoryScreen
import com.zipdabang.zipdabang_android.module.search.ui.SearchScreen
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
fun NavGraphBuilder.SharedNavGraph(
    navController: NavController,
    showSnackBar: (String) -> Unit
){

    navigation(
        startDestination = SharedScreen.DetailRecipe.route,
        route = SHARED_ROUTE
    ){
        composable(
            route = SharedScreen.DetailRecipe.route,
            arguments = listOf(
                navArgument(name = "recipeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: -1

            val scope = rememberCoroutineScope()

            val recipeDetailViewModel = hiltViewModel<RecipeDetailViewModel>()
            val recipeCommentViewModel = hiltViewModel<RecipeCommentViewModel>()

            recipeDetailViewModel.getRecipeDetail(recipeId)

            val postCommentResult = recipeCommentViewModel.postResult.collectAsState()
            val commentItems =
                recipeCommentViewModel.getComments(recipeId).collectAsLazyPagingItems()

            val recipeBlockOwnerId = recipeDetailViewModel.blockOwnerId.value
            val commentBlockOwnerId = recipeCommentViewModel.blockOwnerId.value

            val recipeDetailState = recipeDetailViewModel.recipeDetailState.value

            val likes = recipeDetailViewModel.likes.collectAsState()
            val scraps = recipeDetailViewModel.scraps.collectAsState()
            val isLikeChecked = recipeDetailViewModel.isLikeChecked.collectAsState()
            val isScrapChecked = recipeDetailViewModel.isScrapChecked.collectAsState()
            val toggleLikeState = recipeDetailViewModel.toggleLikeState.collectAsState()
            val toggleScrapState = recipeDetailViewModel.toggleScrapState.collectAsState()

            if (toggleLikeState.value.errorMessage != null
                || toggleScrapState.value.errorMessage != null
            ) {
                throw TogglePreferenceException
            }

            val pagerState = rememberPagerState()

            val currentPlatform = recipeDetailViewModel.currentPlatform.value
            val isOwner = recipeDetailState.recipeDetailData?.isOwner
            var isExpandedForOwner by remember { mutableStateOf(false) }
            var isExpandedForNotOwner by remember { mutableStateOf(false) }

            val deviceSize = recipeDetailViewModel.getDeviceSize()

            val recipeReportId = recipeDetailViewModel.recipeReportState.value.reportId
            val commentReportId = recipeCommentViewModel.commentReportState.value.reportId
            val commentIdForReport = recipeCommentViewModel.commentReportState.value.commentId ?: 0

            RecipeDetailScreen(
                recipeId = recipeId,
                onClickBackIcon = {
                    navController.popBackStack()
                },
                onClickMenuIcon = {
                    if (currentPlatform == CurrentPlatform.NONE
                        || currentPlatform == CurrentPlatform.TEMP
                    ) {
                        // 온보딩으로 이동

                    } else {
                        if (isOwner == true) {
                            isExpandedForOwner = !isExpandedForOwner
                        } else {
                            isExpandedForNotOwner = !isExpandedForNotOwner
                        }
                    }
                },
                onClickProfile = { ownerId -> },
                // onClickCart = { keyword -> },
                onClickRecipeDelete = {

                },
                onClickRecipeEdit = {

                },
                onClickRecipeReport = { reportId ->
                    Log.i(
                        "reportrecipeusecase",
                        "report clicked, recipeId : $recipeId, reportId: $reportId"
                    )
                    recipeDetailViewModel.reportRecipe(
                        recipeId = recipeId,
                        reportId = reportId
                    )

                    scope.launch {
                        recipeDetailViewModel.recipeReportResult.collectLatest { result ->
                            Log.i("reportrecipeusecase", "$result")
                            if (result.isLoading == false && result.data == true) {
                                showSnackBar("해당 게시글을 신고했어요")
                                recipeDetailViewModel.setRecipeBlockDialogStatus(true)
                            } else if (result.isLoading == false && result.data == false) {
                                showSnackBar("게시글 신고 중 오류가 발생했어요.")
                            }
                        }
                    }
                },
                onClickRecipeBlock = {
                    // recipeDetailViewModel.blockUser()
                    // TODO 서버에서 ownerId 추가하면 그 때 blockuser 메소드 추가

                },
                onClickCommentReport = { commentId, reportId ->

                    recipeCommentViewModel.reportComment(
                        recipeId = recipeId,
                        commentId = commentId,
                        reportId = reportId
                    )

                    Log.i(
                        "RecipeDetailScreen > Report Comment",
                        "report clicked, recipeId : $recipeId, reportId: $reportId, commentId: $commentId"
                    )

                    scope.launch {
                        recipeCommentViewModel.reportResult.collectLatest { result ->
                            Log.i("reportcommentusecase", "$result")
                            if (!result.isLoading && result.isReportSuccessful == true) {
                                showSnackBar("해당 댓글을 신고했어요")
                                recipeCommentViewModel.setCommentBlockDialogStatus(true)
                            } else if (!result.isLoading && result.isReportSuccessful == false) {
                                showSnackBar("댓글 신고 중 오류가 발생했어요.")
                            }
                        }
                    }
                },
                onClickCommentBlock = {
                    recipeCommentViewModel.blockUser(commentBlockOwnerId)
                    scope.launch {
                        showSnackBar("해당 이용자를 차단했어요")
                    }
                },
                onClickCommentDelete = { commentId ->
                    recipeCommentViewModel.deleteComment(recipeId, commentId)
                },
                onClickCommentEdit = { commentId, newContent ->
                    recipeCommentViewModel.editComment(recipeId, commentId, newContent)
                },
                onClickRecipeLike = { changedState ->
                    try {
                        recipeDetailViewModel.toggleLike(recipeId)
                    } catch (e: Exception) {
                        Log.d("RecipeDetail", "${e.message}")
                    } catch (e: java.lang.Exception) {
                        Log.d("RecipeDetail", "${e.message}")
                    }
                },
                onClickRecipeScrap = { changedState ->
                    try {
                        recipeDetailViewModel.toggleScrap(recipeId)
                    } catch (e: Exception) {
                        Log.d("RecipeDetail", "${e.message}")
                    } catch (e: java.lang.Exception) {
                        Log.d("RecipeDetail", "${e.message}")
                    }
                },
                showSnackBar = showSnackBar,
                recipeDetailState = recipeDetailState,
                likes = likes.value,
                scraps = scraps.value,
                toggleLikeState = toggleLikeState.value,
                toggleScrapState = toggleScrapState.value,
                isLikeChecked = isLikeChecked.value,
                isScrapChecked = isScrapChecked.value,
                onDismissDropdown = {
                    isExpandedForOwner = false
                    isExpandedForNotOwner = false
                },
                isDropdownExpandedForOwner = isExpandedForOwner,
                isDropdownExpandedForNotOwner = isExpandedForNotOwner,
                deviceSize = deviceSize,
                pagerState = pagerState,
                showCommentReport = { commentId, reportId, ownerId ->
                    recipeCommentViewModel.setCommentReportDialogStatus(true)
                    recipeCommentViewModel.setBlockOwnerId(ownerId)
                    recipeCommentViewModel.setCommentReportState(
                        ReportCommentInfoState(
                            recipeId = recipeId,
                            commentId = commentId,
                            reportId = reportId
                        )
                    )
                },
                showCommentBlock = { ownerId ->
                    recipeCommentViewModel.setBlockOwnerId(ownerId)
                    recipeCommentViewModel.setCommentBlockDialogStatus(true)
                },
                setCommentReport = { changedState ->
                    recipeCommentViewModel.setCommentReportDialogStatus(changedState)
                },
                setCommentBlock = { changedState ->
                    recipeCommentViewModel.setCommentBlockDialogStatus(changedState)
                },
                setRecipeReport = {
                    recipeDetailViewModel.setRecipeReportDialogStatus(it)
                },
                setRecipeBlock = {
                    recipeDetailViewModel.setRecipeBlockDialogStatus(it)
                },
                recipeReportActivated = recipeDetailViewModel.isRecipeReportActivated.value,
                recipeBlockActivated = recipeDetailViewModel.isRecipeBlockActivated.value,
                commentReportActivated = recipeCommentViewModel.isCommentReportActivated.value,
                commentBlockActivated = recipeCommentViewModel.isCommentBlockActivated.value,
                recipeReportId = recipeReportId,
                commentReportId = commentReportId,
                commentIdForReport = commentIdForReport,
                onRecipeReportContentChange = { changedId ->
                    recipeDetailViewModel.changeReportContent(changedId)
                },
                onCommentReportContentChange = { changedId ->
                    recipeCommentViewModel.changeReportContent(changedId)
                },
                commentItems = commentItems,
                postCommentResult = postCommentResult.value
            )
        }

        composable(SharedScreen.Search.route){
            SearchScreen(
                navController= navController,
                onRecipeItemClick = {
                    recipeid -> navController.navigate(SharedScreen.DetailRecipe.passRecipeId(recipeid))
                }

            )
        }

        composable(SharedScreen.SearchRecipeCategory.route,
            arguments = listOf(
                navArgument("categoryId"){ type = NavType.IntType },
                navArgument("keyword") { type = NavType.StringType }
            )){
               val categoryId = it.arguments?.getInt("categoryId")
               val keyword = it.arguments?.getString("keyword")

              SearchCategoryScreen(navController,
                  onRecipeItemClick = {
                          recipeid -> navController.navigate(SharedScreen.DetailRecipe.passRecipeId(recipeid))
                  }
                  )

        }
        }



    }




