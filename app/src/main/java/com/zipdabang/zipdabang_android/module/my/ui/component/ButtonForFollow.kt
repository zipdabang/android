package com.zipdabang.zipdabang_android.module.my.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

@Composable
fun ButtonForFollow(
    text : String,
    onClick : ()->Unit,
    isFollow : Boolean,
    isFollowing : Boolean
){
    Button(
        modifier = Modifier.fillMaxSize()
            .background(Color.Transparent),
        shape  = ZipdabangandroidTheme.Shapes.medium,
        elevation = null,
        onClick = { onClick() },
        colors = if(isFollow) ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.White)
                 else ButtonDefaults.buttonColors(containerColor = Color.White) ,
        border = BorderStroke(1.dp, Color.White)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (!isFollow) {
                    if (isFollowing) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.my_follow_each
                            ),
                            tint = ZipdabangandroidTheme.Colors.Typo,
                            contentDescription = null
                        )

                    } else {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.my_follow_small
                            ),
                            tint = ZipdabangandroidTheme.Colors.Typo,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                }

                Text(
                    text = text,
                    color = if (isFollow) Color.White else ZipdabangandroidTheme.Colors.Typo,
                    style = ZipdabangandroidTheme.Typography.fourteen_500
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewButton(){
    Column(
        modifier=
        Modifier
            .background(Color.DarkGray)
            .padding(30.dp)
         

    ) {
        ButtonForFollow(
            text = "맞팔로우 하기",
            onClick = { /*TODO*/ },
            isFollow = false,
            isFollowing = true
        )
        ButtonForFollow(
            text = "팔로우 하기",
            onClick = { /*TODO*/ },
            isFollow = false,
            isFollowing = false
        )
        ButtonForFollow(
            text = "언팔로우 하기",
            onClick = { /*TODO*/ },
            isFollow = true,
            isFollowing = true
        )
    }
}