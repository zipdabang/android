package com.zipdabang.zipdabang_android.module.main

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.bottom.BottomMenuContent
import com.zipdabang.zipdabang_android.module.bottom.ui.BottomNavigationBar
import com.zipdabang.zipdabang_android.module.bottom.ui.Navigation
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.DrawerContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ZipdabangApp(){
    var isBottomNavigationSelected = remember { mutableStateOf<BottomMenuContent>(BottomMenuContent.home) }

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                        noticeOnClick = { Log.d("drawer","공지사항")},
                        ToSOnClick = { Log.d("drawer","서비스 이용 약관")},
                        privacyAgreeOnClick = { Log.d("drawer","개인정보 제 3자 동의")},
                        privacyOnClick = { Log.d("drawer","개인정보 처리방침")},
                        FAGOnClick = { Log.d("drawer","FAG")},
                        inquiryOnClick = { Log.d("drawer","오류신고 및 문의하기")},
                        loginOnClick = { Log.d("drawer","로그인하기")},
                        userOnClick = { Log.d("drawer","회원 정보")},
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
                    Scaffold( //material3 Scaffold를 이용함
                        topBar = {
                            AppBarHome( //AppBar 문제 논의!!!
                                endIcon1 = Icons.Default.Search, //아이콘 바꾸기???
                                endIcon2 = Icons.Default.Menu,
                                onClickEndIcon1 = {},
                                onClickEndIcon2 = { scope.launch { drawerState.open() } },
                                centerText = "집다방"
                            )
                        },
                        bottomBar = {
                            BottomNavigationBar(
                                navController = navController,
                                onItemClick = { navController.navigate(it.route) },
                                onItemSelected = { isSelected -> isBottomNavigationSelected.value = isSelected }
                            )
                        },
                        snackbarHost={ /*밑에 알람 뜨는거 여기서 커스텀 가능함*/},
                        containerColor = Color.White,
                        contentColor = Color.Black,
                        content = {
                            Box(Modifier.padding(it)) {
                                Navigation(navController = navController)
                            }
                        }
                    )
                }
            }
        )
    }
}


@Preview
@Composable
fun PreviewBottomNav(){
    ZipdabangApp()
}