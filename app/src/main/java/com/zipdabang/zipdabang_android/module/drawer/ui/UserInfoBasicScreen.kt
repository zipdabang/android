package com.zipdabang.zipdabang_android.module.drawer.ui

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
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.DrawerUserInfoViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.MainAndSubTitle
import com.zipdabang.zipdabang_android.ui.component.PrimaryButton
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonOutLined
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.RadioGroupHorizontal
import com.zipdabang.zipdabang_android.ui.component.TextFieldError
import com.zipdabang.zipdabang_android.ui.component.TextFieldErrorAndCorrect
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun UserInfoBasicScreen(
    drawerUserInfoViewModel : DrawerUserInfoViewModel = hiltViewModel(),
    onClickBack : ()->Unit,
    onClickCancel : ()->Unit,
    onClickEdit : ()->Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = { onClickBack() },
                centerText = stringResource(id = R.string.my_myinfo_edit)
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
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MainAndSubTitle(
                    mainValue = stringResource(id = R.string.signup_userinfo_basicinfo),
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
                                .size(16.dp)
                                .weight(1.4f),
                        )
                        Box(
                            modifier = Modifier.weight(8.6f)
                        ){
                            TextFieldError(
                                value = "",
                                onValueChanged = {
                                    //authSharedViewModel.onUserInfoEvent(UserInfoFormEvent.NameChanged(it))
                                },
                                labelValue = stringResource(id = R.string.signup_userinfo_name),
                                placeHolderValue ="",
                                isError = false ,
                                errorMessage = "error",
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
                                .size(16.dp)
                                .weight(1.4f),
                        )
                        Box(
                            modifier = Modifier.weight(5.6f)
                        ){
                            TextFieldErrorAndCorrect(
                                value = "",
                                onValueChanged = {
                                    //authSharedViewModel.onUserInfoEvent(UserInfoFormEvent.BirthdayChanged(it))
                                },
                                labelValue = stringResource(id = R.string.signup_userinfo_birthday),
                                placeHolderValue = stringResource(id = R.string.signup_userinfo_birthday_placeholder),
                                isTried = true,//stateUserInfoForm.birthdayIsTried,
                                isError = true,//stateUserInfoForm.birthdayIsError,
                                isCorrect = false,
                                correctMessage = "",
                                errorMessage = "error",//stateUserInfoForm.birthdayErrorMessage,
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next,
                            )
                        }

                        Box(
                            modifier = Modifier.weight(3.4f)
                        ){
                            RadioGroupHorizontal(
                                optionList = listOf("남", "여"),//genderList,
                                onOptionChange = {
                                    //authSharedViewModel.onUserInfoEvent(UserInfoFormEvent.GenderChanged(it))
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
                                .size(16.dp)
                                .weight(1.4f),
                        )
                        Box(
                            modifier = Modifier.weight(5.2f)
                        ){
                            TextFieldErrorAndCorrect(
                                value = "",//stateUserInfoForm.phoneNumber,
                                onValueChanged = {
                                    //authSharedViewModel.onUserInfoEvent(UserInfoFormEvent.PhoneNumberChanged(it))
                                },
                                labelValue = stringResource(id = R.string.signup_userinfo_phonenumber),
                                placeHolderValue = stringResource(id = R.string.signup_userinfo_phonenumber_placeholder),
                                isTried = true,//stateUserInfoForm.phoneNumberIsTried,
                                isError = true,//stateUserInfoForm.phoneNumberIsError,
                                isCorrect = false,//stateUserInfoForm.phoneNumberIsCorrect,
                                errorMessage = "error",//stateUserInfoForm.phoneNumberErrorMessage,
                                correctMessage = "correct",//stateUserInfoForm.phoneNumberCorrectMessage,
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                            )
                        }
                        Box(modifier = Modifier
                            .weight(3.4f)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)){
                            PrimaryButtonOutLined(
                                borderColor = ZipdabangandroidTheme.Colors.BlackSesame,
                                text = stringResource(id = R.string.signup_userinfo_certificatecall),
                                onClick = {
                                    //authSharedViewModel.onUserInfoEvent(UserInfoFormEvent.PhoneNumberClicked(true))
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
                                value = "인증번호",//stateUserInfoForm.authNumber,
                                onValueChanged = {
                                    //authSharedViewModel.onUserInfoEvent(UserInfoFormEvent.AuthNumberChanged(it))
                                },
                                isTried = true,//stateUserInfoForm.authNumberIsTried,
                                labelValue = stringResource(id = R.string.signup_userinfo_certificatenumber),
                                placeHolderValue = "",
                                isError = true,//stateUserInfoForm.authNumberIsError,
                                isCorrect = false,//stateUserInfoForm.authNumberIsCorrect,
                                errorMessage = "에러임",//stateUserInfoForm.authNumberErrorMessage,
                                correctMessage = "마즘",//stateUserInfoForm.authNumberCorrectMessage,
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                            )
                        }
                        Text(
                            modifier = Modifier.weight(1.4f),
                            text = "00:00", //formatTime(authSharedViewModel.remainingTime),
                            color = Color(0xFFB00020),
                            style = ZipdabangandroidTheme.Typography.fourteen_300,
                            textAlign = TextAlign.Center
                        )
                        Box(modifier = Modifier
                            .weight(3.4f)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)){
                            PrimaryButtonOutLined(
                                borderColor = ZipdabangandroidTheme.Colors.BlackSesame,
                                text = stringResource(id = R.string.signup_userinfo_ok),
                                onClick = {
                                    //authSharedViewModel.onUserInfoEvent(UserInfoFormEvent.AuthNumberClicked(true))
                                }
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.padding(16.dp,0.dp,16.dp, 12.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Box(
                    modifier = Modifier.weight(1f)
                ){
                    PrimaryButtonOutLined(
                        borderColor = ZipdabangandroidTheme.Colors.Strawberry,
                        text= stringResource(id = R.string.drawer_cancel),
                        onClick={
                            onClickCancel()
                        },
                    )
                }
                Box(
                    modifier = Modifier.weight(1f)
                ){
                    PrimaryButton(
                        backgroundColor = ZipdabangandroidTheme.Colors.Strawberry,
                        text= stringResource(id = R.string.drawer_editdone),
                        onClick={
                            onClickEdit()
                        },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewUserInfoBasicScreen() {
    UserInfoBasicScreen(
        onClickBack = {},
        onClickCancel = {},
        onClickEdit = {}
    )
}