package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerValue
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.common.TogglePreferenceException
import com.zipdabang.zipdabang_android.ui.component.AppBarCollapsing
import com.zipdabang.zipdabang_android.ui.component.Pager
import com.zipdabang.zipdabang_android.ui.component.loadXmlDrawable
import com.zipdabang.zipdabang_android.ui.theme.DialogBackground

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RecipeDetailScreen(
    navController: NavController,
    recipeId: Int?,
    onClickBackIcon: () -> Unit,
    onClickProfile: (Int) -> Unit,
    onClickReport: (Int) -> Unit,
    onClickBlock: (Int) -> Unit,
    onClickCart: (String) -> Unit,
    onClickDelete: (Int) -> Unit,
    onClickEdit: (Int) -> Unit,
    onClickCommentReport: (Int) -> Unit,
    onClickCommentBlock: (Int) -> Unit,
    onClickCommentEdit: (Int) -> Unit,
    onClickCommentDelete: (Int) -> Unit
){

    Log.d("RecipeDetail", "$recipeId")

    //drawer에 필요한 drawerState랑 scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val viewModel = hiltViewModel<RecipeDetailViewModel>()

    recipeId?.let {
        viewModel.getRecipeDetail(it)
    }

    val recipeDetailState by remember {
        derivedStateOf { viewModel.recipeDetailState.value }
    }

    val toggleLikeState = viewModel.toggleLikeState
    val toggleScrapState = viewModel.toggleScrapState

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

    val pagerState = rememberPagerState()

    AppBarCollapsing(
        startIcon = loadXmlDrawable(resId = R.drawable.recipe_arrow_left),
        endIcon = loadXmlDrawable(resId = R.drawable.recipe_more_white),
        imageUrl = recipeDetailState.recipeDetailData?.recipeInfo?.thumbnailUrl ?: "null",
        onClickStartIcon = onClickBackIcon,
        onClickEndIcon = {

        },
        deviceSize = viewModel.getDeviceSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {

            item {
                RecipeIntro(
                    profileUrl = recipeDetailState.recipeDetailData?.profileUrl ?: "null",
                    recipeTitle = recipeDetailState.recipeDetailData?.recipeInfo?.recipeName ?: "null",
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
                                viewModel.toggleLike(it.recipeId)
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
                                viewModel.toggleScrap(it.recipeId)
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
                    TabItem.RecipeInfo(recipeDetailState, onClickCart),
                    TabItem.Comment(
                        comments = recipeDetailState.recipeDetailData?.recipeInfo?.comments ?: 0,
                        recipeId = recipeId ?: -1,
                        onClickReport = onClickCommentReport,
                        onClickBlock = onClickCommentBlock,
                        onClickDelete = onClickCommentDelete,
                        onClickEdit = onClickCommentEdit
                    )
                ),
                pagerState = pagerState,
                deviceSize = viewModel.getDeviceSize()
            )

        }
    }
}