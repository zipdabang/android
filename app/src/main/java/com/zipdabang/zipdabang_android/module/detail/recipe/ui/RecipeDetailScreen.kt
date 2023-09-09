package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
import com.zipdabang.zipdabang_android.ui.component.AppBarCollapsing
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.component.Pager
import com.zipdabang.zipdabang_android.ui.component.loadXmlDrawable
import com.zipdabang.zipdabang_android.ui.theme.DialogBackground
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RecipeDetailScreen(
    navController: NavController,
    recipeId: Int?,
    onClickBackIcon: () -> Unit,
    onClickProfile: (Int) -> Unit,
    onClickReport: (Int) -> Unit,
    onClickBlock: (Int) -> Unit,
    onClickLike: (Int) -> Unit,
    onClickScrap: (Int) -> Unit,
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
    val toggleLikeState = viewModel.toggleLikeState.value
    val toggleScrapState = viewModel.toggleScrapState.value

    var isLikeChecked by rememberSaveable {
        mutableStateOf(recipeDetailState.recipeDetailData?.recipeInfo?.isLiked)
    }

    var isScrapChecked by rememberSaveable {
        mutableStateOf(recipeDetailState.recipeDetailData?.recipeInfo?.isScrapped)
    }

    var likes by rememberSaveable {
        mutableStateOf(recipeDetailState.recipeDetailData?.recipeInfo?.likes)
    }

    var scraps by rememberSaveable {
        mutableStateOf(recipeDetailState.recipeDetailData?.recipeInfo?.scraps)
    }

    val pagerState = rememberPagerState()

    AppBarCollapsing(
        startIcon = loadXmlDrawable(resId = R.drawable.recipe_arrow_left),
        endIcon = loadXmlDrawable(resId = R.drawable.recipe_more_white),
        imageUrl = recipeDetailState.recipeDetailData?.recipeInfo?.thumbnailUrl ?: "null",
        onClickStartIcon = onClickBackIcon,
        onClickEndIcon = {

        },
        deviceHeight = viewModel.getDeviceHeight()
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
                        likes = it.likes,
                        scraps = it.scraps,
                        isLikeChecked = it.isLiked,
                        isScrapChecked = it.isScrapped,
                        onLikeClick = { changedState ->
                            viewModel.toggleLike(it.recipeId)
                            // TODO state에 따른 분기처리
                            isLikeChecked = changedState
                            likes =
                                if (isLikeChecked as Boolean) likes?.plus(1)
                                else likes?.minus(1)
                            onClickLike(it.recipeId)
                        },
                        onScrapClick = { changedState ->
                            viewModel.toggleScrap(it.recipeId)
                            // TODO state에 따른 분기처리
                            isScrapChecked = changedState
                            scraps =
                                if (isScrapChecked as Boolean) scraps?.plus(1)
                                else scraps?.minus(1)
                            onClickScrap(it.recipeId)
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
                deviceHeight = viewModel.getDeviceHeight()
            )

        }
    }
}