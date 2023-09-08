package com.zipdabang.zipdabang_android.module.my.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.AppBarDefault
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.component.SearchBar
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyrecipeScreen(
    navController: NavController,
    onClickBack : ()->Unit,
    onClickWrite : ()->Unit,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                topBar = {
                    AppBarDefault(
                        startIcon = R.drawable.ic_topbar_backbtn,
                        endIcon = R.drawable.ic_topbar_menu,
                        onClickStartIcon = { onClickBack() },
                        onClickEndIcon = { scope.launch { drawerState.open() } },
                        centerText = stringResource(id = R.string.my_myrecipe)
                    )
                },
                containerColor = Color.White,
                contentColor = Color.White,
            ){
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                        .background(Color.White)
                ) {
                    Box(
                        modifier = Modifier.padding(16.dp, 10.dp, 16.dp,0.dp)
                    ){
                        SearchBar(hintText = stringResource(id = R.string.my_searchbar_search))
                        //val pagerState = rememberPagerState()
                        //Pager(tabsList = listOf(TabItem()), pagerState = pagerState)
                    }

                    // 레시피 작성하기
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        modifier = Modifier.height(56.dp)
                    ){
                        Box(
                            modifier = Modifier
                                .background(ZipdabangandroidTheme.Colors.Strawberry)
                                .height(56.dp)
                                .fillMaxWidth()
                                .clickable(
                                    onClick = {onClickWrite()}
                                ),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text= stringResource(id = R.string.my_addnewrecipe),
                                color = Color.White,
                                style = ZipdabangandroidTheme.Typography.sixteen_700,
                            )
                        }
                    }
                }
            }
        },
        drawerState = drawerState,
        navController = navController
    )

}

@Preview
@Composable
fun PreviewMyrecipeScreen() {
    MyrecipeScreen(navController = rememberNavController(), onClickBack = {}, onClickWrite = {})
}
