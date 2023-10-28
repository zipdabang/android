package com.zipdabang.zipdabang_android.module.sign_up.ui

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoDataViewModel
import com.zipdabang.zipdabang_android.core.navigation.AuthScreen
import com.zipdabang.zipdabang_android.module.sign_up.ui.viewmodel.AuthSharedViewModel
import com.zipdabang.zipdabang_android.module.sign_up.ui.state.BeverageFormEvent
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.MainAndSubTitle
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatusForSignup
import com.zipdabang.zipdabang_android.ui.component.RoundedButton
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RegisterPreferencesScreen(
    navController: NavHostController,
    authSharedViewModel: AuthSharedViewModel = hiltViewModel(),
    onClickBack : ()->Unit,
    onClickNext: ()->Unit,
) {
    val stateBeverageForm = authSharedViewModel.stateBeverageForm
    val tokenStoreViewModel = hiltViewModel<ProtoDataViewModel>()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = stateBeverageForm){
        authSharedViewModel.onBeverageEvent(BeverageFormEvent.BtnChanged(true))
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
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            color = ZipdabangandroidTheme.Colors.SubBackground
        ){
            Image(
                painter = painterResource(id = R.drawable.img_preferbeverages),
                contentDescription = null, // Accessibility에 필요하지 않은 경우
                contentScale = ContentScale.FillBounds, // 이미지를 화면에 맞게 확대/축소
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.Transparent),
                    contentAlignment = Alignment.TopCenter
                ){
                    Column(
                        modifier = Modifier
                            .padding(16.dp, 10.dp, 16.dp, 20.dp)
                            .background(Color.Transparent)
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

                        val chunkedBeverageList = stateBeverageForm.beverageList.chunked(3)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 20.dp, 0.dp, 0.dp)
                                .background(color = Color.Transparent),
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
                                            shimmering = if(stateBeverageForm.isLoading || stateBeverageForm.error.isNotBlank()){true} else{false},
                                            isClicked = stateBeverageForm.beverageCheckList[index],
                                            isClickedChange = { selectedClicked ->
                                                authSharedViewModel.onBeverageEvent(
                                                    BeverageFormEvent.BeverageCheckListChanged(preference.id-1 ,selectedClicked))
                                            }
                                        )
                                        index ++
                                    }
                                }
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.Transparent)
                        .padding(0.dp, 40.dp, 0.dp, 0.dp),
                    contentAlignment = Alignment.Center
                ){
                    ClickableText(
                        text= AnnotatedString(
                            text= stringResource(id = R.string.signup_preferences_inputlater),
                            spanStyle = SpanStyle(
                                color = Color.White
                            ),
                        ) + AnnotatedString(
                                text = " ",
                        ) + AnnotatedString(
                            text=stringResource(id = R.string.signup_preferences_gotohome),
                            spanStyle = SpanStyle(
                                color = Color.White
                            ),
                        ),
                        style = ZipdabangandroidTheme.Typography.fourteen_300,
                        onClick={
                            if(!stateBeverageForm.btnEnabled){
                                CoroutineScope(Dispatchers.Main).launch {
                                    try{
                                        val result = authSharedViewModel.postInfo(tokenStoreViewModel)

                                        if(result.success) onClickNext()
                                        else {
                                            scope.launch {
                                                snackbarHostState.showSnackbar(result.message)
                                            }
                                        }
                                    } catch (e:Exception){

                                    }
                                }
                            }
                        },
                    )
                }
            }
            //하단 버튼
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.padding(16.dp,0.dp,16.dp, 12.dp)
            ){
                PrimaryButtonWithStatusForSignup(
                    text= stringResource(id = R.string.signup_btn_choicecomplete),
                    onClick={
                        CoroutineScope(Dispatchers.Main).launch {
                            try{
                                val result = authSharedViewModel.postInfo(tokenStoreViewModel)

                                if(result.success) onClickNext()
                                else {
                                    scope.launch {
                                        snackbarHostState.showSnackbar(result.message)
                                    }
                                }

                            } catch (e:Exception){ }
                        }
                    },
                    isFormFilled = stateBeverageForm.btnEnabled
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
        },
    )
}