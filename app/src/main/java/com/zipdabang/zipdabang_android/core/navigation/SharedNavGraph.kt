package com.zipdabang.zipdabang_android.core.navigation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.CommentMgtFailureException
import com.zipdabang.zipdabang_android.common.TogglePreferenceException
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentViewModel
import com.zipdabang.zipdabang_android.module.comment.ui.ReportCommentInfoState
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.BlockNoticeViewModel
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeDetailLoading
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeDetailScreen
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeDetailViewModel
import com.zipdabang.zipdabang_android.module.search.ui.SearchCategoryScreen
import com.zipdabang.zipdabang_android.module.search.ui.SearchScreen
import com.zipdabang.zipdabang_android.ui.component.LoginRequestDialog
import com.zipdabang.zipdabang_android.ui.component.Notice
import com.zipdabang.zipdabang_android.ui.component.RecipeDeleteDialog
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
fun NavGraphBuilder.SharedNavGraph(
    navController: NavController,
    outerNavController: NavController,
    showSnackBar: (String) -> Unit
){

    navigation(
        startDestination = SharedScreen.DetailRecipe.route,
        route = SHARED_ROUTE
    ){
        composable(
            route = SharedScreen.BlockedRecipe.route,
            arguments = listOf(
                navArgument(name = "recipeId") { type = NavType.IntType },
                navArgument(name = "ownerId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: -1
            val ownerId = backStackEntry.arguments?.getInt("ownerId") ?: -1

            val viewModel = hiltViewModel<BlockNoticeViewModel>()
            val cancelState = viewModel.cancelState.value

            Notice(
                contentText = stringResource(id = R.string.notice_blocked_recipe),
                buttonText = stringResource(id = R.string.button_cancel_block)
            ) {
                viewModel.cancelUserBlock(ownerId)
            }

            if (cancelState.isLoading == true) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = ZipdabangandroidTheme.Colors.Choco
                    )
                }
            }

            if (cancelState.isLoading == false && cancelState.data == true) {
                navController.navigate(route = SharedScreen.DetailRecipe.passRecipeId(recipeId)) {
                    popUpTo(route = SharedScreen.BlockedRecipe.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        }
        
        composable(
            route = SharedScreen.DetailRecipe.route,
            arguments = listOf(
                navArgument(name = "recipeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val currentBackStackEntry = backStackEntry?.destination?.route
            LaunchedEffect(key1 = true) {
                val backStackEntries: List<NavBackStackEntry> = navController.currentBackStack.first()
                for (entry in backStackEntries) {
                    val route = entry.destination.route
                    Log.d("SharedNavGraph", "route : ${route}")
                }
            }
            
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

/*            if (toggleLikeState.value.errorMessage != null
                || toggleScrapState.value.errorMessage != null
            ) {
                throw TogglePreferenceException
            }*/

            val pagerState = rememberPagerState()

            val currentPlatform = recipeDetailViewModel.currentPlatform.value
            val isOwner = recipeDetailState.recipeDetailData?.isOwner
            val ownerId = recipeDetailState.recipeDetailData?.ownerId ?: 0
            var isExpandedForOwner by remember { mutableStateOf(false) }
            var isExpandedForNotOwner by remember { mutableStateOf(false) }

            val deviceSize = recipeDetailViewModel.getDeviceSize()

            val recipeReportId = recipeDetailViewModel.recipeReportState.value.reportId
            val commentReportId = recipeCommentViewModel.commentReportState.value.reportId
            val commentIdForReport = recipeCommentViewModel.commentReportState.value.commentId ?: 0

            var showDeleteDialog by remember {
                mutableStateOf(false)
            }

            var showLoginRequestDialog by remember {
                mutableStateOf(false)
            }

            val recipeDeleteState = recipeDetailViewModel.recipeDeleteState.value

            if (recipeDeleteState.isLoading == false && recipeDeleteState.data == true) {
                navController.popBackStack()
            }

            LoginRequestDialog(
                showDialog = showLoginRequestDialog,
                setShowDialog = { changedValue ->
                    showLoginRequestDialog = changedValue
                }
            ) {
                outerNavController.navigate(AUTH_ROUTE){
                    popUpTo(SharedScreen.DetailRecipe.route) {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            }

            RecipeDeleteDialog(
                showDialog = showDeleteDialog,
                setShowDialog = { changedValue ->
                    showDeleteDialog = changedValue
                }
            ) {
                recipeDetailViewModel.deleteRecipe(recipeId = recipeId)
            }

            if (recipeDetailState.isLoading) {
                Log.d("SharedNavGraph", "recipe loading")
                RecipeDetailLoading()
            } else {
                Log.d("SharedNavGraph", "recipe loading ended")
                RecipeDetailScreen(
                    recipeId = recipeId,
                    ownerId = ownerId,
                    onClickBackIcon = {
                        navController.popBackStack()
                    },
                    onClickMenuIcon = {
                        if (currentPlatform == CurrentPlatform.NONE
                            || currentPlatform == CurrentPlatform.TEMP
                        ) {
                            showLoginRequestDialog = true
                        } else {
                            if (isOwner == true) {
                                isExpandedForOwner = !isExpandedForOwner
                            } else {
                                isExpandedForNotOwner = !isExpandedForNotOwner
                            }
                        }
                    },
                    onClickProfile = { profileOwnerId ->
                        navController.navigate(
                            route = MyScreen.OtherPage.passUserId(profileOwnerId)
                        ) {
                            launchSingleTop = true
                        }
                    },
                    // onClickCart = { keyword -> },
                    onClickRecipeDelete = {
                        showDeleteDialog = true
                    },
                    onClickRecipeEdit = {
                        navController.navigate(route = MyScreen.RecipeWrite.passRecipeId(recipeId)) {
                            launchSingleTop = true
                        }
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
                        recipeDetailViewModel.blockUser(ownerId)
                        scope.launch {
                            showSnackBar("해당 이용자를 차단했어요")
                        }
                        navController.popBackStack()
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
                        showSnackBar("해당 이용자를 차단했어요")

                    },
                    onClickCommentDelete = { commentId ->
                        recipeCommentViewModel.deleteComment(recipeId, commentId)
                    },
                    onClickCommentEdit = { commentId, newContent ->
                        recipeCommentViewModel.editComment(recipeId, commentId, newContent)
                    },
                    onClickCommentSubmit = { recipeId, content ->
                        try {
                            if (currentPlatform == CurrentPlatform.TEMP
                                || currentPlatform == CurrentPlatform.NONE) {
                                showLoginRequestDialog = true

                            } else {
                                recipeCommentViewModel.postComment(recipeId, content)
                            }
                        } catch (e: CommentMgtFailureException) {
                            Log.d("comment submit", "edit failure")
                        } catch (e: Exception) {
                            Log.d("comment submit", "unknown failure : ${e.message}")
                        }
                    },
                    onClickRecipeLike = { changedState ->
                        try {
                            if (currentPlatform == CurrentPlatform.NONE
                                || currentPlatform == CurrentPlatform.TEMP) {
                                showLoginRequestDialog = true
                            } else {
                                if (isOwner == true) {
                                    showSnackBar("본인 레시피에 좋아요를 누를 수 없습니다.")
                                } else {
                                    recipeDetailViewModel.toggleLike(recipeId)
                                }
                            }
                        } catch (e: Exception) {
                            Log.d("RecipeDetail", "${e.message}")
                        } catch (e: java.lang.Exception) {
                            Log.d("RecipeDetail", "${e.message}")
                        }
                    },
                    onClickRecipeScrap = { changedState ->
                        try {
                            if (currentPlatform == CurrentPlatform.NONE
                                || currentPlatform == CurrentPlatform.TEMP) {
                                showLoginRequestDialog = true
                            } else {
                                if (isOwner == true) {
                                    showSnackBar("본인 레시피를 스크랩 할 수 없습니다.")
                                } else {
                                    recipeDetailViewModel.toggleScrap(recipeId)
                                }
                            }
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
            )
        ){
               val categoryId = it.arguments?.getInt("categoryId")
               val keyword = it.arguments?.getString("keyword")

              SearchCategoryScreen(
                  onRecipeItemClick = {
                          recipeid -> navController.navigate(SharedScreen.DetailRecipe.passRecipeId(recipeid))
                  }
                  )

        }
        }



    }




