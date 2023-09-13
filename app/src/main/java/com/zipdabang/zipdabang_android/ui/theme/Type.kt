package com.zipdabang.zipdabang_android.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R

private val Kopubworld = FontFamily(
    Font(R.font.kopubworlddotum_bold, FontWeight.Bold), //700
    Font(R.font.kopubworlddotum_medium, FontWeight.Medium), //500
    Font(R.font.kopubworlddotum_light, FontWeight.Light), //300
)

private val cafe24 = FontFamily(
    Font(R.font.cafe24ssurroundair, FontWeight.Light), //300
)

private val SCoreDream = FontFamily(
    Font(R.font.scdream_bold, FontWeight.Bold),
    Font(R.font.scdream_normal, FontWeight.Normal)
)

@Suppress("DEPRECATION")
val defaultTextStyle = TextStyle(
    platformStyle = PlatformTextStyle( //플랫폼 텍스트 스타일
        includeFontPadding = false   //텍스트 주위의 폰트 padding을 제거
    ),
    lineHeightStyle = LineHeightStyle( //줄 간격 스타일
        alignment = LineHeightStyle.Alignment.Center, //텍스트의 줄 간격을 수직 중앙에 맞게 설정
        trim = LineHeightStyle.Trim.None //텍스트 주위의 여백을 제거하지 않고 유지하도록 설정
    )
)

/*
 FontWeight.Light (값:300)
 FontWeight.Normal (값:400)
 FontWeight.Medium (값:500)
 FontWeight.Bold (값:700)
 FontWeight.ExtraBold (값:800)
 */
@OptIn(ExperimentalTextApi::class)
data class ZipdabangTypography (
    //로고!!!
    val logo : TextStyle = defaultTextStyle.copy(
        fontFamily = cafe24,
        fontWeight = FontWeight.Light,
        fontSize = 60.sp,
        //lineHeight = 60.sp,
        letterSpacing = -1.7.sp,
    ),
    //로고 밑에 글씨!!!
    val underLogo : TextStyle = defaultTextStyle.copy(
        fontFamily = cafe24,
        fontWeight = FontWeight.Light,
        fontSize = 18.sp,
        letterSpacing = -1.7.sp,
    ),
    //top bar 로고!!!
    val topbarLogo : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp,
        letterSpacing = -1.7.sp,
    ),
    //signupInfo
    val sixteen_300_cafe24 : TextStyle = defaultTextStyle.copy(
        fontFamily = cafe24,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        letterSpacing = -1.7.sp,
    ),
    //button!!!
    val button : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = -1.7.sp,
    ),

    val twentysix_700 : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        letterSpacing = -1.7.sp,
    ),
    val twentysix_500 : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Medium,
        fontSize = 26.sp,
        letterSpacing = -1.7.sp,
    ),

    val twentytwo_700 : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        letterSpacing = -1.7.sp,
    ),

    val eighteen_700 : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        letterSpacing = -1.7.sp,
    ),
    val eighteen_500 : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        letterSpacing = -1.7.sp,
    ),
    val eighteen_300 : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Light,
        fontSize = 18.sp,
        letterSpacing = -1.7.sp,
    ),

    val sixteen_700 : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        letterSpacing = -1.7.sp,
    ),
    val sixteen_500 : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        letterSpacing = -1.7.sp,
    ),
    val sixteen_300 : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        letterSpacing = -1.7.sp,
    ),
    val sixteen_300_noSpacing : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
    ),

    val fourteen_700 : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = -1.7.sp,
    ),
    val fourteen_500 : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = -1.7.sp,
    ),
    val fourteen_300 : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        letterSpacing = -1.7.sp,
    ),

    val twelve_500 : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = -1.0.sp,
    ),
    val twelve_300 : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        letterSpacing = -1.0.sp,
    ),

    val ten_300 : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Light,
        fontSize = 10.sp,
        letterSpacing = 0.sp,
    ),

    val eight_300 : TextStyle = defaultTextStyle.copy(
        fontFamily = Kopubworld,
        fontWeight = FontWeight.Light,
        fontSize = 8.sp,
        letterSpacing = 0.sp,
    ),



    val thirtytwo_900_scdream : TextStyle = defaultTextStyle.copy(
        fontFamily = SCoreDream,
        fontWeight = FontWeight.W900,
        fontSize = 32.sp,
        letterSpacing = -2.sp,
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFD4B2A7) ,
                Color(0xFF867768)
            ),
            start = Offset(0f, Float.POSITIVE_INFINITY),
            end = Offset(Float.POSITIVE_INFINITY, 0f),
        )
    ),

    val twentyfour_900_scdream : TextStyle = defaultTextStyle.copy(
        fontFamily = SCoreDream,
        fontWeight = FontWeight.W900,
        fontSize = 24.sp,
        letterSpacing = -2.sp,
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFD4B2A7) ,
                Color(0xFF867768)
            ),
            start = Offset(0f, Float.POSITIVE_INFINITY),
            end = Offset(Float.POSITIVE_INFINITY, 0f),
        )
    ),
)


/*
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)*/
