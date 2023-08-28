package com.zipdabang.zipdabang_android.module.sign_up.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.navigation.AuthScreen
import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.AuthSharedViewModel
import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.UserAddressFormEvent
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.MainAndSubTitle
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonOutLined
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.TextFieldBasic
import com.zipdabang.zipdabang_android.ui.component.TextFieldErrorAndCorrect
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RegisterUserAddressScreen(
    navController: NavHostController,
    authSharedViewModel: AuthSharedViewModel = hiltViewModel(),
    onClickBack: ()->Unit,
    onClickNext: ()->Unit,
) {
    val stateUserAddressForm = authSharedViewModel.stateUserAddressForm

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

                //상세 정보
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    Text(
                        text= stringResource(id = R.string.signup_userinfo_detailinfo),
                        style = ZipdabangandroidTheme.Typography.sixteen_300_cafe24,
                        color = ZipdabangandroidTheme.Colors.Choco
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.ic_signup_zipcode),
                            contentDescription = "Icon",
                            tint = ZipdabangandroidTheme.Colors.Choco,
                            modifier = Modifier
                                .size(12.dp)
                                .weight(1.4f),
                        )
                        Box(
                            modifier = Modifier.weight(5.2f)
                        ){
                            TextFieldBasic(
                                value = stateUserAddressForm.zipCode,
                                onValueChanged = {
                                    authSharedViewModel.onUserAddressEvent(UserAddressFormEvent.ZipcodeChanged(it))
                                },
                                //isTried = stateUserAddressForm.zipCodeIsTried,
                                labelValue = stringResource(id = R.string.signup_userinfo_zipcode),
                                placeHolderValue = "",
                                isError = stateUserAddressForm.zipCodeIsError,
                                //isCorrect = stateUserAddressForm.zipCodeIsCorrect,
                                errorMessage = stateUserAddressForm.zipCodeErrorMessage,
                                //correctMessage = stateUserAddressForm.zipCodeCorrectMessage,
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done,
                            )
                        }
                        Box(modifier = Modifier
                            .weight(3.4f)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)){
                            PrimaryButtonOutLined(
                                borderColor =ZipdabangandroidTheme.Colors.BlackSesame,
                                text = stringResource(id = R.string.signup_userinfo_addresssearch),
                                onClick = {
                                    authSharedViewModel.onUserAddressEvent(UserAddressFormEvent.ZipcodeClicked(true))
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
                            tint = ZipdabangandroidTheme.Colors.Choco,
                            modifier = Modifier
                                .size(16.dp)
                                .weight(1.4f),
                        )
                        Box(
                            modifier = Modifier.weight(8.6f)
                        ){
                            TextFieldBasic(
                                value = stateUserAddressForm.address,
                                onValueChanged = {
                                    authSharedViewModel.onUserAddressEvent(UserAddressFormEvent.AddressChanged(it))
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
                            TextFieldBasic(
                                value = stateUserAddressForm.detailAddress,
                                onValueChanged = {
                                    authSharedViewModel.onUserAddressEvent(UserAddressFormEvent.DetailaddressChanged(it))
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
fun PreviewRegisterUserAddressScreen(){
    val navController = rememberNavController()
    RegisterUserAddressScreen(
        navController = navController,
        onClickBack = {
            navController.navigate(AuthScreen.RegisterUserInfo.route)
        },
        onClickNext = {
            navController.navigate(AuthScreen.RegisterNickname.route)
        }
    )
}
