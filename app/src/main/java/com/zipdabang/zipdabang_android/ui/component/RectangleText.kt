package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.NavBlack
import com.zipdabang.zipdabang_android.ui.theme.NoItem
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RectangleWithRadiusText(
    text :  String,
    backgroundcolor : Color,
    fontColor : Color,
    fontSize : TextUnit,
){

   Box(
       modifier = Modifier.fillMaxSize()
           .background(backgroundcolor,shape =ZipdabangandroidTheme.Shapes.medium )
   ){
        Text(
            text = text,
            fontSize = fontSize,
            color = fontColor,
            textAlign = TextAlign.Center,
            maxLines = 2,
            modifier = Modifier
                .padding(vertical= 10.dp, horizontal = 10.dp)
                .align(Alignment.Center)

        )
    }
}

@Composable
fun RectangleWithTwoLinesText(
    text1 :  String,
    text2 : String
){

    Box(
        modifier = Modifier
            .background(NoItem, shape = ZipdabangandroidTheme.Shapes.large)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterStart),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text1,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                fontWeight = FontWeight(700),
                color = NavBlack,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start=20.dp)
            )
            Text(
                text2,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                fontWeight = FontWeight(500),
                color = NavBlack,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start=20.dp)

            )
        }
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