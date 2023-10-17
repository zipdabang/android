package com.zipdabang.zipdabang_android.ui.component

import android.provider.SyncStateContract.Columns
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRow
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
fun LazyListScope.Pager(
    tabsList: List<TabItem>,
    pagerState: PagerState,
    deviceSize: DeviceScreenSize
) {
    item {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height((deviceSize.height - (deviceSize.width / 2 + 70)).dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
            ) {
                Tabs(tabs = tabsList, pagerState = pagerState)
            }

            TabContent(tabs = tabsList, pagerState = pagerState)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CategoryPager(
    tabsList: List<TabItem>,
    pagerState: PagerState,
    deviceSize: DeviceScreenSize,
    onLoginRequest: () -> Unit,
    showSnackbar: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(420.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            CategoryTabs(tabs = tabsList, pagerState = pagerState)
        }

        TabContent(tabs = tabsList, pagerState = pagerState)
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
fun ColumnPagers(
    tabsList: List<TabItem>,
    pagerState: PagerState,
   // deviceSize: DeviceScreenSize
) {

    Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
            ) {
                Tabs(tabs = tabsList, pagerState = pagerState)
            }

            TabContent(tabs = tabsList, pagerState = pagerState)
        }

}
@Composable
@OptIn(ExperimentalPagerApi::class)
fun ColumnPagersNoPadding(
    tabsList: List<TabItem>,
    pagerState: PagerState,
    // deviceSize: DeviceScreenSize
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Tabs(tabs = tabsList, pagerState = pagerState)
        }

        TabContent(tabs = tabsList, pagerState = pagerState)
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
fun CategoryTabs(tabs: List<TabItem>, pagerState: PagerState) {

    val scope = rememberCoroutineScope()

    //https://www.youtube.com/watch?app=desktop&v=ZdADYzYF7O8
    ScrollableTabRow(
        modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            IndicatorForCategory(tabPositions = tabPositions, pagerState = pagerState)
        },
        divider = {},
        containerColor = Color.White,
        contentColor = Color.White,
        edgePadding = 16.dp,
    ) {

        tabs.forEachIndexed { index, tabItem ->
            Tab(
                modifier = Modifier.zIndex(2f),
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
                        fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium))
                    )
                },
                selectedContentColor = Color.White,
                unselectedContentColor = ZipdabangandroidTheme.Colors.Choco
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun IndicatorForCategory(
    tabPositions: List<TabPosition>, pagerState: PagerState
) {
    val transition = updateTransition(pagerState.currentPage, label = "")
    val indicatorStart by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 50f)
            } else {
                spring(dampingRatio = 1f, stiffness = 1000f)
            }
        },
        label = ""
    ) {
        tabPositions[it].left
    }

    val indicatorEnd by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 1000f)
            } else {
                spring(dampingRatio = 1f, stiffness = 50f)
            }
        },
        label = ""
    ) {
        tabPositions[it].right
    }
    
    Box(modifier = Modifier
        .offset(x = indicatorStart)
        .wrapContentSize(align = Alignment.BottomStart)
        .width(indicatorEnd - indicatorStart)
        .padding(2.dp)
        .fillMaxSize()
        .background(
            color = ZipdabangandroidTheme.Colors.Choco,
            shape = RoundedCornerShape(50)
        )
        .zIndex(1f)
    )
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
