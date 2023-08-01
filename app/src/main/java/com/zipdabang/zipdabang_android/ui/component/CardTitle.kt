package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun CardTitleDefault(title: String, subTitle: String) {
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
            lineHeight = 12.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = subTitle,
            color = color,
            fontFamily = typography,
            fontSize = 8.sp,
            lineHeight = 8.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .offset(y = (-2).dp)
        )
    }
}

@Composable
fun CardTitleReversed(title: String, subTitle: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        val color = ZipdabangandroidTheme.Colors.Typo
        val typography = FontFamily(Font(R.font.kopubworlddotum_medium))
        Text(
            text = subTitle,
            color = color,
            fontFamily = typography,
            fontSize = 8.sp,
            lineHeight = 8.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = title,
            color = color,
            fontFamily = typography,
            fontSize = 12.sp,
            lineHeight = 12.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .offset(y = (-2).dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CardTitlePreview() {
    CardTitleDefault(title = "상품 제목", subTitle = "가격")
}

@Preview(showBackground = true)
@Composable
fun CardTitleReversedPreview() {
    CardTitleReversed(title = "가격", subTitle = "상품제목")
}