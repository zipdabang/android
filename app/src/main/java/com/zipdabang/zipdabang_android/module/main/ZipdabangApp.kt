package com.zipdabang.zipdabang_android.module.main

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.module.bottom.BottomMenuContent
import com.zipdabang.zipdabang_android.module.bottom.ui.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZipdabangApp(){
    //navigation에 필요한 변수들
    var isBottomNavigationSelected = remember { mutableStateOf<BottomMenuContent>(BottomMenuContent.home) }
    val navController = rememberNavController()

    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route
    Log.e("entry", currentRoute.toString())

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
//                onItemClick = {
//                    navController.navigate(it.route)
//                              },
//                backStackEntry = backStackEntry
                )},
        snackbarHost={ /*밑에 알람 뜨는거 여기서 커스텀 가능함*/ },
        containerColor = Color.White,
        contentColor = Color.Black,
        content = {
            Box(Modifier.padding(it)) {
                //Navigation(navController = navController)
            }
        }
    )

}


@Preview
@Composable
fun PreviewBottomNav(){
    ZipdabangApp()
}