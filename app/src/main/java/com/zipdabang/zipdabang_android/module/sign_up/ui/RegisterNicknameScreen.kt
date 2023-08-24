package com.zipdabang.zipdabang_android.module.sign_up.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.navigation.AuthScreen
import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.AuthSharedViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonOutLined
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.TextFieldErrorAndCorrect
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RegisterNicknameScreen(
    navController: NavHostController,
    authSharedViewModel: AuthSharedViewModel = hiltViewModel(),
    onClickBack: ()->Unit,
    onClickNext: ()->Unit,
) {
    val stateNicknameValue by authSharedViewModel.stateNicknameValue.collectAsState()
    val stateTrycount by authSharedViewModel.stateTrycount.collectAsState()
    val stateIsError by authSharedViewModel.stateIsError.collectAsState()
    val stateIsCorrect by authSharedViewModel.stateIsCorrect.collectAsState()
    val stateErrorMessage by authSharedViewModel.stateErrorMessage.collectAsState()
    val stateCorrectMessage by authSharedViewModel.stateCorrectMessage.collectAsState()
    val stateNicknameValidate by authSharedViewModel.stateNicknameValidate.collectAsState()
    Log.e("nickname-screen", "${stateIsError}")
    Log.e("nickname-screen", "${stateIsCorrect}")
    Log.e("nickname-screen", "${stateErrorMessage}")
    Log.e("nickname-screen", "${stateCorrectMessage}")

    LaunchedEffect(
        stateIsCorrect
    ){
        authSharedViewModel.updateNicknameValidation(stateIsCorrect)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp, 10.dp, 16.dp, 0.dp)
                    .background(Color.White)
                    .fillMaxSize(),
            ){
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.signup_nickname_maintitle_zipdabang),
                        color = ZipdabangandroidTheme.Colors.Typo,
                        style= ZipdabangandroidTheme.Typography.twentysix_700
                    )
                    Text(
                        text = stringResource(id = R.string.signup_nickname_maintitle_zipdabangback),
                        color = ZipdabangandroidTheme.Colors.Typo,
                        style= ZipdabangandroidTheme.Typography.twentysix_500
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.signup_nickname_maintitle_nickname),
                        color = ZipdabangandroidTheme.Colors.Typo,
                        style= ZipdabangandroidTheme.Typography.twentysix_700
                    )
                    Text(
                        text = stringResource(id = R.string.signup_nickname_maintitle_nicknameback),
                        color = ZipdabangandroidTheme.Colors.Typo,
                        style= ZipdabangandroidTheme.Typography.twentysix_500
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(0.dp, 20.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Box(
                        modifier = Modifier.weight(6.8f),
                    ){
                        TextFieldErrorAndCorrect(
                            value = stateNicknameValue,
                            onValueChanged = {
                                authSharedViewModel.updateNickname(it)
                            },
                            tryCount = stateTrycount,
                            labelValue = stringResource(id = R.string.signup_nickname),
                            placeHolderValue = stringResource(id = R.string.signup_nickname_placeholder),
                            isError = stateIsError,
                            isCorrect = stateIsCorrect,
                            onError = {
                                authSharedViewModel.updateIsError()
                            },
                            onCorrect = {
                                authSharedViewModel.updateIsCorrect()
                            },
                            errorMessage = stateErrorMessage,
                            correctMessage = stateCorrectMessage,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(3.2f)
                            .wrapContentSize(),
                    ){
                        PrimaryButtonOutLined(
                            borderColor = ZipdabangandroidTheme.Colors.BlackSesame,
                            text= stringResource(id = R.string.signup_nickname_deplicatecheck),
                            onClick= {
                                authSharedViewModel.updateTrycount()
                            }
                        )
                    }
                }
            }

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.padding(16.dp,0.dp,16.dp, 12.dp)
            ){
                PrimaryButtonWithStatus(
                    text= stringResource(id = R.string.signup_btn_inputdone),
                    onClick={ onClickNext() },
                    isFormFilled = stateNicknameValidate
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewRegisterNicknameScreen(){
    val navController = rememberNavController()
    RegisterNicknameScreen(
        navController = navController,
        onClickBack = {
            navController.navigate(AuthScreen.RegisterUserInfo.route)
        },
        onClickNext = {
            navController.navigate(AuthScreen.RegisterPreferences.route)
        }
    )
}