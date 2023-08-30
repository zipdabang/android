package com.zipdabang.zipdabang_android.module.recipes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.module.recipes.data.banner.BannerImageItem
import com.zipdabang.zipdabang_android.module.recipes.ui.state.RecipeBannerState
import com.zipdabang.zipdabang_android.ui.component.Banner
import com.zipdabang.zipdabang_android.ui.component.BannerLoading

@Composable
fun RecipeBanner(
    bannerState: RecipeBannerState
) {
    val imageList = bannerState.banners?.map {
        it.imageUrl
    } ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        if (bannerState.isLoading) {
            BannerLoading()
        } else {
            Banner(
                // TODO 하드코딩 고치기
                images = listOf(
                    "https://github.com/zipdabang/android/assets/101035437/203d0287-59b9-41dc-98f1-86164ac056ed",
                    "https://github.com/zipdabang/android/assets/101035437/047e0364-01a8-4e8f-b815-cc7d924aa3bd",
                    "https://github.com/zipdabang/android/assets/101035437/86af2ac2-3cdb-410f-a661-7a9638158ea8"
                )
            )
        }
    }
}