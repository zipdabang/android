package com.zipdabang.zipdabang_android.module.recipes.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.module.recipes.data.banner.BannerImageItem
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipeBannerState
import com.zipdabang.zipdabang_android.ui.component.Banner
import com.zipdabang.zipdabang_android.ui.component.BannerForRecipe
import com.zipdabang.zipdabang_android.ui.component.BannerLoading

@Composable
fun RecipeBanner(
    bannerState: RecipeBannerState,
    onClickBanner: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .aspectRatio(9 / 5.5f)
            .padding(bottom = 10.dp)
    ) {
        if (bannerState.isLoading) {
            BannerLoading()
        } else {
            BannerForRecipe(
                banners = bannerState.banners ?: emptyList(),
                onClick = onClickBanner
            )
        }
    }
}