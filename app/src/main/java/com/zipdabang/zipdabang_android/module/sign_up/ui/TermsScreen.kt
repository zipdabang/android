package com.zipdabang.zipdabang_android.module.sign_up.ui

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.checkAndRequestPermissions
import com.zipdabang.zipdabang_android.core.navigation.AuthScreen
import com.zipdabang.zipdabang_android.module.sign_up.ui.component.CheckBoxWithText
import com.zipdabang.zipdabang_android.module.sign_up.ui.component.CheckBoxWithTextAndButton
import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.AuthSharedViewModel
import com.zipdabang.zipdabang_android.module.sign_up.ui.state.TermsFormEvent
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.CustomSignupPermission
import com.zipdabang.zipdabang_android.ui.component.MainAndSubTitle
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Composable
fun TermsScreen(
    navController: NavHostController,
    authSharedViewModel: AuthSharedViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
    onClickNext: () -> Unit,
    onClickDetailNext: (Int) -> Unit,
) {
    val stateTermsForm = authSharedViewModel.stateTermsForm

    LaunchedEffect(key1 = stateTermsForm) {
        authSharedViewModel.onTermsEvent(TermsFormEvent.BtnChanged(true))
        //Log.e("terms-screen", "${stateTermsForm}")
    }

    // 권한 체크 횟수
    var permissionCount : Int = 0
    // context 정의
    val context = LocalContext.current
    // 요청할 권한들에 대한 배열
    val permissions = arrayOf(
        Manifest.permission.CAMERA, // 카메라
        Manifest.permission.READ_MEDIA_IMAGES, // 갤러리
        Manifest.permission.POST_NOTIFICATIONS, // 알람
    )
    // 권한이 없을 경우 실행할 launcher 정의
    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            Log.d("권한", "권한이 동의되었습니다.")
        } else {
            Log.d("권한", "권한이 거부되었습니다.")
        }
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
                    .fillMaxSize(),
            ) {
                MainAndSubTitle(
                    mainValue = stringResource(id = R.string.signup_terms_maintitle),
                    mainTextStyle = ZipdabangandroidTheme.Typography.twentytwo_700,
                    mainTextColor = ZipdabangandroidTheme.Colors.Typo,
                    subValue = stringResource(id = R.string.signup_terms_subtitle),
                    subTextStyle = ZipdabangandroidTheme.Typography.sixteen_300,
                    subTextColor = ZipdabangandroidTheme.Colors.Typo
                )

                Spacer(modifier = Modifier.height(40.dp))

                CheckBoxWithText(
                    isCheckBox = true,
                    isChecked = stateTermsForm.allAgree, //stateTermsAllagree,
                    isCheckedChange = {
                        authSharedViewModel.onTermsEvent(TermsFormEvent.AllAgreeChanged(it))
                    },
                    shimmering = false,
                    mainValue = stringResource(id = R.string.signup_terms_allagree),
                    mainTextStyle = ZipdabangandroidTheme.Typography.sixteen_700,
                    isDetailValue = true,
                    detailValue = stringResource(id = R.string.signup_terms_allagreedetail),
                    detailTextStyle = ZipdabangandroidTheme.Typography.twelve_300
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp, horizontal = 0.dp),
                    color = Color(0xFFCDC6C3)
                )

                CheckBoxWithText(
                    isCheckBox = false,
                    isChecked = null,
                    isCheckedChange = {},
                    shimmering = false,
                    mainValue = stringResource(id = R.string.signup_terms_zipdabangserviceagree),
                    mainTextStyle = ZipdabangandroidTheme.Typography.sixteen_700,
                    isDetailValue = false,
                    detailValue = null,
                    detailTextStyle = null
                )

                Column(
                    modifier = Modifier.padding(0.dp, 12.dp, 0.dp, 0.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    CheckBoxWithText(
                        isCheckBox = true,
                        isChecked = stateTermsForm.requiredOne,
                        isCheckedChange = {
                            authSharedViewModel.onTermsEvent(TermsFormEvent.RequiredOneChanged(it))
                        },
                        shimmering = if (stateTermsForm.isLoading || stateTermsForm.error.isNotBlank()) {
                            true
                        } else {
                            false
                        },
                        mainValue = stateTermsForm.requiredOneTitle,
                        mainTextStyle = ZipdabangandroidTheme.Typography.fourteen_500,
                        isDetailValue = true,
                        detailValue = stateTermsForm.requiredOneBody,
                        detailTextStyle = ZipdabangandroidTheme.Typography.twelve_300
                    )

                    CheckBoxWithTextAndButton(
                        isChecked = stateTermsForm.requiredTwo,
                        isCheckedChange = {
                            authSharedViewModel.onTermsEvent(TermsFormEvent.RequiredTwoChanged(it))
                        },
                        shimmering = if (stateTermsForm.isLoading || stateTermsForm.error.isNotBlank()) {
                            true
                        } else {
                            false
                        },
                        mainValue = stateTermsForm.requiredTwoTitle,
                        mainTextStyle = ZipdabangandroidTheme.Typography.fourteen_500,
                        onClick = {
                            onClickDetailNext(1)
                        }
                    )

                    CheckBoxWithTextAndButton(
                        isChecked = stateTermsForm.requiredThree,
                        isCheckedChange = {
                            authSharedViewModel.onTermsEvent(TermsFormEvent.RequiredThreeChanged(it))
                        },
                        shimmering = if (stateTermsForm.isLoading || stateTermsForm.error.isNotBlank()) {
                            true
                        } else {
                            false
                        },
                        mainValue = stateTermsForm.requiredThreeTitle,
                        mainTextStyle = ZipdabangandroidTheme.Typography.fourteen_500,
                        onClick = {
                            onClickDetailNext(2)
                        }
                    )

                    CheckBoxWithTextAndButton(
                        isChecked = stateTermsForm.requiredFour,
                        isCheckedChange = {
                            authSharedViewModel.onTermsEvent(TermsFormEvent.RequiredFourChanged(it))
                        },
                        shimmering = if (stateTermsForm.isLoading || stateTermsForm.error.isNotBlank()) {
                            true
                        } else {
                            false
                        },
                        mainValue = stateTermsForm.requiredFourTitle,
                        mainTextStyle = ZipdabangandroidTheme.Typography.fourteen_500,
                        onClick = {
                            onClickDetailNext(3)
                        }
                    )

                    CheckBoxWithTextAndButton(
                        isChecked = stateTermsForm.choice,
                        isCheckedChange = {
                            authSharedViewModel.onTermsEvent(TermsFormEvent.ChoiceChanged(it))
                        },
                        shimmering = if (stateTermsForm.isLoading || stateTermsForm.error.isNotBlank()) {
                            true
                        } else {
                            false
                        },
                        mainValue = stateTermsForm.choiceTitle,
                        mainTextStyle = ZipdabangandroidTheme.Typography.fourteen_500,
                        onClick = {
                            onClickDetailNext(4)
                        }
                    )
                }
            }

            //하단 버튼
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 12.dp)
            ) {
                PrimaryButtonWithStatus(
                    text = stringResource(id = R.string.signup_btn_termsagree),
                    onClick = {
                        authSharedViewModel.showPermissionDialog = true
                    },
                    isFormFilled = stateTermsForm.btnEnabled
                )
            }

            if (authSharedViewModel.showPermissionDialog) {
                CustomSignupPermission(
                    setShowDialog = {
                        authSharedViewModel.showPermissionDialog = it
                    },
                    onCheckClick = {
                        if (permissionCount == 1){
                            authSharedViewModel.showPermissionDialog = false
                            onClickNext()
                        } else {
                            checkAndRequestPermissions(
                                context,
                                permissions,
                                launcherMultiplePermissions
                            )
                            permissionCount ++
                        }
                    },
                )
            }

        }
    }

}


@Preview
@Composable
fun PreviewTermsScreen() {
    val navController = rememberNavController()
    TermsScreen(
        navController = navController,
        onClickNext = {
            navController.navigate(AuthScreen.RegisterUserInfo.route)
        },
        onClickBack = { },
        onClickDetailNext = { }
    )
}