package com.zipdabang.zipdabang_android.module.my.ui.component

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.CircleImage
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun FollowItem(
    imageUrl : String,
    nickName : String,
    isFollow : Boolean,
    isFollowEach : Boolean = false
){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)){
        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Box(modifier = Modifier.size(40.dp)){
                CircleImage(imageUrl = "https://mblogthumb-phinf.pstatic.net/MjAyMDExMDFfMTgy/MDAxNjA0MjI4ODc1NDMw.Ex906Mv9nnPEZGCh4SREknadZvzMO8LyDzGOHMKPdwAg.ZAmE6pU5lhEdeOUsPdxg8-gOuZrq_ipJ5VhqaViubI4g.JPEG.gambasg/%EC%9C%A0%ED%8A%9C%EB%B8%8C_%EA%B8%B0%EB%B3%B8%ED%94%84%EB%A1%9C%ED%95%84_%ED%95%98%EB%8A%98%EC%83%89.jpg?type=w800", contentDescription = "FriendItem Image")
            }
            Spacer(modifier =Modifier.width(16.dp))
            Text(text= nickName, style = ZipdabangandroidTheme.Typography.sixteen_500,color = ZipdabangandroidTheme.Colors.Typo)
        }

        Icon(painter = painterResource(id = R.drawable.my_followspot_small),contentDescription = null,tint =ZipdabangandroidTheme.Colors.Typo,
            modifier = Modifier.align(Alignment.CenterEnd)
        )




    }
}
