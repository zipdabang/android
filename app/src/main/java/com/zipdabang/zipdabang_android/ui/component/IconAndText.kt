package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

//18번 -> 완성, icon size 설정 필요.
@Composable
fun IconAndText(
    iconImageVector: Int,
    iconColor : Color,
    iconModifier : Modifier,
    text : String,
    textColor : Color,
    textStyle : TextStyle,
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Icon(
            painter = painterResource(id = iconImageVector),
            contentDescription = "Icon",
            tint = iconColor,
            modifier = iconModifier,
        )
        Text(
            text = text,
            color = textColor,
            style = textStyle,
            modifier = Modifier.padding(0.dp, 0.dp, 2.dp, 0.dp)
        )
    }
}

@Preview
@Composable
fun PreviewIconAndText(){
    Box(
        modifier = Modifier.size(100.dp)
    ){
        IconAndText(
            iconImageVector = R.drawable.zipdabanglogo_white,
            iconColor = Color.White,
            iconModifier = Modifier.size(50.dp, 50.dp),
            text= "나의 다방",
            textColor= ZipdabangandroidTheme.Colors.Typo,
            textStyle= ZipdabangandroidTheme.Typography.eighteen_300,)
    }
}