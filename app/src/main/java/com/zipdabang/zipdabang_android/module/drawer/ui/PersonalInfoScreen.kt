package com.zipdabang.zipdabang_android.module.drawer.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.drawer.data.Long_Term
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.launch

@Composable
fun PersonalInfoScreen(
    navController: NavController,
    onClickBack : () -> Unit,
){

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scrollState = rememberScrollState()

 ModalDrawer(
    scaffold = {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = { onClickBack()},
                centerText = stringResource(id= R.string.drawer_privacy)
            )
        },
        containerColor = Color.White,
        contentColor = Color.Black,
        content = {
            Column(modifier = Modifier
                .verticalScroll(scrollState)
                .padding(top = it.calculateTopPadding(), start = 8.dp, end = 8.dp, bottom = 8.dp)
                .background(Color.White)
            ) {

                Text(
                    text = "개인정보처리방침",
                    style = ZipdabangandroidTheme.Typography.twentytwo_700,
                    color = ZipdabangandroidTheme.Colors.Typo
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text= Long_Term.personalInfo,
                    style = ZipdabangandroidTheme.Typography.twelve_700,
                    color = ZipdabangandroidTheme.Colors.Typo
                )

            }

        })
    },
     drawerState = drawerState,
     navController = navController)

}