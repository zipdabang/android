package com.zipdabang.zipdabang_android.module.my.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.MyForOthersViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarMy
import com.zipdabang.zipdabang_android.ui.component.CircleImage
import com.zipdabang.zipdabang_android.ui.component.ColumnPagers
import com.zipdabang.zipdabang_android.ui.component.ColumnPagersNoPadding
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyScreenForOther(
    navController : NavController,
    userId : Int,
    viewModel : MyForOthersViewModel = hiltViewModel()
) {

    //drawer에 필요한 drawerState랑 scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    val infoState = viewModel.otherInfoState
    val commonInfoState = viewModel.commonInfoState

    ModalDrawer(
        scaffold = {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                ZipdabangandroidTheme.Colors.Strawberry,
                                ZipdabangandroidTheme.Colors.Choco,
                            ),
                            start = Offset(0f, Float.POSITIVE_INFINITY),
                            end = Offset(Float.POSITIVE_INFINITY, 0f),
                        ),
                        shape = RectangleShape,
                    ),
                topBar = {
                    AppBarMy(
                        startIcon = R.drawable.ic_topbar_backbtn,
                        endIcon = R.drawable.ic_topbar_menu,
                        onClickStartIcon = {
                            //onClickBack()
                        },
                        onClickEndIcon = { scope.launch { drawerState.open() } },
                        centerText = stringResource(id = R.string.zipdabang_title)
                    )
                },
                containerColor = Color.Transparent,
                contentColor = Color.Transparent,
                content = {
                    val scrollState = rememberScrollState()

                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        if (infoState.value.isSuccess) {
                            // 프로필 부분
                            Row(
                                modifier = Modifier
                                    .weight(1.4f)
                                    .fillMaxWidth()
                                    .padding(16.dp, 30.dp, 16.dp, 0.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                //닉네임 & 선호음료 & 팔로우팔로잉
                                Column {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text =  commonInfoState.value.nickName,
                                            style = ZipdabangandroidTheme.Typography.thirtytwo_700,
                                            color = Color.White
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.padding(6.dp, 2.dp, 0.dp, 0.dp)
                                    ) {
                                        Text(
                                            text = "팔로우 ${commonInfoState.value.followNum} | 팔로잉 ${commonInfoState.value.followingNum}",
                                            style = ZipdabangandroidTheme.Typography.fourteen_300,
                                            color = Color.White
                                        )
                                    }
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .clickable(onClick = { })
                                            .padding(0.dp, 16.dp, 0.dp, 0.dp)
                                            .width(200.dp)
                                            .height(28.dp)
                                            .background(
                                                color = Color.White,
                                                shape = ZipdabangandroidTheme.Shapes.medium
                                            ),
                                    ) {
                                        var buttonText : String = "팔로우하기"
                                        if(commonInfoState.value.isFollowing)  buttonText  = "언팔로우 하기"
                                        Text(
                                            text = buttonText,
                                            textAlign = TextAlign.Center,
                                            color = ZipdabangandroidTheme.Colors.Typo,
                                            style = ZipdabangandroidTheme.Typography.fourteen_500,
                                            maxLines = 1,
                                            modifier = Modifier,
                                        )
                                    }

                                }
                                //프로필
                                Box(
                                    contentAlignment = Alignment.CenterEnd
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(104.dp, 104.dp)
                                            .clip(CircleShape),
                                    ) {

                                        CircleImage(
                                            imageUrl = R.drawable.img_profile,
                                            contentDescription = ""
                                        ) //stateMyUserInfo.profileUrl
                                    }

                                }
                            }

                            Box(
                                modifier = Modifier
                                    .background(color = Color.White)
                                    .weight(3f)
                            ) {
                                ColumnPagersNoPadding(
                                    tabsList = listOf(
                                        TabItem.ProfileForOthers(),
                                        TabItem.RecipesForOthers(
                                            nickname = commonInfoState.value.nickName
                                        )
                                    ),
                                    pagerState = pagerState
                                )
                            }


                        }else if(infoState.value.isLoading){

                        }else {
                            Log.e("error in Myscreen","")
                        }
                    }
                },

                )

        },
        drawerState = drawerState,
        navController = navController,
    )
}


@Composable
@Preview
fun MyscreenForOthers(){
    val navController = rememberNavController()
   // MyScreenForOther(navController =navController )
}