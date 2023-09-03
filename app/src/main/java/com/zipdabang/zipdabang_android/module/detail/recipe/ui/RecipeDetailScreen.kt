package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
    onClickLike: (Int) -> Unit,
    onClickScrap: (Int) -> Unit,
    onClickCart: (String) -> Unit,
    onClickDelete: (Int) -> Unit,
    onClickEdit: (Int) -> Unit
){
    //drawer에 필요한 drawerState랑 scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val viewModel = hiltViewModel<RecipeDetailViewModel>()

    recipeId?.let {
        viewModel.getRecipeDetail(it)
    }

    val recipeDetailState = viewModel.recipeDetailState.value
    val toggleLikeState = viewModel.toggleLikeState.value
    val toggleScrapState = viewModel.toggleScrapState.value

    var isLikeChecked by remember {
        mutableStateOf(recipeDetailState.recipeDetailData?.recipeInfo?.isLiked)
    }

    var isScrapChecked by remember {
        mutableStateOf(recipeDetailState.recipeDetailData?.recipeInfo?.isScrapped)
    }

    var likes by remember {
        mutableStateOf(recipeDetailState.recipeDetailData?.recipeInfo?.likes)
    }

    var scraps by remember {
        mutableStateOf(recipeDetailState.recipeDetailData?.recipeInfo?.scraps)
    }

    ModalDrawer(
        scaffold = {
            AppBarCollapsing(
                startIcon = loadXmlDrawable(resId = R.drawable.recipe_arrow_left),
                endIcon = loadXmlDrawable(resId = R.drawable.recipe_more_white),
                imageUrl = recipeDetailState.recipeDetailData?.recipeInfo?.thumbnailUrl ?: "null",
                onClickStartIcon = onClickBackIcon,
                onClickEndIcon = { scope.launch { drawerState.open() } }
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
                                isOwner = recipeDetailState.recipeDetailData.isOwner ?: false,
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

                    item {
                        val pagerState = rememberPagerState()

                        Pager(
                            tabsList = listOf(
                                TabItem.RecipeInfo(recipeDetailState, onClickCart),
                                TabItem.Comment(
                           recipeDetailState.recipeDetailData?.recipeInfo?.comments ?: 0
                                )
                            ),
                            pagerState = pagerState
                        )
                    }
                }
            }
            /*Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AppBarHome(
                        endIcon1 = R.drawable.ic_topbar_search,
                        endIcon2 = R.drawable.ic_topbar_menu,
                        onClickEndIcon1 = {},
                        onClickEndIcon2 = { scope.launch { drawerState.open() } },
                        centerText = "집다방"
                    )
                },
                containerColor = Color.White,
                contentColor = Color.Black,
                content = {
                    Text("detailRecipes", modifier = Modifier.padding(it))
                }
            )*/
        },
        drawerState = drawerState
    )
}