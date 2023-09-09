package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.TabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.module.detail.recipe.ui.RecipeDetailState
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
fun LazyListScope.Pager(
    tabsList: List<TabItem>,
    pagerState: PagerState,
    deviceHeight: Float
) {
    item {
        Column(
            // TODO 디바이스 가로 길이 가져오기
            modifier = Modifier.fillMaxWidth().height(500.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp)
            ) {
                Tabs(tabs = tabsList, pagerState = pagerState)
            }

            TabContent(tabs = tabsList, pagerState = pagerState)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState) {

    val scope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        indicator = { tabPositions ->

            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                color = ZipdabangandroidTheme.Colors.Strawberry
            )
        }
    ) {

        tabs.forEachIndexed { index, tabItem ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = tabItem.tabTitle,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold))
                    )
                },
                selectedContentColor = ZipdabangandroidTheme.Colors.Strawberry,
                unselectedContentColor = Color(0x80262D31)
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabContent(
    tabs: List<TabItem>,
    pagerState: PagerState
) {
    HorizontalPager(
        count = tabs.size,
        state = pagerState
    ) { page ->
        tabs[page].screen()
    }
}


/*
@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun PagerPreview() {
    // real usage
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val recipeDetailState = RecipeDetailState()
        val pagerState = rememberPagerState()
        val tabsList = listOf(TabItem.RecipeInfo(recipeDetailState, { string -> }), TabItem.Comment(1))
        Pager(tabsList = tabsList, pagerState = pagerState)
    }

}*/
