package com.zipdabang.zipdabang_android.module.my.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.basket.ui.BasketReadyPage
import com.zipdabang.zipdabang_android.ui.component.AppBarDefault
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import kotlinx.coroutines.launch

@Composable
fun ShoppingScreen(
    navController: NavController,
    onClickBack : ()->Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    // 뒤로가기 제어
    /*BackHandler(
        enabled = true,
        onBack= {
            if(drawerState.isOpen){
                scope.launch{
                    drawerState.close()
                }
            }
            else{
                onClickBack()
            }
        }
    )*/
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
                        centerText = stringResource(id = R.string.my_myshopping)
                    )
                },
                containerColor = Color.White,
                contentColor = Color.White,
            ){
                //val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize()
                        //.verticalScroll(scrollState)
                        .background(Color.White)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight()
                        .padding(bottom= it.calculateBottomPadding(),top =120.dp)){
                        BasketReadyPage()
                    }
                }
            }
        },
        drawerState = drawerState,
        navController = navController,
    )
}

@Preview
@Composable
fun PreviewShoppingScreen() {
    ShoppingScreen(navController = rememberNavController(),onClickBack = {})
}
