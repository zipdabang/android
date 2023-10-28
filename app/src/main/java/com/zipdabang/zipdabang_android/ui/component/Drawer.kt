package com.zipdabang.zipdabang_android.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoDataViewModel
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.core.navigation.DrawerScreen
import com.zipdabang.zipdabang_android.core.navigation.MyScreen
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun DrawerContent(
    infoOnClick :  () -> Unit,
    noticeOnClick :  () -> Unit,
    ToSOnClick :  () -> Unit,
    privacyAgreeOnClick :  () -> Unit,
    privacyOnClick :  () -> Unit,
    FAGOnClick :  () -> Unit,
    inquiryOnClick :  () -> Unit,
    inquiryListOnClick :  () -> Unit,
    userOnClick :  () -> Unit,
    alarmOnClick :  () -> Unit,
    etcOnClick :  () -> Unit,
){
    CompositionLocalProvider (
        LocalLayoutDirection provides LayoutDirection.Ltr
    ) {
        Column(
            modifier = Modifier.background(Color.White)
        ){
            //상단
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 16.dp, 16.dp, 0.dp)
                    .weight(1f),
                contentAlignment = Alignment.CenterStart,
            ){
                Text(
                    text = "더보기",
                    style = ZipdabangandroidTheme.Typography.twentytwo_700,
                    color = ZipdabangandroidTheme.Colors.Typo
                )
            }
            //항목들
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp)
                    .weight(8f)
                    .heightIn(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable(
                            onClick = { infoOnClick() }
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp,0.dp,16.dp,0.dp),
                        text = stringResource(id = R.string.drawer_info),
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo,
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable(
                            onClick = { noticeOnClick() }
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp,0.dp,16.dp,0.dp),
                        text = stringResource(id = R.string.drawer_notice),
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo,
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable(
                            onClick = { ToSOnClick() }
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp,0.dp,16.dp,0.dp),
                        text = stringResource(id = R.string.drawer_ToS),
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo,
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable(
                            onClick = { privacyAgreeOnClick() }
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp,0.dp,16.dp,0.dp),
                        text = stringResource(id = R.string.drawer_privacyAgree),
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo,
                    )
                }
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = ZipdabangandroidTheme.Colors.Typo.copy(0.2f),
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable(
                            onClick = { privacyOnClick() }
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp,0.dp,16.dp,0.dp),
                        text = stringResource(id = R.string.drawer_privacy),
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo,
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable(
                            onClick = { FAGOnClick() }
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp,0.dp,16.dp,0.dp),
                        text = stringResource(id = R.string.drawer_FAG),
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo,
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable(
                            onClick = { inquiryOnClick() }
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp,0.dp,16.dp,0.dp),
                        text = stringResource(id = R.string.drawer_inquiry),
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo,
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable(
                            onClick = { inquiryListOnClick() }
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp,0.dp,16.dp,0.dp),
                        text = stringResource(id = R.string.drawer_inquiry_list),
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo,
                    )
                }
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = ZipdabangandroidTheme.Colors.Typo.copy(0.2f),
                )
                /*Box(
                    modifier = Modifier.fillMaxWidth()
                        .weight(1f)
                        .clickable(
                            onClick={ loginOnClick() }
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp,0.dp,16.dp,0.dp),
                        text = stringResource(id = R.string.drawer_login),
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo,
                    )
                }*/
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable(
                            onClick = { userOnClick() }
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp,0.dp,16.dp,0.dp),
                        text = stringResource(id = R.string.drawer_user),
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo,
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable(
                            onClick = { etcOnClick() }
                        ),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp,0.dp,16.dp,0.dp),
                        text = stringResource(id = R.string.drawer_setting),
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo,
                    )
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f))
            }
            //하단
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF_F7F6F6))
                    .weight(2.5f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly,
            ){
                Column(

                ){
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        contentAlignment = Alignment.CenterStart
                    ){
                        Text(
                            modifier = Modifier.padding(16.dp,0.dp,16.dp,0.dp),
                            text = stringResource(id = R.string.drawer_appName),
                            style = ZipdabangandroidTheme.Typography.twelve_500,
                            color = ZipdabangandroidTheme.Colors.Typo,
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.Start
                    ){
                        Text(
                            text = stringResource(id = R.string.drawer_appGmail),
                            style = ZipdabangandroidTheme.Typography.twelve_300,
                            color = ZipdabangandroidTheme.Colors.Typo,
                            modifier = Modifier.padding(16.dp, 0.dp, 4.dp, 0.dp),
                        )
                        Text(
                            text = "|",
                            style = ZipdabangandroidTheme.Typography.twelve_300,
                            color = ZipdabangandroidTheme.Colors.Typo,
                            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 0.dp),
                        )
                        Text(
                            text = stringResource(id = R.string.drawer_appPhone),
                            style = ZipdabangandroidTheme.Typography.twelve_300,
                            color = ZipdabangandroidTheme.Colors.Typo,
                            modifier = Modifier.padding(4.dp, 0.dp, 16.dp, 0.dp),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp),
                            text = stringResource(id = R.string.drawer_appVersion),
                            style = ZipdabangandroidTheme.Typography.twelve_300,
                            color = ZipdabangandroidTheme.Colors.Typo,
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp, 0.dp, 16.dp, 0.dp),
                        text = stringResource(id = R.string.drawer_appDetail),
                        style = ZipdabangandroidTheme.Typography.twelve_300,
                        color = ZipdabangandroidTheme.Colors.Typo,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDrawerContent(){
    DrawerContent(
        infoOnClick = {Log.d("클릭", "집다방 정보")},
        noticeOnClick = {Log.d("클릭","공지사항")},
        ToSOnClick = {Log.d("클릭","서비스 이용 약관")},
        privacyAgreeOnClick = {Log.d("클릭","개인정보 제 3자 동의")},
        privacyOnClick = {Log.d("클릭","개인정보 처리방침")},
        FAGOnClick = {Log.d("클릭","FAG")},
        inquiryOnClick = {Log.d("클릭","오류신고 및 문의하기")},
        inquiryListOnClick =  {Log.d("클릭","로그인하기")},
        userOnClick = {Log.d("클릭","회원 정보")},
        alarmOnClick = {Log.d("클릭","알림 정보")},
        etcOnClick = {Log.d("클릭","기타 정보")},
    )
}


@Composable
fun ModalDrawer(
    scaffold: @Composable () -> Unit,
    drawerState: DrawerState,
    navController: NavController = rememberNavController()
){
    val tokenStoreViewModel = hiltViewModel<ProtoDataViewModel>()
    val currentPlatform = tokenStoreViewModel.tokens.collectAsState(
        initial = Token(
            null,
            null,
            null,
            CurrentPlatform.NONE,
            null,
            null
        )
    )

    //drawer가 오른쪽에서 왼쪽으로 나오게끔 하기 위함
    //ㄴ사실 이거 오른쪽에서 왼쪽으로 글을 읽는 나라들을 위한 건데, drawer 또한 오른쪽에서 왼쪽으로 나와서 설정함
    CompositionLocalProvider (
        LocalLayoutDirection provides LayoutDirection.Rtl
    ){
        //material3에서의 drawer를 이용하기 위함 -> ModalNavigationDrawer랑 ModalDrawerSheet를 이용함
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(300.dp),
                    drawerContainerColor = Color.White,
                    drawerTonalElevation = DrawerDefaults.ModalDrawerElevation,
                ) {
                    //activityContentScope(drawerState, scope)
                    DrawerContent(
                        infoOnClick = { Log.d("drawer", "집다방 정보")},
                        noticeOnClick = { navController.navigate(DrawerScreen.Notice.route) },
                        ToSOnClick = {  navController.navigate(DrawerScreen.Service.route)},
                        privacyAgreeOnClick = { Log.d("drawer","개인정보 제 3자 동의")},
                        privacyOnClick = { navController.navigate(DrawerScreen.PersonalInfo.route)},
                        FAGOnClick = { Log.d("drawer","FAG")},
                        inquiryOnClick = {
                            if (currentPlatform.value.platformStatus == CurrentPlatform.TEMP) {
                                navController.navigate(MyScreen.Home.route)
                            }
                            else navController.navigate(DrawerScreen.Report.route)},
                        inquiryListOnClick = {
                            if (currentPlatform.value.platformStatus == CurrentPlatform.TEMP) {
                                navController.navigate(MyScreen.Home.route)
                            }
                            else navController.navigate(DrawerScreen.ReportList.route)},
                        userOnClick = {
                            if (currentPlatform.value.platformStatus == CurrentPlatform.TEMP) {
                                navController.navigate(MyScreen.Home.route)
                            }
                            else navController.navigate(DrawerScreen.UserInfo.route)
                        },
                        alarmOnClick = { Log.d("drawer","알림 정보")},
                        etcOnClick = { Log.d("drawer","기타 정보")},
                    )
                }
            },
            gesturesEnabled = drawerState.currentValue == DrawerValue.Open,
            content = {
                // Scaffold에 있는 내용까지 오른쪽에서 왼쪽으로 읽히는 방향으로 바뀌면 안돼서, 다시 설정 바꿈
                CompositionLocalProvider (
                    LocalLayoutDirection provides LayoutDirection.Ltr
                ) {
                    scaffold()
                }
            }
        )
    }
}


