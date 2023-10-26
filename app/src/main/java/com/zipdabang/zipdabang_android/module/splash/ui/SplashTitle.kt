package com.zipdabang.zipdabang_android.module.splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun SplashTitle(
    color: Color
) {

    val splashLogo = R.drawable.zipdabanglogo_transparent_normal

    Column(
        modifier = Modifier
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                modifier = Modifier
                    .size(60.dp),
                painter = painterResource(id = splashLogo),
                contentDescription = "splash",
            )
            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )
            Text(
                modifier = Modifier
                    .wrapContentHeight(),
                text = stringResource(R.string.zipdabang_title),
                fontSize = 60.sp,
                fontFamily = FontFamily(Font(R.font.cafe24ssurroundair)),
                color = color,
                textAlign = TextAlign.Center
            )
        }
        Text(
            text = stringResource(R.string.zipdabang_subtitle),
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.cafe24ssurroundair)),
            fontWeight = FontWeight(300),
            color = color
        )
    }
}

@Preview
@Composable
fun SplashTitlePreview() {
    SplashTitle(color = Color.White)
}