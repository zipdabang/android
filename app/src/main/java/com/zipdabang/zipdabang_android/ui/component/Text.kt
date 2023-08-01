package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme


//17번 -> 완성
@Composable
fun MainAndSubTitle(
    mainValue : String,
    mainTextStyle : TextStyle,
    mainTextColor : Color,
    subValue : String,
    subTextStyle: TextStyle,
    subTextColor : Color,
){
    Column(){
        Text(
            text= mainValue,
            style = mainTextStyle,
            color = mainTextColor,
        )
        Text(
            text= subValue,
            style = subTextStyle,
            color = subTextColor,
        )
    }
}
@Preview
@Composable
fun PreviewMainAndSubTitle(){
    MainAndSubTitle(
        mainValue = "집다방 서비스 이용 약관",
        mainTextStyle = ZipdabangandroidTheme.Typography.twentytwo_700,
        mainTextColor = ZipdabangandroidTheme.Colors.Typo,
        subValue = "서비스 이용 약관을 확인해주세요",
        subTextStyle= ZipdabangandroidTheme.Typography.sixteen_500,
        subTextColor = ZipdabangandroidTheme.Colors.Typo,
    )
}
