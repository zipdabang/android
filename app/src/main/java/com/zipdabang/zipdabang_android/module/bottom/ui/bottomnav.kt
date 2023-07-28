package com.zipdabang.zipdabang_android.module.bottom.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController


@Composable
fun BottomNav(){

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, onItemClick = {
                navController.navigate(it.route)
            })
        }
    ) {
        Box(Modifier.padding(it)){
            Navigation(navController = navController)
        }
    }
}