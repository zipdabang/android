package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun CardTitle(title: String, subTitle: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        val color = ZipdabangandroidTheme.Colors.Typo
        val typography = FontFamily(Font(R.font.kopubworlddotum_medium))
        Text(
            text = title,
            color = color,
            fontFamily = typography,
            fontSize = 12.sp,
            lineHeight = 12.sp
        )
        Text(
            text = subTitle,
            color = color,
            fontFamily = typography,
            fontSize = 8.sp,
            lineHeight = 8.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CardTitlePreview() {
    CardTitle(title = "상품 제목", subTitle = "가격")
}