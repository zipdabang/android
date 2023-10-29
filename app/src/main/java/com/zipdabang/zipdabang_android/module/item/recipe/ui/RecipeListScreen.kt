package com.zipdabang.zipdabang_android.module.item.recipe.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.module.item.recipe.common.RecipeSort
import com.zipdabang.zipdabang_android.module.item.recipe.common.RecipeSubtitleState
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.common.QueryType
import com.zipdabang.zipdabang_android.module.recipes.data.common.RecipeItem
import com.zipdabang.zipdabang_android.ui.component.AppBarWithFullFunction
import com.zipdabang.zipdabang_android.ui.component.FloatingActionButton
import com.zipdabang.zipdabang_android.ui.component.LoginRequestDialog
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch

@Composable
fun RecipeListScreen(
    navController: NavController,
    currentPlatform: CurrentPlatform,
    total: String,
    sortBy: String,
    queryType: QueryType,
    categoryState: RecipeSubtitleState,
    recipeList: LazyPagingItems<RecipeItem>,
    onSearchIconClick: () -> Unit,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onLoginRequest: () -> Unit,
    onSortChange: (String) -> Unit,
    onLikeClick: (Int) -> Deferred<Boolean>,
    onScrapClick: (Int) -> Deferred<Boolean>,
    showSnackbar: (String) -> Unit
) {
    val TAG = "RecipeListScreen"

    val scope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val sortList = listOf(
        RecipeSort.LATEST,
        RecipeSort.FOLLOW,
        RecipeSort.LIKES
    )

    Log.i(TAG, "ownerType : $categoryState")
    Log.i(TAG, "sortBy : $sortBy")

    var showLoginRequestDialog by remember {
        mutableStateOf(false)
    }

    val checkLoggedIn = {
        if (currentPlatform == CurrentPlatform.TEMP
            || currentPlatform == CurrentPlatform.NONE) {
            showLoginRequestDialog = true
            false
        } else {
            true
        }
    }

    val lazyGridState = rememberLazyGridState()

    val isScrolled: Boolean by remember {
        derivedStateOf {
            // lazyList의 첫번째 아이템의 좌표가 0을 넘어서면 true
            lazyGridState.firstVisibleItemIndex > 0
        }
    }

    val centerText = categoryState.let {
        if (it.categoryId == -1 && it.ownerType != null) {
            "레시피.zip"
        } else if (it.categoryId != null && it.ownerType == null) {
            when (it.categoryId) {
                0 -> "전체"
                1 -> "커피"
                2 -> "논카페인"
                3 -> "차(Tea)"
                4 -> "에이드"
                5 -> "스무디"
                6 -> "과일 음료"
                7 -> "건강 음료"
                else -> ""
            }
        } else {
            "레시피"
        }
    }

    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AppBarWithFullFunction(
                        startIcon = R.drawable.ic_topbar_backbtn,
                        endIcon1 = R.drawable.ic_topbar_search,
                        endIcon2 = R.drawable.ic_topbar_menu,
                        onClickStartIcon = onBackClick,
                        onClickEndIcon1 = onSearchIconClick,
                        onClickEndIcon2 = { scope.launch { drawerState.open() } },
                        centerText = centerText
                    )
                },
                containerColor = Color.White,
                contentColor = Color.Black
            ) { padding ->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                ) {
                    LoginRequestDialog(
                        showDialog = showLoginRequestDialog,
                        setShowDialog = { changedState ->
                            showLoginRequestDialog = changedState
                        },
                        onLoginRequest = onLoginRequest
                    )

                    RecipeList(
                        modifier = Modifier,
                        onItemClick = onItemClick,
                        total = total,
                        sortList = sortList,
                        onSortChange = onSortChange,
                        recipeList = recipeList,
                        checkLoggedIn = checkLoggedIn,
                        onToggleLike = { recipeId ->
                            onLikeClick(recipeId)
                        },
                        onToggleScrap = { recipeId ->
                            onScrapClick(recipeId)
                        },
                        showSnackbar = showSnackbar,
                        lazyGridState = lazyGridState
                    ) {
                        categoryState.let {
                            if (it.categoryId == -1 && it.ownerType != null) {
                                when (it.ownerType) {
                                    OwnerType.OFFICIAL.type -> EveryoneSubtitle()
                                    OwnerType.BARISTA.type -> InfluencerSubtitle()
                                    OwnerType.USER.type -> OurSubtitle()
                                }
                            } else if (it.categoryId != null && it.ownerType == null) {
                                when (it.categoryId) {
                                    0 -> AllSubtitle()
                                    1 -> CoffeeSubtitle()
                                    2 -> NonCaffeineSubtitle()
                                    3 -> TeaSubtitle()
                                    4 -> AdeSubtitle()
                                    5 -> SmoothieSubtitle()
                                    6 -> FruitSubtitle()
                                    7 -> WellBeingSubtitle()
                                    else -> WellBeingSubtitle()
                                }
                            } else {
                                AllSubtitle()
                            }
                        }
                    }

                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 40.dp, end = 16.dp),
                        isScrolled = isScrolled,
                        icon = R.drawable.zipdabanglogo_transparent_normal,
                        title = "나의 레시피 공유하기",
                        onClick = onShareClick
                    )
                }
            }
        },
        drawerState = drawerState,
        navController = navController
    )
}