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
import androidx.compose.material.CircularProgressIndicator
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
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.DrawerUserInfoViewModel
import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoBasicEvent
import com.zipdabang.zipdabang_android.module.sign_up.ui.state.UserInfoFormEvent
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.MainAndSubTitle
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonOutLined
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.RadioGroupHorizontal
import com.zipdabang.zipdabang_android.ui.component.TextFieldError
import com.zipdabang.zipdabang_android.ui.component.TextFieldErrorAndCorrect
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@Composable
fun UserInfoBasicScreen(
    drawerUserInfoViewModel : DrawerUserInfoViewModel = hiltViewModel(),
    onClickBack : ()->Unit,
    onClickCancel : ()->Unit,
    onClickEdit : ()->Unit
) {
    val stateUserInfoBasic = drawerUserInfoViewModel.stateUserInfoBasic
    val genderList = drawerUserInfoViewModel.genderList

    LaunchedEffect(drawerUserInfoViewModel.remainingTime) {
        val timer = (drawerUserInfoViewModel.remainingTime downTo 0).asFlow()
            .onEach { delay(1000) } // 1초 지연
            .collect { newTime ->
                drawerUserInfoViewModel.remainingTime = newTime
            }
    }

    LaunchedEffect(stateUserInfoBasic){
        drawerUserInfoViewModel.onUserInfoBasicEvent(UserInfoBasicEvent.BtnEnabled(true))
    }


    Scaffold(
        modifier = Modifier
        .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = { onClickBack() },
                centerText = stringResource(id = R.string.drawer_edit_userinfobasic)
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
                            tint = ZipdabangandroidTheme.Colors.Typo,
                            modifier = Modifier
                                .size(24.dp)
                                .weight(1.4f),
                        )
                        Box(
                            modifier = Modifier.weight(8.6f)
                        ){
                            TextFieldError(
                                value = stateUserInfoBasic.name,
                                maxLength = 20,
                                onValueChanged = { newText, maxLength ->
                                    if (newText.length <= maxLength) {
                                        drawerUserInfoViewModel.onUserInfoBasicEvent(UserInfoBasicEvent.NameChanged(newText))

                                    }
                                },
                                labelValue = stringResource(id = R.string.signup_userinfo_name),
                                placeHolderValue = "",
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
                            tint = ZipdabangandroidTheme.Colors.Typo,
                            modifier = Modifier
                                .size(24.dp)
                                .weight(1.4f),
                        )
                        Box(
                            modifier = Modifier.weight(5.6f)
                        ){
                            TextFieldErrorAndCorrect(
                                value = stateUserInfoBasic.birthday,
                                maxLength = 6,
                                onValueChanged = { newText, maxLength ->
                                    if (newText.length <= maxLength) {
                                        drawerUserInfoViewModel.onUserInfoBasicEvent(UserInfoBasicEvent.BirthdayChanged(newText))

                                    }
                                },
                                labelValue = stringResource(id = R.string.signup_userinfo_birthday),
                                placeHolderValue = stringResource(id = R.string.signup_userinfo_birthday_placeholder),
                                isTried = stateUserInfoBasic.birthdayIsTried,
                                isError = stateUserInfoBasic.birthdayIsError,
                                isCorrect = false,
                                correctMessage = "",
                                errorMessage = stateUserInfoBasic.birthdayErrorMessage,
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next,
                            )
                        }

                        Box(
                            modifier = Modifier.weight(3.4f)
                        ){
                            RadioGroupHorizontal(
                                selectedIndex = if(stateUserInfoBasic.gender == "여") 1 else 0,
                                optionList = genderList,
                                onOptionChange = {
                                    drawerUserInfoViewModel.onUserInfoBasicEvent(UserInfoBasicEvent.GenderChanged(it))
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
                            tint = ZipdabangandroidTheme.Colors.Typo,
                            modifier = Modifier
                                .size(24.dp)
                                .weight(1.4f),
                        )
                        Box(
                            modifier = Modifier.weight(5.2f)
                        ){
                            TextFieldErrorAndCorrect(
                                value = stateUserInfoBasic.phoneNumber,
                                maxLength = 11,
                                onValueChanged = { newText, maxLength ->
                                    if (newText.length <= maxLength) {
                                        drawerUserInfoViewModel.onUserInfoBasicEvent(UserInfoBasicEvent.PhoneNumberChanged(newText))
                                    }
                                },
                                labelValue = stringResource(id = R.string.signup_userinfo_phonenumber),
                                placeHolderValue = stringResource(id = R.string.signup_userinfo_phonenumber_placeholder),
                                isTried = stateUserInfoBasic.phoneNumberIsTried,
                                isError = stateUserInfoBasic.phoneNumberIsError,
                                isCorrect = stateUserInfoBasic.phoneNumberIsCorrect,
                                errorMessage = stateUserInfoBasic.phoneNumberErrorMessage,
                                correctMessage = stateUserInfoBasic.phoneNumberCorrectMessage,
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                            )
                        }
                        Box(modifier = Modifier
                            .weight(3.4f)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)){
                            PrimaryButtonOutLined(
                                borderColor = ZipdabangandroidTheme.Colors.Typo.copy(0.2f),
                                text = stringResource(id = R.string.signup_userinfo_certificatecall),
                                onClick = {
                                    drawerUserInfoViewModel.onUserInfoBasicEvent(UserInfoBasicEvent.PhoneNumberClicked(true))
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
                                value = stateUserInfoBasic.authNumber,
                                maxLength = 6,
                                onValueChanged = { newText, maxLength ->
                                    if (newText.length <= maxLength) {
                                        drawerUserInfoViewModel.onUserInfoBasicEvent(UserInfoBasicEvent.AuthNumberChanged(newText))
                                    }
                                },
                                isTried = stateUserInfoBasic.authNumberIsTried,
                                labelValue = stringResource(id = R.string.signup_userinfo_certificatenumber),
                                placeHolderValue = "",
                                isError = stateUserInfoBasic.authNumberIsError,
                                isCorrect = stateUserInfoBasic.authNumberIsCorrect,
                                errorMessage = stateUserInfoBasic.authNumberErrorMessage,
                                correctMessage = stateUserInfoBasic.authNumberCorrectMessage,
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                            )
                        }
                        Text(
                            modifier = Modifier.weight(1.4f),
                            text = formatTime(drawerUserInfoViewModel.remainingTime),
                            color = Color(0xFFB00020),
                            style = ZipdabangandroidTheme.Typography.fourteen_300,
                            textAlign = TextAlign.Center
                        )
                        Box(modifier = Modifier
                            .weight(3.4f)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)){
                            PrimaryButtonOutLined(
                                borderColor = ZipdabangandroidTheme.Colors.Typo.copy(0.2f),
                                text = stringResource(id = R.string.signup_userinfo_ok),
                                onClick = {
                                    drawerUserInfoViewModel.onUserInfoBasicEvent(UserInfoBasicEvent.AuthNumberClicked(true))
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
                    PrimaryButtonWithStatus(
                        isFormFilled = stateUserInfoBasic.btnEnabled,
                        text= stringResource(id = R.string.drawer_editdone),
                        onClick={
                            drawerUserInfoViewModel.updateValidateBirthday()

                            if(drawerUserInfoViewModel.updateValidateUserInfoBasic()){
                                CoroutineScope(Dispatchers.Main).launch{
                                    drawerUserInfoViewModel.patchUserInfoBasic()
                                    drawerUserInfoViewModel.onUserInfoBasicEvent(UserInfoBasicEvent.AuthNumberChanged(""))
                                    onClickEdit()
                                }
                            } else {
                                drawerUserInfoViewModel.onUserInfoBasicEvent(UserInfoBasicEvent.ValidateChanged(true))
                            }
                        },
                    )
                }
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
fun PreviewUserInfoBasicScreen() {
    UserInfoBasicScreen(
        onClickBack = {},
        onClickCancel = {},
        onClickEdit = {}
    )
}