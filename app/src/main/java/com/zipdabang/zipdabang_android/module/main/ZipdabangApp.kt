package com.zipdabang.zipdabang_android.module.main

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.bottom.BottomMenuContent
import com.zipdabang.zipdabang_android.module.bottom.ui.BottomNavigationBar
import com.zipdabang.zipdabang_android.module.bottom.ui.Navigation
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.DrawerContent
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZipdabangApp(){
    //navigation에 필요한 변수들
    var isBottomNavigationSelected = remember { mutableStateOf<BottomMenuContent>(BottomMenuContent.home) }
    val navController = rememberNavController()

    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route
    //val backStack = backStackEntry.value?.backStack ?: emptyList()
    Log.e("entry", currentRoute.toString())
    //Log.e("entryList", "$backStack")

    Scaffold( //material3 Scaffold를 이용함
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                              },
                backStackEntry = backStackEntry
                //onItemSelected = { isSelected -> isBottomNavigationSelected.value = isSelected }
                )},
        snackbarHost={ /*밑에 알람 뜨는거 여기서 커스텀 가능함*/},
        containerColor = Color.White,
        contentColor = Color.Black,
        content = {
            Box(Modifier.padding(it)) {
                Navigation(navController = navController)
            }
        }
    )

}


@Preview
@Composable
fun PreviewBottomNav(){
    ZipdabangApp()
}