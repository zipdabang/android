package com.zipdabang.zipdabang_android.module.drawer.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme


@Composable
fun ReportItem(
    title : String,
    createdAt : String,
    onClick : () -> Unit
) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(64.dp)
        .clickable {
                   onClick()
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){

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