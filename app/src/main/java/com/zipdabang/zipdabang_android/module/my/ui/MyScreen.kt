package com.zipdabang.zipdabang_android.module.my.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoDataViewModel
import com.zipdabang.zipdabang_android.module.bottom.BottomMenuContent
import com.zipdabang.zipdabang_android.module.bottom.ui.BottomNavigationBar
import com.zipdabang.zipdabang_android.ui.component.AppBarDefault
import com.zipdabang.zipdabang_android.ui.component.AppBarHome
import com.zipdabang.zipdabang_android.ui.component.AppBarMy
import com.zipdabang.zipdabang_android.ui.component.IconAndText
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Composable
fun MyScreen(
    onClickLogout : () -> Unit,
){
    //drawer에 필요한 drawerState랑 scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    //val tokenStoreViewModel = hiltViewModel<ProtoDataViewModel>()

    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    AppBarMy(
                        endIcon = R.drawable.ic_topbar_menu,
                        onClickEndIcon = { scope.launch { drawerState.open() } },
                        centerText = "집다방"
                    )
                },
                containerColor = Color.White,
                contentColor = Color.Black,
                content = {
                    val scrollState = rememberScrollState()
                    Surface(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        ZipdabangandroidTheme.Colors.Strawberry,
                                        ZipdabangandroidTheme.Colors.Choco
                                    )
                                ),
                                shape = RectangleShape,
                                alpha = 0.4f
                            ),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            // 프로필 부분
                            Box(
                                modifier = Modifier
                                    .height(320.dp)
                                    .fillMaxWidth()
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            listOf(
                                                ZipdabangandroidTheme.Colors.Strawberry,
                                                ZipdabangandroidTheme.Colors.Choco
                                            )
                                        ),
                                        shape = RectangleShape,
                                    )
                            ){

                            }

                            //프로필 하단 부분
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                                    .background(ZipdabangandroidTheme.Colors.SubBackground)
                                    .clip(RoundedCornerShape(16.dp))
                                    .padding(16.dp, 24.dp, 16.dp, 24.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ){
                                Row(
                                    modifier = Modifier
                                        .height(80.dp)
                                        .fillMaxWidth()
                                        .background(Color.White)
                                        .clip(RoundedCornerShape(16.dp)),
                                ){
                                    Box(
                                        modifier = Modifier.weight(0.25f)
                                    ){
                                        IconAndText(
                                            iconImageVector = R.drawable.ic_my_favorite,
                                            iconColor = ZipdabangandroidTheme.Colors.Strawberry,
                                            iconModifier = Modifier.size(30.dp, 26.dp),
                                            text = "좋아요",
                                            textColor = ZipdabangandroidTheme.Colors.Typo,
                                            textStyle = ZipdabangandroidTheme.Typography.fourteen_500,
                                            onClick = { }
                                        )
                                    }
                                    Box(
                                        modifier = Modifier.weight(0.25f)
                                    ){
                                        IconAndText(
                                            iconImageVector = R.drawable.ic_my_bookmark,
                                            iconColor = ZipdabangandroidTheme.Colors.Cream,
                                            iconModifier = Modifier.size(30.dp, 26.dp),
                                            text = "스크랩",
                                            textColor = ZipdabangandroidTheme.Colors.Typo,
                                            textStyle = ZipdabangandroidTheme.Typography.fourteen_500,
                                            onClick = { }
                                        )
                                    }
                                    Box(
                                        modifier = Modifier.weight(0.25f)
                                    ){
                                        IconAndText(
                                            iconImageVector = R.drawable.zipdabanglogo_white,
                                            iconColor = Color.Transparent,
                                            iconModifier = Modifier.size(30.dp, 26.dp),
                                            text = "나의 레시피",
                                            textColor = ZipdabangandroidTheme.Colors.Typo,
                                            textStyle = ZipdabangandroidTheme.Typography.fourteen_500,
                                            onClick = { }
                                        )
                                    }
                                    Box(
                                        modifier = Modifier.weight(0.25f)
                                    ){
                                        IconAndText(
                                            iconImageVector = R.drawable.ic_my_shopping_cart,
                                            iconColor = ZipdabangandroidTheme.Colors.Choco,
                                            iconModifier = Modifier.size(30.dp, 26.dp),
                                            text = "쇼핑",
                                            textColor = ZipdabangandroidTheme.Colors.Typo,
                                            textStyle = ZipdabangandroidTheme.Typography.fourteen_500,
                                            onClick = { }
                                        )
                                    }
                                }

                                Column(
                                    modifier = Modifier
                                        .height(180.dp)
                                        .fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ){
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth()
                                            .background(Color.White)
                                            .clip(RoundedCornerShape(16.dp))
                                    ){

                                    }
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth()
                                            .background(Color.White)
                                            .clip(RoundedCornerShape(16.dp))
                                    ){

                                    }
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth()
                                            .background(Color.White)
                                            .clip(RoundedCornerShape(16.dp))
                                    ){

                                    }
                                }

                                Text(
                                    text="로그아웃",
                                    modifier = Modifier.clickable(
                                        onClick = {
                                            CoroutineScope(Dispatchers.Main).launch {
                                                //tokenStoreViewModel.resetToken()
                                                Log.e("signup-tokens","로그아웃 클릭, postJob 실행 중")
                                                onClickLogout()
                                                Log.e("signup-tokens","로그아웃 클릭, onClick 실행 끝")
                                            }
                                        }
                                    )
                                )
                            }
                        }
                    }
                }
            )
        },
        drawerState = drawerState
    )

}

@Preview
@Composable
fun PreviewMyScreen() {
    MyScreen(
        onClickLogout = {}
    )
}
