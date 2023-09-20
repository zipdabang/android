package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme


//10번 -> 완성
@Composable
fun ImageWithIcon(
    imageUrl : Any,
    onClick : () -> Unit
){
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .border(
                BorderStroke(1.dp, ZipdabangandroidTheme.Colors.Typo.copy(0.2f)),
                RoundedCornerShape(4.dp)
            )
            .background(color = Color(0xFFF7F6F6))
            .clip(RoundedCornerShape(4.dp)), //이미지 테두리 적용
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .background(color = Color(0xFFF7F6F6)),
            contentScale = ContentScale.Crop,
        )
        IconButton(
            onClick = {
                onClick()
            },
        ){
            Icon(
                painter = painterResource(id = R.drawable.ic_my_delete_whiterounded),
                contentDescription = "Icon",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
                    .border(BorderStroke(0.dp, Color.Transparent), RoundedCornerShape(4.dp))
            )
        }
    }
}

@Preview
@Composable
fun PreviewImageWithIcon(){
    //Step1 버튼을 누르면 이미지를 받아온다. 받아온 이미지를 imageUrl에 넣는다.
    var imageUrl = R.drawable.zipdabanglogo_white

    //imageUrl을 imageState에 저장한다.
    var imageState by remember { mutableStateOf(imageUrl) }

    Box(
        modifier = Modifier.size(100.dp)
            .padding(16.dp)
    ){
        ImageWithIcon(
            imageUrl = imageState,
            onClick = { /* 카메라 접근 허용 후, 이미지를 받아와서 이 이미지를 imageState에 넣는다. */
                imageState = R.drawable.ic_launcher_background
                //imageState 변수가 변경되면 Compose는 다시 레이아웃을 계산하고 UI를 업데이트 한다.
            }
        )
    }
}






//10번 -> 완성, icon size 설정 필요.
@Composable
fun ImageWithIconAndText(
    addImageClick : () -> Unit, //image add할때 클릭이벤트
    deleteImageClick : () -> Unit, //image delete할때 클릭이벤트
    imageUrl : String,
    iconImageVector : Int,
    iconTint : Color,
    iconModifier : Modifier,
    text : String,
    textStyle : TextStyle,
    textColor : Color,
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .border(BorderStroke(1.dp, ZipdabangandroidTheme.Colors.Typo.copy(0.2f)))
            .background(color = Color(0xFFF7F6F6)),
        contentAlignment = Alignment.Center,
    ){
        if (imageUrl != ""){
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .border(BorderStroke(1.dp, ZipdabangandroidTheme.Colors.Typo.copy(0.2f)))
                    .background(color = Color(0xFFF7F6F6)),
                contentScale = ContentScale.Crop,
            )
            IconButton(
                onClick = {
                    deleteImageClick()
                },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_all_delete_white),
                    contentDescription = "Icon",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp).padding(0.dp)
                )
            }
        } else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable{
                    addImageClick()
                }
            ){
                Icon(
                    painter = painterResource(id = iconImageVector),
                    contentDescription = "Icon",
                    tint = iconTint, //ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
                    modifier = iconModifier
                )
                Text(
                    modifier = Modifier.padding(0.dp,4.dp, 0.dp,0.dp),
                    text = text,
                    style = textStyle,
                    color = textColor,
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewImageWithIconAndText(){
    var imageState by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.size(104.dp)
    ){
        ImageWithIconAndText(
            imageUrl = imageState,
            addImageClick={/* 카메라 접근해서 이미지 불러오기 */
                //imageState =
            },
            deleteImageClick={/* 이미지 삭제하기 */
                //imageState =
            },
            iconImageVector = R.drawable.ic_topbar_backbtn,
            iconTint = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
            iconModifier = Modifier.size(30.dp),
            text = "파일 첨부",
            textStyle = ZipdabangandroidTheme.Typography.sixteen_500,
            textColor = ZipdabangandroidTheme.Colors.Typo.copy(0.5f)
        )
    }
}