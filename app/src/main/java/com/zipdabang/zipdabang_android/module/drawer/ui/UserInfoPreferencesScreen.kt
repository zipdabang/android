package com.zipdabang.zipdabang_android.module.drawer.ui

import android.content.Context
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.drawer.ui.state.userinfo.UserInfoPreferencesEvent
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.DrawerUserInfoViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.MainAndSubTitle
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonForSignup
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonOutLined
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatusForSignup
import com.zipdabang.zipdabang_android.ui.component.RoundedButton
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun UserInfoPreferencesScreen(
    drawerUserInfoViewModel : DrawerUserInfoViewModel = hiltViewModel(),
    onClickBack : ()->Unit,
    onClickCancel : ()->Unit,
    onClickEdit : ()->Unit
) {
    val stateUserInfoPreferences = drawerUserInfoViewModel.stateUserInfoPreferences
    val stateUserInfo = drawerUserInfoViewModel.stateUserInfo

    LaunchedEffect(key1 = stateUserInfoPreferences.preferBeverageCheckList){
        drawerUserInfoViewModel.onUserInfoPreferencesEvent(UserInfoPreferencesEvent.BtnEnabled(true))
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = { onClickBack() },
                centerText = stringResource(id = R.string.drawer_edit_preferbeverage)
            )
        },
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
                horizontalAlignment = Alignment.CenterHorizontally,
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
                            mainValue = stringResource(id = R.string.drawer_pick_prefer_beverage),
                            mainTextStyle = ZipdabangandroidTheme.Typography.twentytwo_700,
                            mainTextColor = ZipdabangandroidTheme.Colors.Typo,
                            subValue = stringResource(id = R.string.drawer_pick_prefer_beverage_detail),
                            subTextStyle = ZipdabangandroidTheme.Typography.sixteen_300,
                            subTextColor =  ZipdabangandroidTheme.Colors.Typo
                        )

                        val chunkedBeverageList = stateUserInfoPreferences.preferBeverageList.chunked(3)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 20.dp, 0.dp, 0.dp)
                                .background(color = Color.Transparent),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
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
                                            shimmering = if(stateUserInfoPreferences.isLoading || stateUserInfoPreferences.error.isNotBlank()) true
                                                        else false,
                                            isClicked = stateUserInfoPreferences.preferBeverageCheckList[preference.id-1],
                                            isClickedChange = { selectedClicked ->
                                                drawerUserInfoViewModel.onUserInfoPreferencesEvent(
                                                    UserInfoPreferencesEvent.BeverageCheckListChanged(preference.id-1 ,selectedClicked))
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 하단 버튼
            Row(
                modifier = Modifier.padding(16.dp,0.dp,16.dp, 12.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Box(
                    modifier = Modifier.weight(1f)
                ){
                    PrimaryButtonForSignup(
                        text= stringResource(id = R.string.drawer_cancel),
                        onClick={
                            onClickCancel()
                        },
                    )
                }
                Box(
                    modifier = Modifier.weight(1f)
                ){
                    PrimaryButtonWithStatusForSignup(
                        isFormFilled = stateUserInfoPreferences.btnEnabled,
                        text= stringResource(if(stateUserInfo.preferBeverageList.size == 0) R.string.drawer_choosedone else R.string.drawer_editdone),
                        onClick={
                            if(stateUserInfoPreferences.btnEnabled){
                                CoroutineScope(Dispatchers.Main).launch{
                                    drawerUserInfoViewModel.patchUserPreferences()
                                    onClickEdit()
                                }
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
fun PreviewUserInfoPreferencesScreen() {
    
}