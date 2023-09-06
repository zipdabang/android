package com.zipdabang.zipdabang_android.module.recipes.ui

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCard
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipePreviewState
import com.zipdabang.zipdabang_android.module.recipes.ui.viewmodel.RecipeMainViewModel
import com.zipdabang.zipdabang_android.ui.component.GroupHeader
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.launch

@Composable
fun RecipeMenuScreen(
    modifier: Modifier,
    onCategoryClick: (Int) -> Unit,
    onOwnerTypeClick: (String) -> Unit,
    onRecipeClick: (Int) -> Unit,
    onBannerClick: (String) -> Unit
) {

    val mainViewModel = hiltViewModel<RecipeMainViewModel>()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        mainViewModel.apply {
            scope.launch {
                getRecipeBanners()
            }
            scope.launch {
                getRecipeCategoryList()
            }
            scope.launch {
                getRecipesByOwnerType(OwnerType.ALL)
            }
            scope.launch {
                getRecipesByOwnerType(OwnerType.INFLUENCER)
            }
            scope.launch {
                getRecipesByOwnerType(OwnerType.USER)
            }
        }
    }

    val bannerState = mainViewModel.banners.value
    val categoryState = mainViewModel.categoryList.value
    val everyoneRecipeState = mainViewModel.everyRecipeState.value
    val influencerRecipeState = mainViewModel.influencerRecipeState.value
    val userRecipeState = mainViewModel.userRecipeState.value

    val likeToggleState = mainViewModel.toggleLikeResult.value
    val scrapToggleState = mainViewModel.toggleScrapResult.value

    val recipeStateMap = mapOf(
        OwnerType.ALL to everyoneRecipeState,
        OwnerType.INFLUENCER to influencerRecipeState,
        OwnerType.USER to userRecipeState
    )

    val recipeStateList = listOf(
        everyoneRecipeState,
        influencerRecipeState,
        userRecipeState
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // TODO 레시피 배너 가져오는 url 변경하기!!

            RecipeBanner(bannerState = bannerState, onClickBanner = onBannerClick)

            Spacer(modifier = Modifier.height(16.dp))

            RecipeCategoryList(
                categoryState = categoryState,
                onCategoryClick = onCategoryClick
            )

            // lazy vertical grid


            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 18.dp,
                        bottom = 12.dp
                    )
            )

            // lazyrows

            RecipePreviewByOwner(
                recipeStateList = recipeStateList,
                onOwnerTypeClick = onOwnerTypeClick,
                onRecipeClick = onRecipeClick
            )

            Spacer(modifier = Modifier.height(40.dp))

        }
    }

    if (likeToggleState.isLoading || scrapToggleState.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            color = ZipdabangandroidTheme.Colors.Choco
        )
    }
}