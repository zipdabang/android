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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.IconAndText
import com.zipdabang.zipdabang_android.ui.component.ImageIconAndText
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun MyInfoScreen(
//    onClickLike: Unit,
//    onClickScrap: Unit,
//    onClickMyrecipe: Unit,
//    onClickShopping: Unit,
//    onClickNotice: Unit,
//    onAlarm : Unit,
//    onInquiry : Unit,
//    onClickLogout: Unit,
) {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        // 좋아요/스크랩/나의레시피/쇼핑
        Row(
            modifier = Modifier
                .height(112.dp)
                .fillMaxWidth()
                .padding(16.dp, 20.dp, 16.dp, 20.dp)
                .background(
                    color = Color.White,
                    shape = ZipdabangandroidTheme.Shapes.small
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(0.25f)
                    .background(
                        color = ZipdabangandroidTheme.Colors.Strawberry,
                        shape = RoundedCornerShape(4.dp)
                    )
            ) {
                IconAndText(
                    iconImageVector = R.drawable.ic_my_favorite,
                    iconColor = Color.White,
                    iconModifier = Modifier.size(30.dp, 26.dp),
                    text = stringResource(id = R.string.my_like),
                    textColor = Color.White,
                    textStyle = ZipdabangandroidTheme.Typography.fourteen_700,
                    onClick = {
                        //onClickLike
                    }
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.25f)
                    .background(
                        color = ZipdabangandroidTheme.Colors.Cream,
                        shape = RoundedCornerShape(4.dp)
                    )
            ) {
                IconAndText(
                    iconImageVector = R.drawable.ic_my_bookmark,
                    iconColor = Color.White,
                    iconModifier = Modifier.size(22.dp, 26.dp),
                    text = stringResource(id = R.string.my_scrap),
                    textColor = Color.White,
                    textStyle = ZipdabangandroidTheme.Typography.fourteen_700,
                    onClick = {
                        //onClickScrap
                    }
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.25f)
                    .background(
                        color = ZipdabangandroidTheme.Colors.Choco,
                        shape = RoundedCornerShape(4.dp)
                    )
            ) {
                ImageIconAndText(
                    iconImageVector = R.drawable.ic_my_zipdabanglogo,
                    iconColor = Color.Transparent,
                    iconModifier = Modifier.size(40.dp, 40.dp),
                    text = stringResource(id = R.string.my_myrecipe),
                    textColor = Color.White,
                    textStyle = ZipdabangandroidTheme.Typography.fourteen_700,
                    onClick = {
                        //onClickMyrecipe
                    }
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.25f)
                    .background(
                        color = ZipdabangandroidTheme.Colors.Latte,
                        shape = RoundedCornerShape(4.dp)
                    )
            ) {
                IconAndText(
                    iconImageVector = R.drawable.ic_my_shoppingcart,
                    iconColor = Color.White,
                    iconModifier = Modifier.size(40.dp, 40.dp),
                    text = stringResource(id = R.string.my_shopping),
                    textColor = Color.White,
                    textStyle = ZipdabangandroidTheme.Typography.fourteen_700,
                    onClick = {
                        //onClickShopping
                    }
                )
            }
        }

        // 진행중인 주문
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 0.dp),
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
                    .height(56.dp)
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

        // 공지사항 및 이벤트/ 알림/ 나의 문의내역
        Column(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .padding(16.dp, 20.dp, 16.dp, 0.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        color = Color.White,
                        shape = ZipdabangandroidTheme.Shapes.small
                    ),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                        color = ZipdabangandroidTheme.Colors.Typo,
                        modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp)
                    )
                }
            }
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = ZipdabangandroidTheme.Colors.Typo.copy(0.2f),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        color = Color.White,
                        shape = ZipdabangandroidTheme.Shapes.small
                    ),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(id = R.string.my_alarm),
                        style = ZipdabangandroidTheme.Typography.fourteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo,
                        modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp)
                    )
                    Text(
                        text = "0",
                        style = ZipdabangandroidTheme.Typography.fourteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo,
                        modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp)
                    )
                }
            }
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = ZipdabangandroidTheme.Colors.Typo.copy(0.2f),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        color = Color.White,
                        shape = ZipdabangandroidTheme.Shapes.small
                    ),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "나의 문의내역",
                        style = ZipdabangandroidTheme.Typography.fourteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                    Text(
                        text = "0",
                        style = ZipdabangandroidTheme.Typography.fourteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo,
                        modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp)
                    )
                }
            }
            Divider(
                modifier = Modifier.fillMaxWidth(),
                color = ZipdabangandroidTheme.Colors.Typo.copy(0.2f),
            )
        }


        // 로그아웃 | 회원정보
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 4.dp, 16.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ClickableText(
                modifier = Modifier,
                text = AnnotatedString(text = stringResource(id = R.string.my_logout)),
                style = ZipdabangandroidTheme.Typography.fourteen_300,
                onClick = {
                    Log.d("logout", "clicked")
                    //myViewModel.signOut(onClickLogout)
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
                    //onClickUserInfo()
                }
            )
        }


    }
}