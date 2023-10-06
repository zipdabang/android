package com.zipdabang.zipdabang_android.module.recipes.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCard
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCardLoading
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerType
import com.zipdabang.zipdabang_android.module.recipes.ui.state.PreferenceToggleState
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipePreviewState
import com.zipdabang.zipdabang_android.ui.component.GroupHeader
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.flow.StateFlow

fun LazyListScope.RecipePreview(
    recipeMapByOwnerType: Map<OwnerType, RecipePreviewState>,
    onOwnerTypeClick: (String) -> Unit,
    onRecipeClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
    onScrapClick: (Int) -> Unit,
    likeState: StateFlow<PreferenceToggleState>,
    scrapState: StateFlow<PreferenceToggleState>,
) {
    item {
        recipeMapByOwnerType.forEach { recipesByOwnerType ->
            RecipePreviewByOwner(
                title = recipesByOwnerType.key,
                state = recipesByOwnerType.value,
                onOwnerTypeClick = onOwnerTypeClick,
                onRecipeClick = onRecipeClick,
                onLikeClick = onLikeClick,
                onScrapClick = onScrapClick,
                likeState = likeState,
                scrapState = scrapState
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }

}