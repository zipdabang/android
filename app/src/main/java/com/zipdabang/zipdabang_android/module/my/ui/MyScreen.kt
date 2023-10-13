package com.zipdabang.zipdabang_android.module.my.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.Pager
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.MyViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarMy
import com.zipdabang.zipdabang_android.ui.component.CircleImage
import com.zipdabang.zipdabang_android.ui.component.ColumnPagersNoPadding
import com.zipdabang.zipdabang_android.ui.component.IconAndText
import com.zipdabang.zipdabang_android.ui.component.ImageIconAndText
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.component.Pager
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyScreen(
    myViewModel: MyViewModel = hiltViewModel(),
    navController: NavController,
    onClickBack: () -> Unit,
    onClickLike: () -> Unit,
    onClickScrap: () -> Unit,
    onClickMyrecipe: () -> Unit,
    onClickShopping: () -> Unit,
    onClickNotice: () -> Unit,
    onAlarm : ()->Unit,
    onInquiry : ()->Unit,
    onClickLogout: () -> Unit,
    onClickUserInfo: () -> Unit,
) {
    //drawer에 필요한 drawerState랑 scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    //val scaffoldState = rememberBottomSheetScaffoldState()
    //val tokenStoreViewModel = hiltViewModel<ProtoDataViewModel>()
    val stateMyUserInfo = myViewModel.stateMyUserInfo

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
                        onClickStartIcon = { onClickBack() },
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
                        // 프로필 부분
                        Row(
                            modifier = Modifier
                                .height(182.dp)
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
                                        text = stateMyUserInfo.nickname,
                                        style = ZipdabangandroidTheme.Typography.thirtytwo_700,
                                        color = Color.White
                                    )
                                }
                                Row(
                                    modifier = Modifier.padding(6.dp, 2.dp, 0.dp, 0.dp)
                                ) {
                                    Text(
                                        text = "팔로우 000    |    팔로잉 000",
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
                                    Text(
                                        text = "프로필 편집하기",
                                        textAlign = TextAlign.Center,
                                        color = ZipdabangandroidTheme.Colors.Typo,
                                        style = ZipdabangandroidTheme.Typography.fourteen_500,
                                        maxLines = 1,
                                        modifier = Modifier.clickable(onClick = {
                                            //onClickUserInfo()
                                            onClickMyrecipe()
                                        }),
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
                                        imageUrl = stateMyUserInfo.profileUrl,
                                        contentDescription = ""
                                    )
                                }
                            }
                        }



                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ){
                            ColumnPagersNoPadding(
                                tabsList = listOf(
                                    TabItem.MyProfile(),
                                    TabItem.MyRecipes(),
                                    TabItem.MyInfo(
//                                        onClickLike = onClickLike,
//                                        onClickScrap = onClickScrap,
//                                        onClickMyrecipe = onClickMyrecipe,
//                                        onClickShopping = onClickShopping,
//                                        onClickNotice = onClickNotice,
//                                        onAlarm = onAlarm,
//                                        onInquiry = onInquiry,
//                                        onClickLogout = onClickLogout,
                                    )
                                ),
                                pagerState = pagerState
                            )
                        }


                        /*Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = ZipdabangandroidTheme.Colors.SubBackground,
                                ),
                            verticalArrangement = Arrangement.Center
                        ) {



                            Box(
                                modifier = Modifier
                                    .height(48.dp)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "000 님의 홈카페에 어서오세요",
                                    style = ZipdabangandroidTheme.Typography.sixteen_700,
                                    color = ZipdabangandroidTheme.Colors.Strawberry,
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .height(120.dp)
                                    .fillMaxWidth()
                                    .padding(16.dp, 16.dp, 16.dp, 16.dp)
                                    .background(
                                        color = Color.White,
                                        shape = ZipdabangandroidTheme.Shapes.small
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Spacer(modifier = Modifier.weight(0.02f))
                                Box(
                                    modifier = Modifier.weight(0.25f)
                                ) {
                                    IconAndText(
                                        iconImageVector = R.drawable.ic_my_favorite,
                                        iconColor = ZipdabangandroidTheme.Colors.Strawberry,
                                        iconModifier = Modifier.size(30.dp, 26.dp),
                                        text = stringResource(id = R.string.my_like),
                                        textColor = ZipdabangandroidTheme.Colors.Typo,
                                        textStyle = ZipdabangandroidTheme.Typography.fourteen_500,
                                        onClick = {
                                            onClickLike()
                                        }
                                    )
                                }
                                Box(
                                    modifier = Modifier.weight(0.25f)
                                ) {
                                    IconAndText(
                                        iconImageVector = R.drawable.ic_my_bookmark,
                                        iconColor = ZipdabangandroidTheme.Colors.Cream,
                                        iconModifier = Modifier.size(22.dp, 26.dp),
                                        text = stringResource(id = R.string.my_scrap),
                                        textColor = ZipdabangandroidTheme.Colors.Typo,
                                        textStyle = ZipdabangandroidTheme.Typography.fourteen_500,
                                        onClick = {
                                            onClickScrap()
                                        }
                                    )
                                }
                                Box(
                                    modifier = Modifier.weight(0.25f)
                                ) {
                                    ImageIconAndText(
                                        iconImageVector = R.drawable.zipdabanglogo_white,
                                        iconColor = Color.Transparent,
                                        iconModifier = Modifier.size(40.dp, 40.dp),
                                        text = stringResource(id = R.string.my_myrecipe),
                                        textColor = ZipdabangandroidTheme.Colors.Typo,
                                        textStyle = ZipdabangandroidTheme.Typography.fourteen_500,
                                        onClick = {
                                            onClickMyrecipe()
                                        }
                                    )
                                }
                                Box(
                                    modifier = Modifier.weight(0.25f)
                                ) {
                                    IconAndText(
                                        iconImageVector = R.drawable.ic_my_shoppingcart,
                                        iconColor = ZipdabangandroidTheme.Colors.Choco,
                                        iconModifier = Modifier.size(40.dp, 40.dp),
                                        text = stringResource(id = R.string.my_shopping),
                                        textColor = ZipdabangandroidTheme.Colors.Typo,
                                        textStyle = ZipdabangandroidTheme.Typography.fourteen_500,
                                        onClick = {
                                            onClickShopping()
                                        }
                                    )
                                }
                                Spacer(modifier = Modifier.weight(0.02f))
                            }

                            Column(
                                modifier = Modifier
                                    .height(172.dp)
                                    .fillMaxWidth()
                                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .clickable(
                                            onClick = {
                                                onClickFriendList()
                                            },
                                        )
                                        .background(
                                            color = Color.White,
                                            shape = ZipdabangandroidTheme.Shapes.small
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp, 0.dp, 16.dp, 0.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.my_friendlist),
                                            style = ZipdabangandroidTheme.Typography.fourteen_500,
                                            color = ZipdabangandroidTheme.Colors.Typo
                                        )
                                        Text(
                                            text = "0",
                                            style = ZipdabangandroidTheme.Typography.fourteen_500,
                                            color = ZipdabangandroidTheme.Colors.Typo
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(onClick = {
                                            onClickNotice()
                                        })
                                        .weight(1f)
                                        .background(
                                            color = Color.White,
                                            shape = ZipdabangandroidTheme.Shapes.small
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp, 0.dp, 16.dp, 0.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.my_noticeandevent),
                                            style = ZipdabangandroidTheme.Typography.fourteen_500,
                                            color = ZipdabangandroidTheme.Colors.Typo
                                        )
                                        Text(
                                            text = "0",
                                            style = ZipdabangandroidTheme.Typography.fourteen_500,
                                            color = ZipdabangandroidTheme.Colors.Typo
                                        )
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                        .background(
                                            color = Color.White,
                                            shape = ZipdabangandroidTheme.Shapes.small
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp, 0.dp, 16.dp, 0.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ) {
                                        Text(
                                            text = stringResource(id = R.string.my_alarm),
                                            style = ZipdabangandroidTheme.Typography.fourteen_500,
                                            color = ZipdabangandroidTheme.Colors.Typo
                                        )
                                        Text(
                                            text = "0",
                                            style = ZipdabangandroidTheme.Typography.fourteen_500,
                                            color = ZipdabangandroidTheme.Colors.Typo
                                        )
                                    }
                                }
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp, 16.dp, 16.dp, 0.dp),
                                verticalArrangement = Arrangement.spacedBy(2.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                        .padding(2.dp, 0.dp, 0.dp, 0.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_my_deliverytruck),
                                        contentDescription = "",
                                        tint = ZipdabangandroidTheme.Colors.Typo,
                                        modifier = Modifier.size(20.dp, 14.dp)
                                    )
                                    Text(
                                        text = stringResource(id = R.string.my_market_ing),
                                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                                        color = ZipdabangandroidTheme.Colors.Typo
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .height(60.dp)
                                        .fillMaxWidth()
                                        .background(
                                            color = ZipdabangandroidTheme.Colors.MainBackground,
                                            shape = RoundedCornerShape(4.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.my_market_notdeploy),
                                        style = ZipdabangandroidTheme.Typography.fourteen_300,
                                        color = ZipdabangandroidTheme.Colors.Typo,
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp, 72.dp, 16.dp, 32.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                ClickableText(
                                    modifier = Modifier,
                                    text = AnnotatedString(text = stringResource(id = R.string.my_logout)),
                                    style = ZipdabangandroidTheme.Typography.fourteen_300,
                                    onClick = {
                                        Log.d("logout", "clicked")
                                        myViewModel.signOut(onClickLogout)
                                    }
                                )
                                Text(
                                    text = "|",
                                    style = ZipdabangandroidTheme.Typography.twelve_300,
                                    color = ZipdabangandroidTheme.Colors.Typo,
                                    modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp),
                                )
                                ClickableText(
                                    text = AnnotatedString(text = stringResource(id = R.string.my_myinfo)),
                                    style = ZipdabangandroidTheme.Typography.fourteen_300,
                                    onClick = {
                                        onClickUserInfo()
                                    }
                                )
                            }

                        }*/
                    }
                },
            )
        },
        drawerState = drawerState,
        navController = navController,
    )

}

@Preview
@Composable
fun PreviewMyScreen() {
    MyScreen(
        navController = rememberNavController(),
        onClickBack = {},
        onClickLike = {},
        onClickScrap = {},
        onClickMyrecipe = {},
        onClickShopping = {},
        onClickNotice = {},
        onClickLogout = {},
        onClickUserInfo = {},
        onAlarm = {},
        onInquiry = {},
    )
}
