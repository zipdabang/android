package com.zipdabang.zipdabang_android.module.drawer.ui

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.DrawerUserInfoViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.CircleImage
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun UserInfoScreen(
    drawerUserInfoViewModel : DrawerUserInfoViewModel = hiltViewModel(),
    onClickBack : ()->Unit,
    onClickEdit : ()->Unit,
    onClickEditBasic : ()->Unit,
    onClickEditDetail : ()->Unit,
    onClickEditNickname : ()->Unit,
    onClickLogout : ()->Unit,
    onClickWithdraw : ()->Unit,
) {
    val stateUserInfo = drawerUserInfoViewModel.stateUserInfo

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
    ){
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
            ){
                Box(
                    modifier = Modifier.padding(0.dp, 12.dp, 0.dp, 0.dp)
                ){
                    Box(
                        modifier = Modifier
                            .size(120.dp, 120.dp)
                            .clip(CircleShape)
                    ){
                        CircleImage(imageUrl = stateUserInfo.profileUrl, contentDescription = "")
                    }
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF7F6F6))
                            .border(1.dp, Color.White, CircleShape)
                            .align(Alignment.BottomEnd)
                            .padding(0.dp)
                            .clickable(onClick = { onClickEdit() })
                            .zIndex(1f),
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_my_edit),
                                contentDescription = "",
                                tint = ZipdabangandroidTheme.Colors.Typo,
                                modifier = Modifier
                                    .size(15.dp, 12.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    )
                }
                Text(
                    text = stateUserInfo.email,
                    style = ZipdabangandroidTheme.Typography.sixteen_300,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    modifier = Modifier.padding(0.dp, 4.dp, 0.dp,0.dp)
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
            ){
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
                ){
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
                            .size(16.dp, 16.dp)
                            .clickable(onClick = { onClickEditBasic() })
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
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_signup_name),
                        contentDescription = "",
                        tint = ZipdabangandroidTheme.Colors.Strawberry,
                        modifier = Modifier
                            .size(22.dp, 22.dp)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                    )
                    Text(
                        modifier = Modifier.padding(8.dp, 0.dp,0.dp,0.dp),
                        text = stateUserInfo.name,
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
                ){
                    Row{
                        Icon(
                            painter = painterResource(id = R.drawable.ic_signup_birthdaycake),
                            contentDescription = "",
                            tint = ZipdabangandroidTheme.Colors.Strawberry,
                            modifier = Modifier
                                .size(22.dp, 24.dp)
                                .padding(8.dp, 0.dp, 0.dp, 0.dp)
                        )
                        Text(
                            modifier = Modifier.padding(12.dp, 0.dp,0.dp,0.dp),
                            text = stateUserInfo.birthday,
                            style = ZipdabangandroidTheme.Typography.sixteen_500,
                            color = ZipdabangandroidTheme.Colors.Typo
                        )
                    }
                    Text(
                        modifier = Modifier.padding(0.dp, 0.dp, 8.dp,0.dp),
                        text = stateUserInfo.gender,
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
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_signup_phone),
                        contentDescription = "",
                        tint = ZipdabangandroidTheme.Colors.Strawberry,
                        modifier = Modifier
                            .size(24.dp, 24.dp)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                    )
                    Text(
                        modifier = Modifier.padding(8.dp, 0.dp,0.dp,0.dp),
                        text = stateUserInfo.phoneNumber,
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
            ){
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
                ){
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
                            .size(16.dp, 16.dp)
                            .clickable(onClick = { onClickEditDetail() })
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
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_signup_zipcode),
                        contentDescription = "",
                        tint = ZipdabangandroidTheme.Colors.Strawberry,
                        modifier = Modifier
                            .size(22.dp, 22.dp)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                    )
                    Text(
                        modifier = Modifier.padding(8.dp, 0.dp,0.dp,0.dp),
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
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_signup_address),
                        contentDescription = "",
                        tint = ZipdabangandroidTheme.Colors.Strawberry,
                        modifier = Modifier
                            .size(22.dp, 24.dp)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                    )
                    Text(
                        modifier = Modifier.padding(8.dp, 0.dp,0.dp,0.dp),
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
                ){
                    Spacer(modifier = Modifier
                        .size(24.dp, 24.dp)
                        .padding(8.dp, 0.dp, 0.dp, 0.dp))
                    Text(
                        modifier = Modifier.padding(8.dp, 0.dp,0.dp,0.dp),
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
            ){
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
                ){
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
                            .size(16.dp, 16.dp)
                            .clickable(onClick = { onClickEditNickname() })
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
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_my_smileface),
                        contentDescription = "",
                        tint = ZipdabangandroidTheme.Colors.Strawberry,
                        modifier = Modifier
                            .size(22.dp, 22.dp)
                            .padding(8.dp, 0.dp, 0.dp, 0.dp)
                    )
                    Text(
                        modifier = Modifier.padding(8.dp, 0.dp,0.dp,0.dp),
                        text = stateUserInfo.nickname,
                        style = ZipdabangandroidTheme.Typography.sixteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }
            }

            // api 로딩
            if(stateUserInfo.error.isNotBlank()){
                androidx.compose.material.Text(
                    text = stateUserInfo.error,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            if(stateUserInfo.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            //로그아웃 | 탈퇴하기
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 48.dp, 16.dp, 40.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                ClickableText(
                    text = AnnotatedString(text = stringResource(id = R.string.my_logout)),
                    style =  ZipdabangandroidTheme.Typography.fourteen_300,
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            //tokenStoreViewModel.resetToken()
                            Log.e("signup-tokens","로그아웃 클릭, postJob 실행 중")
                            onClickLogout()
                            Log.e("signup-tokens","로그아웃 클릭, onClick 실행 끝")
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
                    style =  ZipdabangandroidTheme.Typography.fourteen_300,
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
        onClickBack ={},
        onClickEdit = {},
        onClickEditBasic = {},
        onClickEditDetail = {},
        onClickEditNickname = {},
        onClickLogout = {},
        onClickWithdraw = {}
    )
}