package com.zipdabang.zipdabang_android.module.drawer.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer

@Composable
fun NoticeScreen(
    navController: NavController
){
    //drawer에 필요한 drawerState랑 scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AppBarSignUp(
                        navigationIcon = R.drawable.ic_topbar_backbtn,
                        onClickNavIcon = { /*drawer로 돌아오기*/},
                        centerText = stringResource(id = R.string.app_name)
                    )
                },
                containerColor = Color.White,
                contentColor = Color.Black,
                content = {
                    Text(text="공지사항", modifier = Modifier.padding(it))
                }
            )
        },
        drawerState = drawerState,
        navController = navController
    )
}