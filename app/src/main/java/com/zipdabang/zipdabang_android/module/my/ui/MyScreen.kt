package com.zipdabang.zipdabang_android.module.my.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoDataViewModel
import com.zipdabang.zipdabang_android.module.bottom.BottomMenuContent
import com.zipdabang.zipdabang_android.module.bottom.ui.BottomNavigationBar
import com.zipdabang.zipdabang_android.ui.component.AppBarDefault
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.AppBarMy
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MyScreen(){
    //drawer에 필요한 drawerState랑 scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    //val tokenStoreViewModel = hiltViewModel<ProtoDataViewModel>()

    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AppBarMy(
                        endIcon = R.drawable.ic_topbar_menu,
                        onClickEndIcon = { scope.launch { drawerState.open() } },
                        centerText = "집다방"
                    )
                },
                containerColor = Color.White,
                contentColor = Color.Black,
                content = {
                    Surface(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize(),
                        color = Color.White
                    ) {
                        Column(
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxSize(),
                        ) {
                            Text(
                                text="로그아웃",
                                modifier = Modifier.clickable(
                                    onClick = {
                                        //tokenStoreViewModel.updateAccessToken("")
                                        //tokenStoreViewModel.updateRefreshToken("")
                                    }
                                )
                            )
                        }
                    }
                }
            )
        },
        drawerState = drawerState
    )

}

@Preview
@Composable
fun PreviewMyScreen() {
    MyScreen()
}
