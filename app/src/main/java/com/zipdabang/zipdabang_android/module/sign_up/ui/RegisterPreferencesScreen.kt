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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
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
import com.zipdabang.zipdabang_android.ui.component.RoundedButton
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RegisterPreferencesScreen(
    navController: NavHostController,
    authSharedViewModel: AuthSharedViewModel = hiltViewModel(), //FakeAuthSharedViewModel = provideFakeAuthSharedViewModel(),
    onClickBack : ()->Unit,
    onClickNext: ()->Unit,
) {
    val state = authSharedViewModel.statePreferences.value
    val stateBeverageList by authSharedViewModel.stateBeverageList.collectAsState()
    val statePreferencesValidate by authSharedViewModel.statePreferencesValidate.collectAsState()
    //Log.e("preferences-screen", "${stateBeverageList}")

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
            color = ZipdabangandroidTheme.Colors.SubBackground
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White),
                    contentAlignment = Alignment.TopCenter
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp, 10.dp, 16.dp, 20.dp)
                            .background(Color.White)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ){
                        MainAndSubTitle(
                            mainValue = stringResource(id = R.string.signup_preferences_maintitle),
                            mainTextStyle = ZipdabangandroidTheme.Typography.twentytwo_700,
                            mainTextColor = ZipdabangandroidTheme.Colors.Typo,
                            subValue = stringResource(id = R.string.signup_preferences_subtitle),
                            subTextStyle = ZipdabangandroidTheme.Typography.sixteen_300,
                            subTextColor =  ZipdabangandroidTheme.Colors.Typo
                        )

                        val chunkedBeverageList = state.beverageList.chunked(3)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 20.dp, 0.dp, 0.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            var index = 0
                            for (chunk in chunkedBeverageList) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    for (preference in chunk) {
                                        RoundedButton(
                                            imageUrl = preference.imageUrl,
                                            buttonText = preference.categoryName,
                                            isClicked = stateBeverageList[index],
                                            isClickedChange = { selectedClicked ->
                                                authSharedViewModel.updateBeverageList(preference.id-1 , selectedClicked)
                                            }
                                        )
                                        index ++
                                    }
                                }
                            }
                            if (state.error.isNotBlank()) {
                                Text(
                                    text = state.error,
                                    color = Color.Red,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            if (state.isLoading) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(ZipdabangandroidTheme.Colors.SubBackground)
                        .padding(0.dp, 40.dp, 0.dp, 0.dp),
                    contentAlignment = Alignment.Center
                ){
                    ClickableText(
                        text= AnnotatedString(
                            text= stringResource(id = R.string.signup_preferences_inputlater),
                            spanStyle = SpanStyle(
                                color = ZipdabangandroidTheme.Colors.Typo
                            ),
                        ) + AnnotatedString(
                                text = " ",
                        ) + AnnotatedString(
                            text=stringResource(id = R.string.signup_preferences_gotohome),
                            spanStyle = SpanStyle(
                                color = ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f)
                            ),
                        ),
                        style = ZipdabangandroidTheme.Typography.fourteen_300,
                        onClick={  },
                    )
                }
            }

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.padding(16.dp,0.dp,16.dp, 12.dp)
            ){
                PrimaryButtonWithStatus(
                    text= stringResource(id = R.string.signup_btn_choicecomplete),
                    onClick={ onClickNext() },
                    isFormFilled = statePreferencesValidate
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewRegisterPreferencesScreen(){
    val navController = rememberNavController()
    RegisterPreferencesScreen(
        navController = navController,
        onClickBack = {
            navController.navigate(AuthScreen.RegisterNickname.route)
        },
        onClickNext = {
            navController.navigate(AuthScreen.RegisterPreferences.route)
        }
    )
}