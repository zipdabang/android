package com.zipdabang.zipdabang_android.module.drawer.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.DrawerUserInfoViewModel
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.userinfo.UserInfoNicknameEvent
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonOutLined
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.TextFieldErrorAndCorrectIcon
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun UserInfoNicknameScreen(
    drawerUserInfoViewModel : DrawerUserInfoViewModel = hiltViewModel(),
    onClickBack : ()->Unit,
    onClickCancel : ()->Unit,
    onClickEdit : ()->Unit
) {
    val stateUserInfoNickname = drawerUserInfoViewModel.stateUserInfoNickname

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp, 10.dp, 16.dp, 0.dp)
                    .background(Color.White)
                    .fillMaxSize(),
            ){
                Text(
                    text= stringResource(id = R.string.signup_nickname),
                    style = ZipdabangandroidTheme.Typography.twentytwo_700,
                    color = ZipdabangandroidTheme.Colors.Typo
                )

                Row(
                    modifier = Modifier
                        .padding(0.dp, 10.dp, 0.dp, 0.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Box(
                        modifier = Modifier.weight(6.8f),
                    ){
                        TextFieldErrorAndCorrectIcon(
                            value = stateUserInfoNickname.nickname,
                            onValueChanged = {
                                drawerUserInfoViewModel.onUserInfoNicknameEvent(UserInfoNicknameEvent.NicknameChanged(it))
                            },
                            isTried = stateUserInfoNickname.isTried,
                            labelValue = stringResource(id = R.string.signup_nickname),
                            placeHolderValue = stringResource(id = R.string.signup_nickname_placeholder),
                            isError = stateUserInfoNickname.isError,
                            isCorrect = stateUserInfoNickname.isSuccess,
                            errorMessage = stateUserInfoNickname.errorMessage,
                            correctMessage = stateUserInfoNickname.successMessage,
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
                                drawerUserInfoViewModel.onUserInfoNicknameEvent(UserInfoNicknameEvent.NicknameClicked(true))
                            }
                        )
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
                        isFormFilled = stateUserInfoNickname.btnEnabled,
                        text= stringResource(id = R.string.drawer_editdone),
                        onClick={
                            if(stateUserInfoNickname.isSuccess){
                                CoroutineScope(Dispatchers.Main).launch{
                                    drawerUserInfoViewModel.patchUserInfoNickname()
                                    onClickEdit()
                                }
                            } else {
                                drawerUserInfoViewModel.onUserInfoNicknameEvent(UserInfoNicknameEvent.BtnEnabled(true))
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
fun PreviewUserInfoNicknameScreen() {
    UserInfoNicknameScreen(
        onClickBack={},
        onClickCancel = {},
        onClickEdit = {}
    )
}