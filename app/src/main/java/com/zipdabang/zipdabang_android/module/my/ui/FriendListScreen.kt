package com.zipdabang.zipdabang_android.module.my.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.zipdabang.zipdabang_android.ui.component.AppBarDefault
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.component.SearchBar
import kotlinx.coroutines.launch

@Composable
fun FriendListScreen(
    onClickBack : ()->Unit,
    navController: NavController,
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
                        centerText = stringResource(id = R.string.my_friendlist)
                    )
                },
                containerColor = Color.White,
                contentColor = Color.White,
            ){
                Surface(
                    modifier = Modifier.padding(it)
                ){
                    Box(
                        modifier = Modifier.padding(16.dp, 10.dp, 16.dp,0.dp)
                    ){
                        SearchBar(hintText = stringResource(id = R.string.my_searchbar_person))
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
fun PreviewFriendListScreen() {
    FriendListScreen(navController = rememberNavController(), onClickBack = {})
}
