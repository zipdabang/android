package com.zipdabang.zipdabang_android.module.item.recipe.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun AllSubtitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 10.dp,
                bottom = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AnnotatedString(
                text = "전체 ",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Strawberry,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            ) + AnnotatedString(
                text = "레시피가 모여있어요!",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Choco,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            )
        )
    }
}

@Composable
fun CoffeeSubtitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 10.dp,
                bottom = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AnnotatedString(
                text = "달콤 쌉사름한 ",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Choco,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            ) + AnnotatedString(
                text = "커피 ",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Strawberry,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            )+ AnnotatedString(
                text = "한 잔 어때요?",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Choco,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            )
        )
    }
}

@Composable
fun NonCaffeineSubtitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 10.dp,
                bottom = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AnnotatedString(
                text = "카페인 시러시러 ",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Choco,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            ) + AnnotatedString(
                text = "논카페인",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Strawberry,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            )
        )
    }
}

@Composable
fun TeaSubtitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 10.dp,
                bottom = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AnnotatedString(
                text = "나를 감싸주는 따듯한 ",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Choco,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            ) + AnnotatedString(
                text = "차(Tea)",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Strawberry,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            )
        )
    }
}

@Composable
fun AdeSubtitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 10.dp,
                bottom = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AnnotatedString(
                text = "입 안에서 ",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Choco,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            ) + AnnotatedString(
                text = "에이드",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Strawberry,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            )+ AnnotatedString(
                text = "가 상큼하게 톡톡!",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Choco,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            )
        )
    }
}

@Composable
fun SmoothieSubtitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 10.dp,
                bottom = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AnnotatedString(
                text = "스무디",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Strawberry,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            )+ AnnotatedString(
                text = "가 주는 부드러운 행복감",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Choco,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            )
        )
    }
}

@Composable
fun FruitSubtitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 10.dp,
                bottom = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AnnotatedString(
                text = "자연미가 대폭발하는 ",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Choco,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            ) + AnnotatedString(
                text = "과일 음료",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Strawberry,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            )
        )
    }
}

@Composable
fun WellBeingSubtitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 10.dp,
                bottom = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AnnotatedString(
                text = "맛까지 챙기는 ",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Choco,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            ) + AnnotatedString(
                text = "건강 음료 ",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Strawberry,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            )+ AnnotatedString(
                text = "매력에 퐁당~",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Choco,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            )
        )
    }
}

@Composable
fun EveryoneSubtitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 10.dp,
                bottom = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AnnotatedString(
                text = "집다방",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Strawberry,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            ) + AnnotatedString(
                text = "의 레시피",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Choco,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            )
        )
    }
}

@Composable
fun InfluencerSubtitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 10.dp,
                bottom = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AnnotatedString(
                text = "인플루언서",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Strawberry,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            ) + AnnotatedString(
                text = "의 레시피",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Choco,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            )
        )
    }
}

@Composable
fun OurSubtitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 10.dp,
                bottom = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AnnotatedString(
                text = "우리들",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Strawberry,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            ) + AnnotatedString(
                text = "의 레시피",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Choco,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            )
        )
    }
}

@Composable
fun HotRecipeTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 20.dp,
                bottom = 10.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = AnnotatedString(
                text = "카테고리별 ",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Choco,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            ) + AnnotatedString(
                text = "인기 레시피",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Strawberry,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                    fontSize = 16.sp
                )
            )
        )
    }
}