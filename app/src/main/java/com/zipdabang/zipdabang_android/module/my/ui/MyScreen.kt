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
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.AsyncImage
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
import com.zipdabang.zipdabang_android.ui.shimmeringEffect
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
    onClickAlarm : ()->Unit,
    onClickInquiry : ()->Unit,
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

    var shimmering: Boolean = true
    if (stateMyUserInfo.isLoading || stateMyUserInfo.error.isNotBlank()) {
        shimmering = true
    } else {
        shimmering = false
    }

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
                            Column {
                                // 닉네임
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        text = stateMyUserInfo.nickname,
                                        style = ZipdabangandroidTheme.Typography.thirtytwo_700,
                                        color = Color.White,
                                        modifier = Modifier
                                            .width(160.dp)
                                            .then(
                                            if (shimmering) {
                                                Modifier.shimmeringEffect()
                                            } else {
                                                Modifier
                                            }
                                        ),
                                    )
                                }
                                // 팔로우팔로잉
                                Row(
                                    modifier = Modifier.padding(6.dp, 2.dp, 0.dp, 0.dp)
                                ) {
                                    Text(
                                        text = if (shimmering) {
                                            ""
                                        } else {
                                            "팔로우  ${stateMyUserInfo.followerCount}    |    팔로잉 ${stateMyUserInfo.followingCount}"
                                        },
                                        style = ZipdabangandroidTheme.Typography.fourteen_300,
                                        color = Color.White,
                                        modifier = Modifier
                                            .width(160.dp)
                                            .then(
                                            if (shimmering) {
                                                Modifier.shimmeringEffect()
                                            } else {
                                                Modifier
                                            }
                                        ),
                                    )
                                }
                                // 프로필 편집하기
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .clickable(onClick = {
                                            onClickUserInfo()
                                        })
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
                                    AsyncImage(
                                        model = stateMyUserInfo.profileUrl,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .fillMaxSize()
                                            .then(
                                                if (shimmering) {
                                                    Modifier.shimmeringEffect()
                                                } else {
                                                    Modifier
                                                }
                                            ),
                                        contentScale = ContentScale.Crop,
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
                                    TabItem.MyProfile(
                                        shimmering = shimmering,
                                        oneline = stateMyUserInfo.oneline,
                                        preferCategoryList = stateMyUserInfo.memberPreferCategoryDto,
                                        onClickUserInfo = onClickUserInfo
                                    ),
                                    TabItem.MyRecipes(
                                        shimmering = shimmering,
                                        nickname = stateMyUserInfo.nickname,
                                        onClickMyrecipe = onClickMyrecipe,
                                    ),
                                    TabItem.MyInfo(
                                        onClickLike = onClickLike,
                                        onClickScrap = onClickScrap,
                                        onClickMyrecipe = onClickMyrecipe,
                                        onClickShopping = onClickShopping,
                                        onClickNotice = onClickNotice,
                                        onClickAlarm = onClickAlarm,
                                        onClickInquiry = onClickInquiry,
                                        onClickLogout = onClickLogout,
                                        onClickUserInfo = onClickUserInfo
                                    )
                                ),
                                pagerState = pagerState
                            )
                        }
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
        onClickAlarm = {},
        onClickInquiry = {},
    )
}
