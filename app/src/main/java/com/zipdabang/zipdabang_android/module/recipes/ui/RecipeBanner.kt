package com.zipdabang.zipdabang_android.module.recipes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.module.recipes.data.banner.BannerImageItem
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipeBannerState
import com.zipdabang.zipdabang_android.ui.component.Banner
import com.zipdabang.zipdabang_android.ui.component.BannerLoading

@Composable
fun RecipeBanner(
    bannerState: RecipeBannerState,
    onClickBanner: (String) -> Unit
) {
    val imageList = bannerState.banners?.map {
        it.imageUrl
    } ?: emptyList()

    Column(
        modifier = Modifier
            .aspectRatio(9 / 5f)
            .padding(bottom = 16.dp)
    ) {
        if (bannerState.isLoading) {
            BannerLoading()
        } else {
            Banner(
                // TODO 하드코딩 고치기
                images = imageList
            )
        }
    }
}