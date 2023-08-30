package com.zipdabang.zipdabang_android.module.recipes.ui

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

@Composable
fun RecipeMenuScreen(
    modifier: Modifier,
    onCategoryClick: (Int) -> Unit,
    onOwnerTypeClick: (String) -> Unit,
    onRecipeClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
    onScrapClick: (Int) -> Unit
) {

    val mainViewModel = hiltViewModel<RecipeMainViewModel>()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        mainViewModel.apply {
            // TODO 동시다발적으로 실행되도록 각 메소드마다 coroutine scope 내부에서 실행
            // TODO 배너 불러오기 추가 필요
            getRecipeCategoryList()
            getRecipesByOwnerType(OwnerType.ALL)
            getRecipesByOwnerType(OwnerType.INFLUENCER)
            getRecipesByOwnerType(OwnerType.USER)
        }
    }

    val bannerState = mainViewModel.banners.value
    val categoryState = mainViewModel.categoryList.value
    val everyoneRecipeState = mainViewModel.everyRecipeState.value
    val influencerRecipeState = mainViewModel.influencerRecipeState.value
    val userRecipeState = mainViewModel.userRecipeState.value

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

    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        // TODO 레시피 배너 가져오는 url 변경하기!!
        RecipeBanner(bannerState = bannerState)

        // lazy vertical grid
        RecipeCategoryList(
            categoryState = categoryState,
            onCategoryClick = onCategoryClick
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )

        // lazyrows

        RecipePreviewByOwner(
            recipeStateList = recipeStateList,
            onOwnerTypeClick = onOwnerTypeClick,
            onLikeClick = onLikeClick,
            onScrapClick = onScrapClick,
            onRecipeClick = onRecipeClick
        )

        Spacer(modifier = Modifier.height(40.dp))

    }
}