package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RectangleWithRadiusText(
    text :  String,
    backgroundcolor : Color,
    fontColor : Color,
    fontSize : TextUnit,
){

   Surface(
       color = backgroundcolor,
       shape = ZipdabangandroidTheme.Shapes.small,
       modifier = Modifier.fillMaxSize()
   ){
        Text(
            text = text,
            fontSize = fontSize,
            color = fontColor,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier
                .padding(vertical= 10.dp),
        )
    }
}


@Preview
@Composable
fun RectangleTextPreview(){

    Box(
        modifier = Modifier.padding( horizontal= 10.dp),
        contentAlignment= Alignment.TopCenter
    ) {
        RectangleWithRadiusText(
            text = "주문 내용을 확인하였으며, 정보제공에 동의합니다.",
            backgroundcolor = Color(0xFFD9D9D9),
            fontColor = Color(0xFF262D31),
            14.sp,
        )
    }
}