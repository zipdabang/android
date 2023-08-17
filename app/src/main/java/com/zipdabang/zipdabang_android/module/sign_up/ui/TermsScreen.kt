package com.zipdabang.zipdabang_android.module.sign_up.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.navigation.AuthScreen
import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.AuthSharedViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.MainAndSubTitle
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonOutLined
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun TermsScreen(
    navController: NavHostController,
    authSharedViewModel: AuthSharedViewModel,
    onClickNext: ()->Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = { },
                centerText = stringResource(id = R.string.signup)
            )
        },
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(Color.White)
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp, 10.dp, 16.dp, 0.dp)
                    .background(Color.White)
                    .fillMaxWidth()
            ){
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                ){
                    MainAndSubTitle(
                        mainValue = stringResource(id = R.string.signup_terms_maintitle),
                        mainTextStyle = ZipdabangandroidTheme.Typography.twentytwo_700,
                        mainTextColor = ZipdabangandroidTheme.Colors.Typo,
                        subValue = stringResource(id = R.string.signup_terms_subtitle),
                        subTextStyle = ZipdabangandroidTheme.Typography.sixteen_300,
                        subTextColor =  ZipdabangandroidTheme.Colors.Typo,
                    )
                }

                Spacer(
                    modifier = Modifier
                        .weight(0.8f)
                        .fillMaxWidth()
                )

                Column(
                    modifier = Modifier
                        .weight(8f)
                        .fillMaxWidth()
                ){
                    var isCheckedAllagree by remember { mutableStateOf(true) }

                    CheckBoxWithText(
                        isChecked = isCheckedAllagree,
                        isCheckedChange = {selectedChecked -> isCheckedAllagree = selectedChecked },
                        mainValue = stringResource(id = R.string.signup_terms_allagree),
                        mainTextStyle = ZipdabangandroidTheme.Typography.sixteen_700
                    )
                }

                Box(
                    modifier = Modifier.weight(1.2f)
                ){
                    PrimaryButtonOutLined(
                        borderColor = ZipdabangandroidTheme.Colors.Strawberry,
                        text= stringResource(id = R.string.signup_btn_termsagree),
                        onClick={ onClickNext }
                    )
                }
            }
        }
    }
    
}

@Preview
@Composable
fun PreviewTermsScreen(){
    val navController = rememberNavController()
    TermsScreen(
        navController = navController,
        authSharedViewModel = AuthSharedViewModel(),
        onClickNext = {
            navController.navigate(AuthScreen.RegisterUserInfo.route)
        }
    )
}