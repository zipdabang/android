package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme


//10번 추가 -> 완료
@Composable
fun ImageWithIcon(
    imageUrl : MutableState<Int>,
){
    //var imageUrl by remember { mutableStateOf(R.drawable.cat) }
    Box(
        modifier = Modifier
            .size(48.dp)
            .border(
                BorderStroke(1.dp, ZipdabangandroidTheme.Colors.Typo.copy(0.2f)),
                RoundedCornerShape(4.dp)
            )
            .background(color = Color(0xFFF7F6F6))
            .clip(RoundedCornerShape(4.dp)), //이미지 테두리 적용
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            model = imageUrl.value,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .background(color = Color(0xFFF7F6F6)),
            contentScale = ContentScale.Crop,
        )
        IconButton(
            onClick = {
                if (imageUrl.value != null) {
                    //사진 불러오기
                    imageUrl.value = 0
                }
            },
        ){
            Icon(
                painter = painterResource(id = R.drawable.imagewithicon_delete),contentDescription = "Icon",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
                    .border(BorderStroke(0.dp, Color.Transparent), RoundedCornerShape(4.dp))
            )
        }
    }
}


//10번 -> 완료
@Composable
fun ImageWithIconAndText(
    imageUrl : MutableState<Int>,
    icon : ImageVector,
    text : String,
){
    //var imageUrl by remember { mutableStateOf(R.drawable.cat) }
    Box(
        modifier = Modifier
            .size(104.dp)
            .border(BorderStroke(1.dp, ZipdabangandroidTheme.Colors.Typo.copy(0.2f)))
            .background(color = Color(0xFFF7F6F6))
            .clickable {
                //click했을때 일어날 일, 사진 추가하기
            },
        contentAlignment = Alignment.Center,
    ){
        if (imageUrl.value != 0){
            AsyncImage(
                model = imageUrl.value,
                contentDescription = null,
                modifier = Modifier
                    .size(104.dp)
                    .border(BorderStroke(1.dp, ZipdabangandroidTheme.Colors.Typo.copy(0.2f)))
                    .background(color = Color(0xFFF7F6F6)),
                contentScale = ContentScale.Crop,
            )
            IconButton(
                onClick = {
                    if (imageUrl.value != null) {
                        //사진 불러오기
                        imageUrl.value = 0
                    }
                },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    painter = painterResource(R.drawable.imagewithiconandtext_delete),
                    contentDescription = "Icon",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                        .padding(0.dp)
                )
            }
        } else {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon",
                    tint = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = text,
                    style = ZipdabangandroidTheme.Typography.sixteen_500,
                    color = ZipdabangandroidTheme.Colors.Typo.copy(0.5f)
                )
            }
        }
    }
}



@Preview
@Composable
fun PreviewImageWithIcon(){
    var textState = remember { mutableStateOf(R.drawable.ic_launcher_background) }
    ImageWithIcon(textState)
}

@Preview
@Composable
fun PreviewImageWithIconAndText(){
    var textState = remember { mutableStateOf(R.drawable.ic_launcher_background) }
    ImageWithIconAndText(textState, Icons.Default.Email, "파일 첨부")
}