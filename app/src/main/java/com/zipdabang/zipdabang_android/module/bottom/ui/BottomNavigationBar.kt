package com.zipdabang.zipdabang_android.module.bottom.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zipdabang.zipdabang_android.core.navigation.MarketScreen
import com.zipdabang.zipdabang_android.module.bottom.BottomMenuContent
import com.zipdabang.zipdabang_android.module.main.MainScreen
import com.zipdabang.zipdabang_android.module.market.ui.MarketScreen
import com.zipdabang.zipdabang_android.ui.component.CustomMarketReady
import com.zipdabang.zipdabang_android.ui.theme.NavBlack
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.selects.whileSelect
import kotlinx.serialization.descriptors.setSerialDescriptor

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
) {
    val home_screens = listOf(
        BottomMenuContent.market,
        BottomMenuContent.basket,
        BottomMenuContent.home,
        BottomMenuContent.recipes,
        BottomMenuContent.my
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = home_screens.any { it.route == currentDestination?.route }


    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = Color.White,
        ) {
            home_screens.forEach { screen ->
                val selected = currentDestination?.hierarchy?.any{
                it.route == screen.route
            } == true

                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController,
                    selected = selected,
                )
            }
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen : BottomMenuContent,
    currentDestination: NavDestination?,
    navController: NavHostController,
    selected: Boolean,
    ) {
    val showDialog = remember { mutableStateOf(false) }
    val isFirst = remember { mutableStateOf(true) }


        BottomNavigationItem(
            selected = selected,
            onClick = {
                if (screen.route != MarketScreen.Home.route) {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            },
            selectedContentColor = ZipdabangandroidTheme.Colors.Latte,
            unselectedContentColor = NavBlack,
            icon = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (selected) {
                        Icon(
                            painter = painterResource(id = screen.activeIcon),
                            contentDescription = null
                        )

                        Text(
                            text = screen.title,
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp
                        )


                    } else {
                        Icon(
                            painter = painterResource(id = screen.inactiveIcon),
                            contentDescription = null,
                        )

                        Text(
                            text = screen.title,
                            textAlign = TextAlign.Center,
                            fontSize = 10.sp
                        )
                    }
                }
            })
    }







