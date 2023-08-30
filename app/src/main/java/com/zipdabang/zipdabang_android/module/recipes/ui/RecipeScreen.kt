package com.zipdabang.zipdabang_android.module.recipes.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import kotlinx.coroutines.launch

@Composable
fun RecipeScreen(
    onCategoryClick: (Int) -> Unit,
    onOwnerTypeClick: (String) -> Unit,
    onRecipeClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
    onScrapClick: (Int) -> Unit,
    onBannerClick: (String) -> Unit
){
    //drawer에 필요한 drawerState랑 scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AppBarHome(
                        endIcon1 = R.drawable.ic_topbar_search,
                        endIcon2 = R.drawable.ic_topbar_menu,
                        onClickEndIcon1 = {},
                        onClickEndIcon2 = { scope.launch { drawerState.open() } },
                        centerText = "집다방"
                    )
                },
                containerColor = Color.White,
                contentColor = Color.Black,
//                floatingActionButton =
            ) { padding ->
                // scaffoldpadding parameter 미사용 시, 화면이 앱바를 고려하지 않고 맨 위에 붙어서 나와서
                // 화면이 앱바에 가려짐

                // val scrollState = rememberScrollState()

                RecipeMenuScreen(
                    modifier = Modifier.padding(padding),
                    onCategoryClick = onCategoryClick,
                    onOwnerTypeClick = onOwnerTypeClick,
                    onRecipeClick = onRecipeClick,
                    onLikeClick = onLikeClick,
                    onScrapClick = onScrapClick,
                    onBannerClick = onBannerClick
                )

            }
        },
        drawerState = drawerState
    )


}