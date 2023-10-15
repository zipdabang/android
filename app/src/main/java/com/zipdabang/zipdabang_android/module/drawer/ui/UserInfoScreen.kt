package com.zipdabang.zipdabang_android.module.drawer.ui

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.DrawerUserInfoViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.shimmeringEffect
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun UserInfoScreen(
    drawerUserInfoViewModel: DrawerUserInfoViewModel = hiltViewModel(),
    onClickBack: () -> Unit,
    onClickEdit: () -> Unit,
    onClickEditBasic: () -> Unit,
    onClickEditDetail: () -> Unit,
    onClickEditNickname: () -> Unit,
    onClickEditOneLine: () -> Unit,
    onClickEditPreferences: () -> Unit,
    onClickLogout: () -> Unit,
    onClickWithdraw: () -> Unit,
) {
    val stateUserInfo = drawerUserInfoViewModel.stateUserInfo

    var shimmering: Boolean = true
    if (stateUserInfo.isLoading || stateUserInfo.error.isNotBlank()) {
        shimmering = true
    } else {
        shimmering = false
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = { onClickBack() },
                centerText = stringResource(id = R.string.my_myinfo)
            )
        },
        containerColor = Color.White,
        contentColor = Color.White,
    ) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {
            //프로필 부분
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.padding(0.dp, 12.dp, 0.dp, 0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp, 120.dp)
                            .border(1.dp, ZipdabangandroidTheme.Colors.BlackSesame, CircleShape)
                        //.clip(CircleShape)

                    ) {
                        AsyncImage(
                            model = stateUserInfo.profileUrl,
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
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF7F6F6))
                            .border(1.dp, Color.White, CircleShape)
                            .align(Alignment.BottomEnd)
                            .padding(0.dp)
                            .clickable(onClick = { if (!shimmering) onClickEdit() })
                            .zIndex(1f),
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_my_edit),
                                contentDescription = "",
                                tint = ZipdabangandroidTheme.Colors.Typo,
                                modifier = Modifier
                                    .size(24.dp, 24.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    )
                }
                Text(
                    textAlign = TextAlign.Center,
                    text = if (shimmering) {
                        ""
                    } else {
                        stateUserInfo.email
                    },
                    style = ZipdabangandroidTheme.Typography.sixteen_300,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    modifier = Modifier
                        .padding(0.dp, 4.dp, 0.dp, 0.dp)
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

            //기본 정보
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(16.dp, 20.dp, 16.dp, 20.dp)
                    .background(
                        color = Color.Transparent,
                        shape = ZipdabangandroidTheme.Shapes.small,
                    )
                    .shadow(
                        elevation = 4.dp,
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    ZipdabangandroidTheme.Colors.Strawberry,
                                    ZipdabangandroidTheme.Colors.Cream
                                )
                            ),
                            shape = ZipdabangandroidTheme.Shapes.smallRoundedTop,
                        )
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.signup_userinfo_basicinfo),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = Color.White
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_my_edit),
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp, 24.dp)
                            .clickable(onClick = { if (!shimmering) onClickEditBasic() })
                    )
                }
                //이름
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_signup_name),
                        contentDescription = "",
                        tint = ZipdabangandroidTheme.Colors.Typo,
                        modifier = Modifier
                            .size(28.dp, 28.dp)
                            .padding(4.dp, 0.dp, 0.dp, 0.dp)
                    )
                    Text(
                        modifier = Modifier
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                            .fillMaxWidth()
                            .then(
                                if (shimmering) {
                                    Modifier.shimmeringEffect()
                                } else {
                                    Modifier
                                }
                            ),
                        text = if (shimmering) {
                            ""
                        } else {
                            stateUserInfo.name
                        },
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    color = ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
                )
                //생년월일 앞자리랑 성별
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (shimmering) {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_signup_birthdaycake),
                                contentDescription = "",
                                tint = ZipdabangandroidTheme.Colors.Typo,
                                modifier = Modifier
                                    .size(28.dp, 30.dp)
                                    .padding(4.dp, 0.dp, 0.dp, 0.dp)
                            )
                            Text(
                                modifier = Modifier
                                    .padding(12.dp, 3.dp, 0.dp, 0.dp)
                                    .shimmeringEffect()
                                    .fillMaxWidth(),
                                text = if (shimmering) {
                                    ""
                                } else {
                                    stateUserInfo.birthday
                                },
                                style = ZipdabangandroidTheme.Typography.sixteen_500,
                                color = ZipdabangandroidTheme.Colors.Typo
                            )
                        }
                    } else {
                        Row {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_signup_birthdaycake),
                                contentDescription = "",
                                tint = ZipdabangandroidTheme.Colors.Typo,
                                modifier = Modifier
                                    .size(28.dp, 30.dp)
                                    .padding(4.dp, 0.dp, 0.dp, 0.dp)
                            )
                            Text(
                                modifier = Modifier.padding(12.dp, 3.dp, 0.dp, 0.dp),
                                text = stateUserInfo.birthday,
                                style = ZipdabangandroidTheme.Typography.sixteen_500,
                                color = ZipdabangandroidTheme.Colors.Typo
                            )
                        }
                        Text(
                            modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp),
                            text = stateUserInfo.gender,
                            style = ZipdabangandroidTheme.Typography.sixteen_500,
                            color = ZipdabangandroidTheme.Colors.Typo
                        )
                    }
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    color = ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
                )
                //전화번호
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = ZipdabangandroidTheme.Shapes.smallRoundedBottom
                        )
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_signup_phone),
                        contentDescription = "",
                        tint = ZipdabangandroidTheme.Colors.Typo,
                        modifier = Modifier
                            .size(30.dp, 30.dp)
                            .padding(4.dp, 0.dp, 0.dp, 0.dp)
                    )
                    Text(
                        modifier = Modifier
                            .padding(6.dp, 0.dp, 0.dp, 0.dp)
                            .fillMaxWidth()
                            .then(
                                if (shimmering) {
                                    Modifier.shimmeringEffect()
                                } else {
                                    Modifier
                                }
                            ),
                        text = if (shimmering) {
                            ""
                        } else {
                            stateUserInfo.phoneNumber
                        },
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }
            }

            //상세 정보
            /*Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(16.dp, 0.dp, 16.dp, 20.dp)
                    .background(
                        color = Color.Transparent,
                        shape = ZipdabangandroidTheme.Shapes.small,
                    )
                    .shadow(
                        elevation = 2.dp,
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    ZipdabangandroidTheme.Colors.Strawberry,
                                    ZipdabangandroidTheme.Colors.Cream
                                )
                            ),
                            shape = ZipdabangandroidTheme.Shapes.smallRoundedTop,
                        )
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.signup_userinfo_detailinfo),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = Color.White
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_my_edit),
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp, 24.dp)
                            .clickable(onClick = { if(!shimmering)onClickEditDetail() })
                    )
                }
                //우편번호
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_signup_zipcode),
                        contentDescription = "",
                        tint = ZipdabangandroidTheme.Colors.Typo,
                        modifier = Modifier
                            .size(28.dp, 28.dp)
                            .padding(4.dp, 0.dp, 0.dp, 0.dp)
                    )
                    Text(
                        modifier = Modifier
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                            .fillMaxWidth()
                            .then(
                                if (shimmering) {
                                    Modifier.shimmeringEffect()
                                } else {
                                    Modifier
                                }
                            ),
                        text = stateUserInfo.zipcode,
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    color = ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
                )
                //주소
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_signup_address),
                        contentDescription = "",
                        tint = ZipdabangandroidTheme.Colors.Typo,
                        modifier = Modifier
                            .size(28.dp, 30.dp)
                            .padding(4.dp, 0.dp, 0.dp, 0.dp)
                    )
                    Text(
                        modifier = Modifier
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                            .fillMaxWidth()
                            .then(
                                if (shimmering) {
                                    Modifier.shimmeringEffect()
                                } else {
                                    Modifier
                                }
                            ),
                        text = stateUserInfo.address,
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    color = ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
                )
                //상세 주소
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = ZipdabangandroidTheme.Shapes.smallRoundedBottom
                        )
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(30.dp, 30.dp)
                            .padding(4.dp, 0.dp, 0.dp, 0.dp)
                    )
                    Text(
                        modifier = Modifier
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                            .fillMaxWidth()
                            .then(
                                if (shimmering) {
                                    Modifier.shimmeringEffect()
                                } else {
                                    Modifier
                                }
                            ),
                        text = stateUserInfo.detailAddress,
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }
            }*/

            //닉네임
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
                    .padding(16.dp, 0.dp, 16.dp, 20.dp)
                    .background(
                        color = Color.Transparent,
                        shape = ZipdabangandroidTheme.Shapes.small,
                    )
                    .shadow(
                        elevation = 2.dp,
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    ZipdabangandroidTheme.Colors.Strawberry,
                                    ZipdabangandroidTheme.Colors.Cream
                                )
                            ),
                            shape = ZipdabangandroidTheme.Shapes.smallRoundedTop,
                        )
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.signup_nickname),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = Color.White
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_my_edit),
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .size(22.dp, 22.dp)
                            .clickable(onClick = { if (!shimmering) onClickEditNickname() })
                    )
                }
                //닉네임
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = ZipdabangandroidTheme.Shapes.smallRoundedBottom
                        )
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_drawer_nickname),
                        contentDescription = "",
                        tint = ZipdabangandroidTheme.Colors.Typo,
                        modifier = Modifier
                            .size(30.dp, 30.dp)
                            .padding(2.dp, 0.dp, 0.dp, 0.dp)
                    )
                    Text(
                        modifier = Modifier
                            .padding(6.dp, 0.dp, 0.dp, 0.dp)
                            .fillMaxWidth()
                            .then(
                                if (shimmering) {
                                    Modifier.shimmeringEffect()
                                } else {
                                    Modifier
                                }
                            ),
                        text = if (shimmering) {
                            ""
                        } else {
                            stateUserInfo.nickname
                        },
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }
            }

            //한 줄 소개
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
                    .padding(16.dp, 0.dp, 16.dp, 20.dp)
                    .background(
                        color = Color.Transparent,
                        shape = ZipdabangandroidTheme.Shapes.small,
                    )
                    .shadow(
                        elevation = 2.dp,
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    ZipdabangandroidTheme.Colors.Strawberry,
                                    ZipdabangandroidTheme.Colors.Cream
                                )
                            ),
                            shape = ZipdabangandroidTheme.Shapes.smallRoundedTop,
                        )
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.drawer_oneline),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = Color.White
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_my_edit),
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp, 24.dp)
                            .clickable(onClick = { if (!shimmering) onClickEditOneLine() })
                    )
                }
                //한 줄 소개
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = ZipdabangandroidTheme.Shapes.smallRoundedBottom
                        )
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_drawer_oneline),
                        contentDescription = "",
                        tint = ZipdabangandroidTheme.Colors.Typo,
                        modifier = Modifier
                            .size(30.dp, 30.dp)
                            .padding(2.dp, 0.dp, 0.dp, 0.dp)
                    )
                    Text(
                        modifier = Modifier
                            .padding(6.dp, 0.dp, 0.dp, 0.dp)
                            .fillMaxWidth()
                            .then(
                                if (shimmering) {
                                    Modifier.shimmeringEffect()
                                } else {
                                    Modifier
                                }
                            ),
                        text = if (shimmering) ""
                        else if (stateUserInfo.oneline == "") stringResource(id = R.string.drawer_oneline_not_exist)
                        else stateUserInfo.oneline,
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }
            }

            //선호하는 음료
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp, 0.dp, 16.dp, 20.dp)
                    .background(
                        color = Color.Transparent,
                        shape = ZipdabangandroidTheme.Shapes.small,
                    )
                    .shadow(
                        elevation = 2.dp,
                    ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    ZipdabangandroidTheme.Colors.Strawberry,
                                    ZipdabangandroidTheme.Colors.Cream
                                )
                            ),
                            shape = ZipdabangandroidTheme.Shapes.smallRoundedTop,
                        )
                        .wrapContentHeight()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.drawer_preferbeverages),
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = Color.White
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_my_edit),
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp, 24.dp)
                            .clickable(onClick = { if (!shimmering) onClickEditPreferences() })
                    )
                }
                //선호하는 음료
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = ZipdabangandroidTheme.Shapes.smallRoundedBottom
                        )
                        .height(
                            if (stateUserInfo.size <= 4) 52.dp
                            else 84.dp
                        )
                        .wrapContentHeight()
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_my_heart),
                            contentDescription = "",
                            tint = ZipdabangandroidTheme.Colors.Typo,
                            modifier = Modifier
                                .size(24.dp, 22.dp)
                                .padding(4.dp, 0.dp, 0.dp, 4.dp)
                        )
                        if (stateUserInfo.preferBeverageList.size == 0) {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp, 0.dp, 0.dp, 0.dp)
                                    .then(
                                        if (shimmering) {
                                            Modifier.shimmeringEffect()
                                        } else {
                                            Modifier
                                        }
                                    ),
                                text = stringResource(R.string.drawer_preferbeverages_not_exist),
                                style = ZipdabangandroidTheme.Typography.sixteen_500,
                                color = ZipdabangandroidTheme.Colors.Typo,
                            )
                        } else if (shimmering) {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp, 0.dp, 0.dp, 0.dp)
                                    .shimmeringEffect()
                                    .fillMaxWidth(),
                                text = "",
                                style = ZipdabangandroidTheme.Typography.sixteen_500,
                                color = ZipdabangandroidTheme.Colors.Typo,
                            )
                        } else {
                            stateUserInfo.preferBeverageList.take(4)
                                .forEachIndexed { index, beverage ->
                                    Text(
                                        modifier = Modifier
                                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                                            .then(
                                                if (shimmering) {
                                                    Modifier.shimmeringEffect()
                                                } else {
                                                    Modifier
                                                }
                                            ),
                                        text = beverage,
                                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                                        color = ZipdabangandroidTheme.Colors.Typo,
                                    )
                                }
                        }
                    }
                    if (stateUserInfo.size > 4) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            if(shimmering){
                                Spacer(
                                    modifier = Modifier
                                        .shimmeringEffect()
                                        .fillMaxWidth()
                                )
                            }
                            else{
                                Spacer(
                                    modifier = Modifier
                                        .size(26.dp, 24.dp)
                                        .padding(8.dp, 0.dp, 0.dp, 0.dp)
                                )
                                stateUserInfo.preferBeverageList.drop(4)
                                    .forEachIndexed { index, beverage ->
                                        Text(
                                            modifier = Modifier
                                                .padding(6.dp, 0.dp, 0.dp, 0.dp),
                                            text = beverage,
                                            style = ZipdabangandroidTheme.Typography.sixteen_500,
                                            color = ZipdabangandroidTheme.Colors.Typo,
                                        )
                                    }
                            }
                        }
                    }
                }
            }

            //로그아웃 | 탈퇴하기
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 48.dp, 16.dp, 40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ClickableText(
                    text = AnnotatedString(text = stringResource(id = R.string.my_logout)),
                    style = ZipdabangandroidTheme.Typography.fourteen_300,
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            //tokenStoreViewModel.resetToken()
                            Log.e("signup-tokens", "로그아웃 클릭, postJob 실행 중")
                            onClickLogout()
                            Log.e("signup-tokens", "로그아웃 클릭, onClick 실행 끝")
                        }
                    }
                )
                Text(
                    text = "|",
                    style = ZipdabangandroidTheme.Typography.twelve_300,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 0.dp),
                )
                ClickableText(
                    text = AnnotatedString(text = stringResource(id = R.string.my_withdraw)),
                    style = ZipdabangandroidTheme.Typography.fourteen_300,
                    onClick = {
                        onClickWithdraw()
                    }
                )
            }
        }


    }
}


@Preview
@Composable
fun PreviewUserInfoScrren() {
    UserInfoScreen(
        onClickBack = {},
        onClickEdit = {},
        onClickEditBasic = {},
        onClickEditDetail = {},
        onClickEditNickname = {},
        onClickEditOneLine = {},
        onClickEditPreferences = {},
        onClickLogout = {},
        onClickWithdraw = {}
    )
}