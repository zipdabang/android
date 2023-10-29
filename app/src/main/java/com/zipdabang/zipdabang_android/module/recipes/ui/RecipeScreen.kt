package com.zipdabang.zipdabang_android.module.recipes.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.core.navigation.AUTH_ROUTE
import com.zipdabang.zipdabang_android.core.navigation.SharedScreen
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerCategory
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.HotRecipeViewModel
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeMainViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.LoginRequestDialog
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun RecipeScreen(
    navController: NavController,
    onSearchIconClick: () -> Unit,
    onCategoryClick: (Int) -> Unit,
    onOwnerTypeClick: (String) -> Unit,
    onRecipeClick: (Int) -> Unit,
    onBlockedRecipeClick: (Int, Int) -> Unit,
    onBannerClick: (String) -> Unit,
    onLoginRequest: () -> Unit,
    showSnackbar: (String) -> Unit,
    showLoginRequestDialog: Boolean,
    setShowLoginRequestDialog: (Boolean) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val viewModel = hiltViewModel<RecipeMainViewModel>()
    val hotRecipeViewModel = hiltViewModel<HotRecipeViewModel>()

    val hotAllRecipes = hotRecipeViewModel.hotAllRecipeState.value
    val hotCoffeeRecipes = hotRecipeViewModel.hotCoffeeRecipeState.value
    val hotCaffeineFreeRecipes = hotRecipeViewModel.hotCaffeineFreeRecipeState.value
    val hotTeaRecipes = hotRecipeViewModel.hotTeaRecipeState.value
    val hotAdeRecipes = hotRecipeViewModel.hotAdeRecipeState.value
    val hotSmoothieRecipes = hotRecipeViewModel.hotSmoothieRecipeState.value
    val hotFruitRecipes = hotRecipeViewModel.hotFruitRecipeState.value
    val hotWellBeingRecipes = hotRecipeViewModel.hotWellBeingRecipeState.value

    val currentPlatform = viewModel.currentPlatform.value

    Log.i("recipescreen", "$hotAllRecipes")

    val banners = viewModel.banners.value
    val categories = viewModel.categoryList.value
    Log.i("RecipeScreen", "$categories")
/*
    val likeState = viewModel.toggleLikeResult.collectAsState().value
    val scrapState = viewModel.toggleScrapResult.collectAsState().value
*/

    val deviceSize = viewModel.getDeviceSize()
    val pagerState = rememberPagerState()

    val checkLoggedIn = remember {
        {
            if (currentPlatform == CurrentPlatform.TEMP
                || currentPlatform == CurrentPlatform.NONE) {
                setShowLoginRequestDialog(true)
                false
            } else {
                true
            }
        }
    }

    val onLikeClick = remember {
        { recipeId: Int ->
            scope.async {
                viewModel.toggleItemLike(recipeId)
            }
        }
    }

    val onScrapClick = remember {
        { recipeId: Int ->
            scope.async {
                viewModel.toggleItemScrap(recipeId)
            }
        }
    }


/*    val onLikeClick = { recipeId: Int ->
        if (currentPlatform == CurrentPlatform.TEMP
            || currentPlatform == CurrentPlatform.NONE) {
            setShowLoginRequestDialog(true)
        } else {
            viewModel.toggleLike(recipeId = recipeId)
            Log.d("RecipeScreen", "$likeState")
            if (likeState.errorMessage != null) {
                Log.d("RecipeScreen", "my recipe")
                showSnackbar("레시피에 좋아요를 누를 수 없습니다.")
            }
        }
    }*/

/*    val onScrapClick = { recipeId: Int ->
        if (currentPlatform == CurrentPlatform.TEMP
            || currentPlatform == CurrentPlatform.NONE) {
            setShowLoginRequestDialog(true)
        } else {
            viewModel.toggleScrap(recipeId = recipeId)
            if (scrapState.errorMessage != null) {
                showSnackbar("레시피를 스크랩 할 수 없습니다.")
            }
        }
    }*/

    LoginRequestDialog(
        showDialog = showLoginRequestDialog,
        setShowDialog = setShowLoginRequestDialog
    ) {
        onLoginRequest()
    }

    val ownerTypeList = listOf(
        OwnerCategory(
            groupName = OwnerType.OFFICIAL.type,
            title = OwnerType.OFFICIAL.title,
            subTitle = OwnerType.OFFICIAL.subTitle,
            borderColor = Color(0xFFB8AFAB),
            backgroundColor = ZipdabangandroidTheme.Colors.BlackSesame,
            textColor = ZipdabangandroidTheme.Colors.Typo,
            drawable = R.drawable.recipe_category_zipdabang_revised
        ),
        OwnerCategory(
            groupName = OwnerType.BARISTA.type,
            title = OwnerType.BARISTA.title,
            subTitle = OwnerType.BARISTA.subTitle,
            borderColor = Color(0xFFC29789),
            backgroundColor = ZipdabangandroidTheme.Colors.Strawberry,
            textColor = Color.White,
            drawable = R.drawable.recipe_category_barista
        ),
        OwnerCategory(
            groupName = OwnerType.USER.type,
            title = OwnerType.USER.title,
            subTitle = OwnerType.USER.subTitle,
            borderColor = Color(0xFF856F65),
            backgroundColor = ZipdabangandroidTheme.Colors.Choco,
            textColor = Color.White,
            drawable = R.drawable.recipe_category_our
        )
    )

    val tabs = listOf(
        TabItem.Coffee(
            hotItems = hotCoffeeRecipes,
            onRecipeClick = onRecipeClick,
            onBlockedRecipeClick = onBlockedRecipeClick,
            checkLoggedIn = checkLoggedIn,
            onScrapClick = onScrapClick,
            onLikeClick = onLikeClick,
            showSnackbar = showSnackbar
        ),
        TabItem.CaffeineFree(
            hotItems = hotCaffeineFreeRecipes,
            onRecipeClick = onRecipeClick,
            onBlockedRecipeClick = onBlockedRecipeClick,
            checkLoggedIn = checkLoggedIn,
            onScrapClick = onScrapClick,
            onLikeClick = onLikeClick,
            showSnackbar = showSnackbar
        ),
        TabItem.Tea(
            hotItems = hotTeaRecipes,
            onRecipeClick = onRecipeClick,
            checkLoggedIn = checkLoggedIn,
            onBlockedRecipeClick = onBlockedRecipeClick,
            onScrapClick = onScrapClick,
            onLikeClick = onLikeClick,
            showSnackbar = showSnackbar
        ),
        TabItem.Ade(
            hotItems = hotAdeRecipes,
            onRecipeClick = onRecipeClick,
            checkLoggedIn = checkLoggedIn,
            onBlockedRecipeClick = onBlockedRecipeClick,
            onScrapClick = onScrapClick,
            onLikeClick = onLikeClick,
            showSnackbar = showSnackbar
        ),
        TabItem.Smoothie(
            hotItems = hotSmoothieRecipes,
            onRecipeClick = onRecipeClick,
            checkLoggedIn = checkLoggedIn,
            onBlockedRecipeClick = onBlockedRecipeClick,
            onScrapClick = onScrapClick,
            onLikeClick = onLikeClick,
            showSnackbar = showSnackbar
        ),
        TabItem.Fruit(
            hotItems = hotFruitRecipes,
            onRecipeClick = onRecipeClick,
            checkLoggedIn = checkLoggedIn,
            onBlockedRecipeClick = onBlockedRecipeClick,
            onScrapClick = onScrapClick,
            onLikeClick = onLikeClick,
            showSnackbar = showSnackbar
        ),
        TabItem.WellBeing(
            hotItems = hotWellBeingRecipes,
            onRecipeClick = onRecipeClick,
            checkLoggedIn = checkLoggedIn,
            onBlockedRecipeClick = onBlockedRecipeClick,
            onScrapClick = onScrapClick,
            onLikeClick = onLikeClick,
            showSnackbar = showSnackbar
        ),
        TabItem.All(
            hotItems = hotAllRecipes,
            onRecipeClick = onRecipeClick,
            checkLoggedIn = checkLoggedIn,
            onBlockedRecipeClick = onBlockedRecipeClick,
            onScrapClick = onScrapClick,
            onLikeClick = onLikeClick,
            showSnackbar = showSnackbar
        )
    )

/*    if (likeState.errorMessage != null) {
        Log.d("RecipeScreen", likeState.errorMessage!!)
        showSnackbar(likeState.errorMessage!!)
    }

    if (scrapState.errorMessage != null) {
        showSnackbar(scrapState.errorMessage)
    }*/


    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AppBarHome(
                        endIcon1 = R.drawable.ic_topbar_search,
                        endIcon2 = R.drawable.ic_topbar_menu,
                        onClickEndIcon1 = { onSearchIconClick() },
                        onClickEndIcon2 = { scope.launch { drawerState.open() } },
                        centerText = "집다방"
                    )
                },
                containerColor = Color.White,
                contentColor = Color.Black,
            ) { padding ->
                RecipeMenuScreen(
                    modifier = Modifier.padding(padding),
                    onCategoryClick = onCategoryClick,
                    onOwnerTypeClick = onOwnerTypeClick,
                    onBannerClick = onBannerClick,
                    banners = banners,
                    categories = categories,
                    ownerTypeList = ownerTypeList,
                    hotRecipes = tabs,
                    deviceSize = deviceSize,
                    pagerState = pagerState,
                    onLoginRequest = onLoginRequest,
                    showSnackbar = showSnackbar
                )


            }
        },
        drawerState = drawerState,
        navController = navController
    )
}

