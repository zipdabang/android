package com.zipdabang.zipdabang_android.module.drawer.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.DrawerUserInfoViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.ImageWithIcon
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun UserInfoProfileScreen(
//    drawerUserInfoViewModel : DrawerUserInfoViewModel = hiltViewModel(),
    onClickBack : ()->Unit,
) {
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
                        onClickNavIcon = { onClickBack() },
                        centerText = stringResource(id = R.string.my_profileedit)
                    )
                },
                containerColor = Color.White,
                contentColor = Color.White,
                content = {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(48.dp)
                        ){
                            Column(
                                modifier = Modifier.clickable(onClick={ }),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ){
                                AsyncImage(
                                    model = R.drawable.img_gallery,
                                    contentDescription = "직접 선택",
                                    modifier = Modifier.size(104.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.my_profileedit_select),
                                    style = ZipdabangandroidTheme.Typography.fourteen_500,
                                    color = ZipdabangandroidTheme.Colors.Typo
                                )
                            }
                            Column(
                                modifier = Modifier.clickable(onClick={}),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ){
                                AsyncImage(
                                    model = R.drawable.img_profile,
                                    contentDescription = "기본 프로필",
                                    modifier = Modifier.size(104.dp)
                                )
                                Text(
                                    text = stringResource(id = R.string.my_profileedit_basicprofile),
                                    style = ZipdabangandroidTheme.Typography.fourteen_500,
                                    color = ZipdabangandroidTheme.Colors.Typo
                                )
                            }
                        }

                    }
                }
            )
        },
        drawerState = drawerState,
    )
}

@Preview
@Composable
fun PreviewUserInfoProfileScreen() {
    UserInfoProfileScreen(
        onClickBack={},
    )
}