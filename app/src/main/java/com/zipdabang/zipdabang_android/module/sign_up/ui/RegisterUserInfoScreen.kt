package com.zipdabang.zipdabang_android.module.sign_up.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.navigation.AuthScreen
import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.AuthSharedViewModel
import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.UserInfoFormEvent
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.MainAndSubTitle
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonOutLined
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.RadioGroupHorizontal
import com.zipdabang.zipdabang_android.ui.component.TextFieldError
import com.zipdabang.zipdabang_android.ui.component.TextFieldErrorAndCorrect
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach

@Composable
fun RegisterUserInfoScreen(
    navController: NavHostController,
    authSharedViewModel: AuthSharedViewModel = hiltViewModel(),
    onClickBack: ()->Unit,
    onClickNext: ()->Unit,
) {
    val stateUserInfoForm = authSharedViewModel.stateUserInfoForm
    val genderList = authSharedViewModel.genderList

    LaunchedEffect(authSharedViewModel.remainingTime) {
        val timer = (authSharedViewModel.remainingTime downTo 0).asFlow()
            .onEach { delay(1000) } // 1초 지연
            .collect { newTime ->
                authSharedViewModel.remainingTime = newTime
            }
    }
    LaunchedEffect(key1 = stateUserInfoForm){
        authSharedViewModel.onUserInfoEvent(UserInfoFormEvent.BtnChanged(true))
    }


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
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    Text(
                        text= stringResource(id = R.string.signup_userinfo_basicinfo),
                        style = ZipdabangandroidTheme.Typography.sixteen_300_cafe24,
                        color = ZipdabangandroidTheme.Colors.Choco
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_signup_name),
                            contentDescription = "Icon",
                            tint = ZipdabangandroidTheme.Colors.Choco,
                            modifier = Modifier
                                .size(24.dp)
                                .weight(1.4f),
                        )
                        Box(
                            modifier = Modifier.weight(8.6f)
                        ){
                            TextFieldError(
                                value = stateUserInfoForm.name,
                                onValueChanged = {
                                    authSharedViewModel.onUserInfoEvent(UserInfoFormEvent.NameChanged(it))
                                },
                                labelValue = stringResource(id = R.string.signup_userinfo_name),
                                placeHolderValue ="",
                                isError = false ,
                                errorMessage = "",
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_signup_birthdaycake),
                            contentDescription = "Icon",
                            tint = ZipdabangandroidTheme.Colors.Choco,
                            modifier = Modifier
                                .size(24.dp)
                                .weight(1.4f),
                        )
                        Box(
                            modifier = Modifier.weight(5.6f)
                        ){
                            TextFieldErrorAndCorrect(
                                value = stateUserInfoForm.birthday,
                                onValueChanged = {
                                    authSharedViewModel.onUserInfoEvent(UserInfoFormEvent.BirthdayChanged(it))
                                },
                                labelValue = stringResource(id = R.string.signup_userinfo_birthday),
                                placeHolderValue = stringResource(id = R.string.signup_userinfo_birthday_placeholder),
                                isTried = stateUserInfoForm.birthdayIsTried,
                                isError = stateUserInfoForm.birthdayIsError,
                                isCorrect = false,
                                correctMessage = "",
                                errorMessage = stateUserInfoForm.birthdayErrorMessage,
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next,
                            )
                        }

                        Box(
                            modifier = Modifier.weight(3.4f)
                        ){
                            RadioGroupHorizontal(
                                selectedIndex = 0,
                                optionList = genderList,
                                onOptionChange = {
                                    authSharedViewModel.onUserInfoEvent(UserInfoFormEvent.GenderChanged(it))
                                }
                            )
                        }
                    }
                }

                //본인 인증
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    Text(
                        text= stringResource(id = R.string.signup_userinfo_validation),
                        style = ZipdabangandroidTheme.Typography.sixteen_300_cafe24,
                        color = ZipdabangandroidTheme.Colors.Choco
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_signup_phone),
                            contentDescription = "Icon",
                            tint = ZipdabangandroidTheme.Colors.Choco,
                            modifier = Modifier
                                .size(24.dp)
                                .weight(1.4f),
                        )
                        Box(
                            modifier = Modifier.weight(5.2f)
                        ){
                            TextFieldErrorAndCorrect(
                                value = stateUserInfoForm.phoneNumber,
                                onValueChanged = {
                                    authSharedViewModel.onUserInfoEvent(UserInfoFormEvent.PhoneNumberChanged(it))
                                },
                                labelValue = stringResource(id = R.string.signup_userinfo_phonenumber),
                                placeHolderValue = stringResource(id = R.string.signup_userinfo_phonenumber_placeholder),
                                isTried = stateUserInfoForm.phoneNumberIsTried,
                                isError = stateUserInfoForm.phoneNumberIsError,
                                isCorrect = stateUserInfoForm.phoneNumberIsCorrect,
                                errorMessage = stateUserInfoForm.phoneNumberErrorMessage,
                                correctMessage = stateUserInfoForm.phoneNumberCorrectMessage,
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                            )
                        }
                        Box(modifier = Modifier
                            .weight(3.4f)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)){
                            PrimaryButtonOutLined(
                                borderColor =ZipdabangandroidTheme.Colors.BlackSesame,
                                text = stringResource(id = R.string.signup_userinfo_certificatecall),
                                onClick = {
                                    authSharedViewModel.onUserInfoEvent(UserInfoFormEvent.PhoneNumberClicked(true))
                                }
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Spacer(
                            modifier = Modifier.weight(1.4f)
                        )
                        Box(
                            modifier = Modifier.weight(3.8f)
                        ){
                            TextFieldErrorAndCorrect(
                                value = stateUserInfoForm.authNumber,
                                onValueChanged = {
                                    authSharedViewModel.onUserInfoEvent(UserInfoFormEvent.AuthNumberChanged(it))
                                },
                                isTried = stateUserInfoForm.authNumberIsTried,
                                labelValue = stringResource(id = R.string.signup_userinfo_certificatenumber),
                                placeHolderValue = "",
                                isError = stateUserInfoForm.authNumberIsError,
                                isCorrect = stateUserInfoForm.authNumberIsCorrect,
                                errorMessage = stateUserInfoForm.authNumberErrorMessage,
                                correctMessage = stateUserInfoForm.authNumberCorrectMessage,
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                            )
                        }
                        Text(
                            modifier = Modifier.weight(1.4f),
                            text = formatTime(authSharedViewModel.remainingTime),
                            color = Color(0xFFB00020),
                            style = ZipdabangandroidTheme.Typography.fourteen_300,
                            textAlign = TextAlign.Center
                        )
                        Box(modifier = Modifier
                            .weight(3.4f)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)){
                            PrimaryButtonOutLined(
                                borderColor =ZipdabangandroidTheme.Colors.BlackSesame,
                                text = stringResource(id = R.string.signup_userinfo_ok),
                                onClick = {
                                    authSharedViewModel.onUserInfoEvent(UserInfoFormEvent.AuthNumberClicked(true))
                                }
                            )
                        }
                    }
                }
            }

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.padding(16.dp,0.dp,16.dp, 12.dp)
            ){
                PrimaryButtonWithStatus(
                    text= stringResource(id = R.string.signup_btn_inputdone),
                    onClick={
                        authSharedViewModel.updateValidateBirthday()

                        if(authSharedViewModel.updateValidateUserInfo()){
                            onClickNext()
                        } else {
                            authSharedViewModel.onUserInfoEvent(UserInfoFormEvent.ValidateChanged(true))
                        }
                    },
                    isFormFilled = stateUserInfoForm.btnEnabled
                )
            }
        }
    }
}

fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
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