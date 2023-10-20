package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun Notice(
    contentText: String,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    // 어둡게 처리
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = contentText,
                color = Color.White,
                style = ZipdabangandroidTheme
                    .Typography.eighteen_700.copy(letterSpacing = 0.sp),
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 20.dp)
            )
            Button(
                onClick = { onButtonClick() },
                shape = ZipdabangandroidTheme.Shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ZipdabangandroidTheme.Colors.Strawberry,
                ),
                enabled = true
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = buttonText,
                        textAlign = TextAlign.Center,
                        style = ZipdabangandroidTheme
                            .Typography.eighteen_700.copy(letterSpacing = 0.sp),
                        color = Color.White,
                        maxLines = 1,
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun NoticePreview() {
    Notice(
        contentText = "차단한 회원의 프로필입니다.\n" +
            "보시려면 차단을 해제해주세요.",
        buttonText = "차단 해제하기"
    ) {

    }
}