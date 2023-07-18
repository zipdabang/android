package com.zipdabang.zipdabang_android.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightThemeColors = ZipdabangColors(
    Typo = Typo,
    MainBackground = MainBackground,
    SubBackground = SubBackground,
    Choco = Choco,
    Latte = Latte,
    BlackSesame = BlackSesame,
    Strawberry = Strawberry,
    Cream = Cream,
    isLight = false,
)
private val DarkThemeColors = ZipdabangColors(
    Typo = Typo,
    MainBackground = MainBackground,
    SubBackground = SubBackground,
    Choco = Purple80,
    Latte = Latte,
    BlackSesame = BlackSesame,
    Strawberry = Pink80,
    Cream = Cream,
    isLight = false,
)


val LocalShapes = staticCompositionLocalOf { ZipdabangShapes() }
val LocalTypography = staticCompositionLocalOf { ZipdabangTypography() }
val LocalColors = staticCompositionLocalOf { LightThemeColors }

/*@Composable
fun ZipdabangandroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}*/
@Composable
fun ZipdabangandroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    //dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    //custom한 typograpy랑 shapes 정의
    val customTypography = ZipdabangTypography()
    val customShapes = ZipdabangShapes()

    //custom한 color는 remember하기
    val currentColor = remember {
        if (darkTheme)
            DarkThemeColors
        else
            LightThemeColors
    }
    //remember로 currentColor.copy()의 상태 저장
    val rememberedColors = remember{
        currentColor.copy()
    }
    //달라진 color를 update하기
    rememberedColors.update(currentColor)

    CompositionLocalProvider (
        LocalColors provides rememberedColors,
        LocalTypography provides customTypography,
        LocalShapes provides customShapes,
    ){
        content()
    }
}

object ZipdabangandroidTheme{
    val Colors: ZipdabangColors
        @Composable
        get() = LocalColors.current
    val Typography : ZipdabangTypography
        @Composable
        get() = LocalTypography.current
    val Shapes : ZipdabangShapes
        @Composable
        get() = LocalShapes.current
}

class ZipdabangColors(
    Typo : Color,
    MainBackground : Color,
    SubBackground : Color,
    Choco : Color,
    Latte : Color,
    BlackSesame : Color,
    Strawberry : Color,
    Cream : Color,
    isLight : Boolean,
){
    var Typo by mutableStateOf(Typo)
        private set
    var MainBackground by mutableStateOf(MainBackground)
        private set
    var SubBackground by mutableStateOf(SubBackground)
        private set
    var Choco by mutableStateOf(Choco)
        private set
    var Latte by mutableStateOf(Latte)
        private set
    var BlackSesame by mutableStateOf(BlackSesame)
        private set
    var Strawberry by mutableStateOf(Strawberry)
        private set
    var Cream by mutableStateOf(Cream)
        private set
    var isLight by mutableStateOf(isLight)
        private set

    fun copy() : ZipdabangColors = ZipdabangColors(
        Typo = Typo,
        MainBackground = MainBackground,
        SubBackground = SubBackground,
        Choco = Choco,
        Latte = Latte,
        BlackSesame = BlackSesame,
        Strawberry = Strawberry,
        Cream = Cream,
        isLight = false,
    )

    fun update(other : ZipdabangColors){
        Typo = other.Typo
        MainBackground = other.MainBackground
        SubBackground = other.SubBackground
        Choco = other.Choco
        Latte = other.Latte
        BlackSesame = other.BlackSesame
        Strawberry = other.Strawberry
        Cream = other.Cream
        isLight = false
    }
}