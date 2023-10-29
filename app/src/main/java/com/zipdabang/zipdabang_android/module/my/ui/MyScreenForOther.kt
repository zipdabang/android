package com.zipdabang.zipdabang_android.module.my.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.TabItem
import com.zipdabang.zipdabang_android.module.my.data.remote.followorcancel.FollowState
import com.zipdabang.zipdabang_android.module.my.ui.component.recipewrite.ButtonForFollow
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.MyForOthersViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarMy
import com.zipdabang.zipdabang_android.ui.component.CircleImage
import com.zipdabang.zipdabang_android.ui.component.ColumnPagersNoPadding
import com.zipdabang.zipdabang_android.ui.component.ModalDrawer
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MyScreenForOther(
    navController : NavController,
    userId : Int,
    onClickHeader : (String) -> Unit,
    onRecipeItemClick : (Int) -> Unit,
    viewModel : MyForOthersViewModel = hiltViewModel()
) {

    //drawer에 필요한 drawerState랑 scope
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    val context = LocalContext.current

    val infoState = viewModel.otherInfoState
    val commonInfoState = viewModel.commonInfoState
    val userId = remember{
        mutableStateOf(userId)
    }

    val followingNum = remember{
        mutableStateOf(0)
    }
    val followerNum = remember{
        mutableStateOf(0)
    }
    var buttonState = remember{
        mutableStateOf(FollowState.NotFriend)
    }
    if(infoState.value.isSuccess) {
        followerNum.value = commonInfoState.value.followNum
        followingNum.value = commonInfoState.value.followingNum
        Log.e("friendlist test", "recompose")
        buttonState.value =
            if (commonInfoState.value.isFollower && commonInfoState.value.isFollowing)
                FollowState.FollowEach
            else if (!commonInfoState.value.isFollower && commonInfoState.value.isFollowing)
                FollowState.UserOnlyFollow
            else if (commonInfoState.value.isFollower && !commonInfoState.value.isFollowing)
                FollowState.OtherOnlyFollow
            else FollowState.NotFriend

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
                                            text = "팔로우 ${followingNum.value} | 팔로잉 ${followerNum.value}",
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
                                            .height(35.dp)
                                            .background(
                                                color = Color.Transparent,
                                                shape = ZipdabangandroidTheme.Shapes.medium
                                            ),
                                    ) {

                                        Log.e("friendlist test",buttonState.toString())
                                       if(!commonInfoState.value.isCheckSelf) ButtonForFollow(
                                            onClick = {
                                                //내가 상대방 팔로우

                                              if(buttonState.value==FollowState.FollowEach) {

                                                  viewModel.followOrCancel(
                                                      userId.value
                                                  )
                                                  buttonState.value = FollowState.OtherOnlyFollow
                                                  followerNum.value -= 1
                                              }
                                              else if(buttonState.value==FollowState.UserOnlyFollow) {

                                                    viewModel.followOrCancel(
                                                        userId.value
                                                    )
                                                    buttonState.value = FollowState.NotFriend
                                                    followerNum.value -= 1
                                                }


                                                //상대방이 나를 팔로우, 나는 상대 팔로우 x
                                             else  if (buttonState.value == FollowState.OtherOnlyFollow) {
                                                    viewModel.followOrCancel(
                                                        userId.value
                                                    )
                                                    Log.e("userid",userId.value.toString())
                                                    //맞팔 취소하면 ->
                                                  buttonState.value = FollowState.FollowEach
                                                  followerNum.value += 1


                                              } else {
                                                    //친구 아님
                                                    viewModel.followOrCancel(
                                                        userId.value
                                                    )
                                                    buttonState.value = FollowState.UserOnlyFollow
                                                  followerNum.value += 1

                                              }

                                            },
                                            followState = buttonState.value
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
                                            imageUrl = commonInfoState.value.profileUrl,
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
                                            nickname = commonInfoState.value.nickName,
                                            onClickHeader = {
                                                onClickHeader(commonInfoState.value.nickName)
                                            },
                                            onRecipeItemClick = {
                                                onRecipeItemClick(it)
                                            }
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