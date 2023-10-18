package com.zipdabang.zipdabang_android.module.drawer.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Colors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme.Colors


@Composable
fun NoticeItem(
    title : String,
    createdAt : String,
    isNotice : Boolean
) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(64.dp)
        .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically){

        if(isNotice)Icon(painter = painterResource(id = R.drawable.drawer_notice_small),
             contentDescription = null,
             modifier = Modifier
                 .background(
                     color = Color(0xFFCDC6C3),
                     shape = CircleShape
                 )
                 .size(40.dp),
            tint = Color.White
            )else{
            Icon(painter = painterResource(id = R.drawable.drawer_event_small),
                contentDescription = null,
                modifier = Modifier
                    .background(
                        color = Colors.Strawberry,
                        shape = CircleShape
                    )
                    .size(40.dp),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(text = createdAt,
                style = ZipdabangandroidTheme.Typography.fourteen_300,
                color = Color(0x80262D31)
            )

            Text(text= title,
                style= ZipdabangandroidTheme.Typography.sixteen_500,
                color = ZipdabangandroidTheme.Colors.Typo
            )
        }

//        Text(text= status,
//            style = ZipdabangandroidTheme.Typography.eighteen_500,
//            color = ZipdabangandroidTheme.Colors.Typo,
//            modifier = Modifier.border(1.dp,Color.Black,ZipdabangandroidTheme.Shapes.large)
//                .padding(horizontal = 12.dp, vertical = 5.dp)
//        )



    }
}
@Preview
@Composable
fun PreviewNotice(){
    NoticeItem(
        title = "공지사항",
        createdAt = "2023.11.04",
        isNotice = true
    )
}
