package com.zipdabang.zipdabang_android.module.main

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.core.navigation.MainNavGraph
import com.zipdabang.zipdabang_android.core.navigation.SharedScreen
import com.zipdabang.zipdabang_android.module.bottom.ui.BottomNavigationBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    outerNavController: NavHostController,
    innerNavController: NavHostController
){
    //drawer에 필요한 drawerState랑 scope
  //  val drawerState = rememberDrawerState(DrawerValue.Closed)
  //  val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        contentColor = Color.Black,
        bottomBar = {
            BottomNavigationBar(navController = innerNavController)
        },
        content = {
            Box(
             modifier = Modifier.padding(it)
            ) {
                MainNavGraph(
                    innerNavController = innerNavController,
                    outerNavController = outerNavController
                )
            }
        },
    )
}




