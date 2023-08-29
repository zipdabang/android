package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RecipeTip(
    recipeTip: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "레시피 Tip!",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                color = ZipdabangandroidTheme.Colors.Choco,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = recipeTip,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                color = ZipdabangandroidTheme.Colors.Typo,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeTipPreview() {
    RecipeTip(recipeTip = "마시멜로를 전자레인지에 30초 정도 돌리면 부풀어서 커질 거예요. " +
            "다시 작아지기 전에 빠르게 꺼내어 초코라떼의 휘핑에 올려주면 " +
            "사진 찍어 자랑하고 싶은 귀여운 초코라떼가 탄생할 거예요 ㅎㅎ")
}