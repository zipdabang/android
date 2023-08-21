package com.zipdabang.zipdabang_android.module.sign_up.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.navigation.AuthScreen
import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.AuthSharedViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.MainAndSubTitle
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonOutLined
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RegisterUserInfoScreen(
    navController: NavHostController,
    authSharedViewModel: AuthSharedViewModel = hiltViewModel(),
    onClickBack: ()->Unit,
    onClickNext: ()->Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = { onClickBack() },
                centerText = stringResource(id = R.string.signup)
            )
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp, 10.dp, 16.dp, 0.dp)
                    .background(Color.White)
                    .fillMaxWidth()
            ) {
                MainAndSubTitle(
                    mainValue = stringResource(id = R.string.signup_userinfo_maintitle),
                    mainTextStyle = ZipdabangandroidTheme.Typography.twentytwo_700,
                    mainTextColor = ZipdabangandroidTheme.Colors.Typo,
                    subValue = stringResource(id = R.string.signup_userinfo_subtitle),
                    subTextStyle = ZipdabangandroidTheme.Typography.sixteen_300,
                    subTextColor = ZipdabangandroidTheme.Colors.Typo
                )

                //기본 정보
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight()
                ){
                    Text(
                        text= stringResource(id = R.string.signup_userinfo_basicinfo),
                        style = ZipdabangandroidTheme.Typography.sixteen_300_cafe24,
                        color = ZipdabangandroidTheme.Colors.Choco
                    )
                }
                //본인 인증
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight()
                ){
                    Text(
                        text= stringResource(id = R.string.signup_userinfo_validation),
                        style = ZipdabangandroidTheme.Typography.sixteen_300_cafe24,
                        color = ZipdabangandroidTheme.Colors.Choco
                    )
                }
                //상세 정보
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .wrapContentHeight()
                ){
                    Text(
                        text= stringResource(id = R.string.signup_userinfo_detailinfo),
                        style = ZipdabangandroidTheme.Typography.sixteen_300_cafe24,
                        color = ZipdabangandroidTheme.Colors.Choco
                    )
                }
            }

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.padding(16.dp,0.dp,16.dp, 12.dp)
            ){
                PrimaryButtonWithStatus(
                    text= stringResource(id = R.string.signup_btn_inputdone),
                    onClick={ onClickNext() },
                    isFormFilled = true //stateTermsValidate
                )
            }
        }
    }

}

@Preview
@Composable
fun PreviewRegisterUserInfoScreen(){
    val navController = rememberNavController()
    RegisterUserInfoScreen(
        navController = navController,
        onClickBack = {
            navController.navigate(AuthScreen.Terms.route)
        },
        onClickNext = {
            navController.navigate(AuthScreen.RegisterNickname.route)
        }
    )
}