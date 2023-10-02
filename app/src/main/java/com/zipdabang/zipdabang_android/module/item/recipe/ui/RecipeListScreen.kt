package com.zipdabang.zipdabang_android.module.item.recipe.ui

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.item.recipe.common.RecipeSubtitleState
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeListViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.AppBarWithFullFunction
import com.zipdabang.zipdabang_android.ui.component.FloatingActionButton
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import kotlinx.coroutines.launch

@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    categoryState: RecipeSubtitleState,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onItemClick: (Int) -> Unit
) {

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val lazyListState = rememberLazyListState()
    val viewModel = hiltViewModel<RecipeListViewModel>()

    val type: RecipeSubtitleState by remember {
        derivedStateOf {
            categoryState
        }
    }

    Log.d("ownerType - on screen", "$type")

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
                    AppBarWithFullFunction(
                        startIcon = R.drawable.ic_topbar_backbtn,
                        endIcon1 = R.drawable.ic_topbar_search,
                        endIcon2 = R.drawable.ic_topbar_menu,
                        onClickStartIcon = {
                            onBackClick()
                            viewModel.deleteAllRecipes()
                        },
                        onClickEndIcon1 = {},
                        onClickEndIcon2 = { scope.launch { drawerState.open() } },
                        centerText = categoryState.let {
                            if (it.categoryId == -1 && it.ownerType != null) {
                                "레시피.zip"
                            } else if (it.categoryId != null && it.ownerType == null) {
                                when (it.categoryId) {
                                    0 -> "전체"
                                    1 -> "커피"
                                    2 -> "논카페인"
                                    3 -> "티"
                                    4 -> "에이드"
                                    5 -> "스무디"
                                    6 -> "과일 음료"
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
                        title = "나의 레시피 공유하기"
                    ) {
                        onShareClick()
                    }
                }
            ) { padding ->
                RecipeList(
                    modifier = Modifier.padding(padding),
                    onItemClick = onItemClick,
                    category = categoryState
                ) {
                    categoryState.let {
                        if (it.categoryId == -1 && it.ownerType != null) {
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
        drawerState = drawerState,
        navController = navController
    )

    val currentOnBack by rememberUpdatedState(onBackClick)

    // 뒤로가기 버튼을 눌렀을 때 callback을 실행
    val backCallback = remember {
        // Always intercept back events. See the SideEffect for
        // a more complete version
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBack()
                viewModel.deleteAllRecipes()
            }
        }
    }

    SideEffect {
        backCallback.isEnabled = true
    }

    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner, backDispatcher) {
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}