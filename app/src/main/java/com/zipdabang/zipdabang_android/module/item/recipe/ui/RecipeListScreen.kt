package com.zipdabang.zipdabang_android.module.item.recipe.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.item.recipe.common.RecipeListTitle
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipeCategoryState
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.FloatingActionButton
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import kotlinx.coroutines.launch

@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    categoryState: RecipeListTitle,
    onShareClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
    onScrapClick: (Int) -> Unit
) {

    Log.d("ownerType - on screen", "$categoryState")

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val lazyListState = rememberLazyListState()
    val isScrolled: Boolean by remember {
        derivedStateOf {
            // lazyList의 첫번째 아이템의 좌표가 0을 넘어서면 true
            lazyListState.firstVisibleItemIndex > 0
        }
    }

    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = modifier.fillMaxSize(),
                topBar = {
                    AppBarHome(
                        endIcon1 = R.drawable.ic_topbar_search,
                        endIcon2 = R.drawable.ic_topbar_menu,
                        onClickEndIcon1 = {},
                        onClickEndIcon2 = { scope.launch { drawerState.open() } },
                        centerText = categoryState.let {
                            if (it.categoryId == -1 && it.ownerType != null) {
                                when (it.ownerType) {
                                    OwnerType.ALL.type -> "모든 레시피"
                                    OwnerType.INFLUENCER.type -> "인플루언서 레시피"
                                    OwnerType.USER.type -> "우리들의 레시피"
                                    else -> ""
                                }
                            } else if (it.categoryId != null && it.ownerType == null) {
                                when (it.categoryId) {
                                    0 -> "전체"
                                    1 -> "커피"
                                    2 -> "논카페인"
                                    3 -> "티"
                                    4 -> "에이드"
                                    5 -> "스무디"
                                    6 -> "생과일 음료"
                                    7 -> "건강 음료"
                                    else -> ""
                                }
                            } else {
                                ""
                            }
                        }
                    )
                },
                containerColor = Color.White,
                contentColor = Color.Black,
                floatingActionButton = {
                    FloatingActionButton(
                        isScrolled = isScrolled,
                        icon = R.drawable.zipdabanglogo_white,
                        title = "레시피 공유하기"
                    ) {
                        onShareClick()
                    }
                }
            ) { padding ->
                RecipeList(
                    modifier = Modifier.padding(padding),
                    onItemClick = onItemClick,
                    category = categoryState,
                    onLikeClick = onLikeClick,
                    onScrapClick = onScrapClick
                ) {
                    categoryState.let {
                        if (it.categoryId == null && it.ownerType != null) {
                            when (it.ownerType) {
                                OwnerType.ALL.type -> EveryoneSubtitle()
                                OwnerType.INFLUENCER.type -> InfluencerSubtitle()
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
            }
        },
        drawerState = drawerState
    )
}