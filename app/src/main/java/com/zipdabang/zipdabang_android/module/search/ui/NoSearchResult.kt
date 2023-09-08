package com.zipdabang.zipdabang_android.module.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.ui.component.RectangleWithRadiusText
import com.zipdabang.zipdabang_android.ui.theme.NavBlack

@Composable
fun NoSearchResult(){
    Box(
        modifier = Modifier.height(60.dp)
    ){
      RectangleWithRadiusText(
          text = "검색 결과가 없습니다.",
          backgroundcolor = Color(0xFFEDE9E3) ,
          fontColor = NavBlack,
          fontSize =14.sp
      )
    }
}