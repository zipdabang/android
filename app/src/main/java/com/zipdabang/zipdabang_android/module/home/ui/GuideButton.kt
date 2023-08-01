package com.zipdabang.zipdabang_android.module.home.ui

import android.content.res.Resources.Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun GuideButton(
    drawable: Int,
    drawableDesc: String,
    text: String
) {
    Row {
        Image(
            modifier = Modifier
                .size(48.dp),
            painter = painterResource(id = drawable),
            contentDescription = drawableDesc
        )
        Spacer(modifier = Modifier
            .width(8.dp))
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
            fontSize = 14.sp,
            fontWeight = FontWeight(300)
        )
    }
}

@Preview
@Composable
fun GuideButtonPreview() {
    GuideButton(drawable = R.drawable.zipdabanglogo_brown, drawableDesc = "logo", text = "집다방이 처음이신가요?")
}