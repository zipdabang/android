package com.zipdabang.zipdabang_android.module.market.ui

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.zipdabang.zipdabang_android.module.item.goods.ui.GoodsCard
import com.zipdabang.zipdabang_android.module.market.data.marketCategory.Category_Product
import com.zipdabang.zipdabang_android.ui.shimmeringEffect


@Composable
fun ShimmeringMarketItem(){
    Box(modifier = Modifier
        .size(height= 224.dp, width = 160.dp)
        .padding(horizontal = 4.dp)
        .shimmeringEffect())
}



