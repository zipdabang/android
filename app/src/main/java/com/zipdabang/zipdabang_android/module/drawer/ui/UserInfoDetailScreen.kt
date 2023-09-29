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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoBasicEvent
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.DrawerUserInfoViewModel
import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoDetailEvent
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.MainAndSubTitle
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonOutLined
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.TextFieldError
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun UserInfoDetailScreen(
    drawerUserInfoViewModel : DrawerUserInfoViewModel = hiltViewModel(),
    onClickBack : ()->Unit,
    onClickCancel : ()->Unit,
    onClickEdit : ()->Unit
) {
    val stateUserInfoDetail = drawerUserInfoViewModel.stateUserInfoDetail

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = { onClickBack() },
                centerText = stringResource(id = R.string.drawer_edit_userinfodetail)
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
                    mainValue = stringResource(id = R.string.signup_userinfo_detailinfo),
                    mainTextStyle = ZipdabangandroidTheme.Typography.twentytwo_700,
                    mainTextColor = ZipdabangandroidTheme.Colors.Typo,
                    subValue = stringResource(id = R.string.signup_userinfo_subtitle),
                    subTextStyle = ZipdabangandroidTheme.Typography.sixteen_300,
                    subTextColor = ZipdabangandroidTheme.Colors.Typo
                )

                //상세 정보
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
                            painter = painterResource(id = R.drawable.ic_signup_zipcode),
                            contentDescription = "Icon",
                            tint = ZipdabangandroidTheme.Colors.Typo,
                            modifier = Modifier
                                .size(24.dp)
                                .weight(1.4f),
                        )
                        Box(
                            modifier = Modifier.weight(5.2f)
                        ){
                            TextFieldError(
                                value = stateUserInfoDetail.zipCode,
                                maxLength = 5,
                                onValueChanged = { newText, maxLength ->
                                    if (newText.length <= maxLength) {
                                        drawerUserInfoViewModel.onUserInfoDetailEvent(
                                            UserInfoDetailEvent.ZipcodeChanged(newText))
                                    }
                                },
                                labelValue = stringResource(id = R.string.signup_userinfo_zipcode),
                                placeHolderValue = "",
                                isError = stateUserInfoDetail.zipCodeIsError,
                                errorMessage = stateUserInfoDetail.zipCodeErrorMessage,
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                            )
                        }
                        Box(modifier = Modifier
                            .weight(3.4f)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)){
                            PrimaryButtonOutLined(
                                borderColor = ZipdabangandroidTheme.Colors.BlackSesame,
                                text = stringResource(id = R.string.signup_userinfo_addresssearch),
                                onClick = {
                                    drawerUserInfoViewModel.onUserInfoDetailEvent(
                                        UserInfoDetailEvent.ZipcodeClicked(true))
                                    //webView
                                }
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_signup_address),
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
                                value = stateUserInfoDetail.address,
                                maxLength = 50,
                                onValueChanged = { newText, maxLength ->
                                    if (newText.length <= maxLength) {
                                        drawerUserInfoViewModel.onUserInfoDetailEvent(
                                            UserInfoDetailEvent.AddressChanged(newText))
                                    }
                                },
                                labelValue = stringResource(id = R.string.signup_userinfo_address),
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
                        Spacer(
                            modifier = Modifier.weight(1.4f)
                        )
                        Box(
                            modifier = Modifier.weight(8.6f)
                        ){
                            TextFieldError(
                                value = stateUserInfoDetail.detailAddress,
                                maxLength = 50,
                                onValueChanged = { newText, maxLength ->
                                    if (newText.length <= maxLength) {
                                        drawerUserInfoViewModel.onUserInfoDetailEvent(
                                            UserInfoDetailEvent.DetailaddressChanged(newText))
                                    }
                                },
                                labelValue = stringResource(id = R.string.signup_userinfo_detailaddress),
                                placeHolderValue = stringResource(id = R.string.signup_userinfo_detailaddress_placeholder),
                                isError = false ,
                                errorMessage = "",
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done,
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
                        isFormFilled = stateUserInfoDetail.btnEnabled,
                        text= stringResource(id = R.string.drawer_editdone),
                        onClick={
                            if(true){
                                CoroutineScope(Dispatchers.Main).launch{
                                    drawerUserInfoViewModel.patchUserInfoDetail()
                                    onClickEdit()
                                }
                            } else {
                                //drawerUserInfoViewModel.onUserInfoNicknameEvent(UserInfoNicknameEvent.BtnClicked(true))
                            }
                        },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewUserInfoDetailScreen() {
    UserInfoDetailScreen(
        onClickBack = {},
        onClickCancel = {},
        onClickEdit = {}
    )
}