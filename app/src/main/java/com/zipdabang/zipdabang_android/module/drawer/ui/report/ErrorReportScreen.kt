package com.zipdabang.zipdabang_android.module.drawer.ui.report

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.TextFieldForDrawerMultiline
import com.zipdabang.zipdabang_android.ui.component.TextFieldForDrawerSingleline
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun ErrorReportScreen(){

    val scrollState = rememberScrollState()
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
    Column(
        modifier = Modifier
            .padding(top = it.calculateTopPadding(), start = 16.dp, end=16.dp)
            .verticalScroll(scrollState)
    ) {

        Text(
            text = "오류문의 및 신고",
            style = ZipdabangandroidTheme.Typography.twentytwo_700,
            color = ZipdabangandroidTheme.Colors.Typo
        )

        Text(
            text = "이름",
            style = ZipdabangandroidTheme.Typography.fourteen_500,
            color = ZipdabangandroidTheme.Colors.Typo,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        var emailText by remember{
            mutableStateOf("")
        }
        var titleText by remember{
            mutableStateOf("")
        }
        var contentText by remember{
            mutableStateOf("")
        }
        var isFormFilled by remember {
            mutableStateOf(false)
        }
        var isError by remember {
            mutableStateOf(false)
        }

        Text(
            text = "답변 받을 이메일",
            style = ZipdabangandroidTheme.Typography.sixteen_700,
            color = ZipdabangandroidTheme.Colors.Typo
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)) {


            TextFieldForDrawerSingleline(
                value = emailText,
                onValueChanged = { it, maxlength ->
                    emailText = it
                    isError = if(it=="") false
                    else !Patterns.EMAIL_ADDRESS.matcher(it).matches()
                },
                placeholderValue = "ex) zipdabang@gmail.com ",
                errorMessage = "이메일 형식이 맞지 않습니다.",
                maxLength = 30,
                isError = isError,
                imeAction = ImeAction.Default
            )
            if (emailText == "") {
                Text(
                    text = "ex) zipdabang@gmail.com",
                    style= ZipdabangandroidTheme.Typography.sixteen_300,
                    color = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 18.dp,top = 5.dp)
                )
            }
        }
        Text(
            text = "문의 및 신고 내용",
            style = ZipdabangandroidTheme.Typography.sixteen_700,
            color = ZipdabangandroidTheme.Colors.Typo
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
        ) {

            TextFieldForDrawerSingleline(
                value = titleText,
                onValueChanged = { it, maxlength ->
                    if (it.length <= maxlength) titleText = it
                },
                placeholderValue = "제목 (최대 20자) ",
                errorMessage = "",
                maxLength = 20,
                isError = false,
                imeAction = ImeAction.Default
            )
            if (titleText == "") {
            Text(
                text = "제목 (최대 20자)",
                style= ZipdabangandroidTheme.Typography.sixteen_300,
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                   .padding(start = 18.dp,top = 5.dp)
            )
        }
        }
        Spacer(modifier = Modifier.height(10.dp))

        TextFieldForDrawerMultiline(
            value = contentText,
            onValueChanged = {
                             it,maxlength ->
                if(it.length <= maxlength) contentText = it
            },
            placeholderValue = "내용 (최대 500자)" ,
            height = 500.dp ,
            maxLines = 40,
            maxLength = 500 ,
            isError = false,
            imeAction = ImeAction.Default
        )
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(104.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ){
            for(i in 1..3) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .background(color = Color(0XFFF7F6F6), shape = RectangleShape)
                ) {
                    Column(modifier = Modifier.align(Alignment.Center)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.drawer_camera_small),
                            tint = Color(0x1A262D31),
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = "파일 첨부",
                            style = ZipdabangandroidTheme.Typography.sixteen_500,
                            color = Color(0x1A262D31),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(40.dp))
        isFormFilled = emailText!="" && isError == false && titleText != "" && contentText != ""

        Box(modifier =Modifier.fillMaxWidth()
            .height(36.dp)) {
            PrimaryButtonWithStatus(
                text = "제출하기",
                onClick = { /*TODO*/ },
                isFormFilled = isFormFilled
            )
        }



    }




    }


}

@Composable
@Preview
fun ErrorReportPreviewScreen(){
    ErrorReportScreen()
}