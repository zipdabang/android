package com.zipdabang.zipdabang_android.module.drawer.ui.report

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.PrimaryButton
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonOutLined
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun ReportSuccessScreen(
    isGotoNewReport : ()-> Unit,
    isDone : () -> Unit
){
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = {  },
                centerText = "오류문의 및 신고"
            )
        }
    ) {
       Box(
            modifier = Modifier
                .padding(top = it.calculateTopPadding(),bottom = 40.dp)
                .fillMaxSize()
        ) {
           Text(text = "정상적으로 문의가 접수되었습니다.\n" + "더 나은 집다방이 되기 위해 노력하겠습니다.\n" + "감사합니다",
               style = ZipdabangandroidTheme.Typography.sixteen_500,color = ZipdabangandroidTheme.Colors.Typo,
               textAlign = TextAlign.Center,
               modifier = Modifier.align(Alignment.Center)
           )

           Row(modifier = Modifier
               .fillMaxWidth()
               .height(36.dp)
               .padding(horizontal = 16.dp)
               .align(Alignment.BottomCenter),
               horizontalArrangement = Arrangement.SpaceEvenly) {
               Box(modifier = Modifier.weight(1f)) {
                   PrimaryButtonOutLined(
                       borderColor = ZipdabangandroidTheme.Colors.Strawberry,
                       text = "새로 작성하기"
                   ) {
                       isGotoNewReport()
                   }
               }
               Spacer(modifier = Modifier.width(10.dp))
               Box(modifier = Modifier.weight(1f)) {
                   PrimaryButton(
                       backgroundColor = ZipdabangandroidTheme.Colors.Strawberry,
                       text = "완료하기"
                   ) {
                       isDone()
                   }
               }
           }

        }
        
    }
    
    
}

@Preview
@Composable
fun ErrorSuccessScreenPreview(){
  //  ReportSuccessScreen()
}