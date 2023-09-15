package com.zipdabang.zipdabang_android.ui.component

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.DialogPink
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun PrimaryButton(
    backgroundColor: Color,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = ZipdabangandroidTheme.Shapes.thin,
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        enabled = true
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = ZipdabangandroidTheme.Colors.Typo,
                maxLines = 1,
                modifier = Modifier,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium))
            )
        }
    }
}

@Composable
fun PrimaryButtonOutLined(
    borderColor: Color,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = ZipdabangandroidTheme.Shapes.thin,
        modifier = Modifier
            .fillMaxWidth(),
        enabled = true,
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = ZipdabangandroidTheme.Colors.Typo,
                maxLines = 1,
                modifier = Modifier,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium))
            )
        }
    }
}

@Composable
fun PrimaryButtonStrawberry(
    text: String,
    onClick: () -> Unit
) {
    PrimaryButton(
        backgroundColor = ZipdabangandroidTheme.Colors.Strawberry,
        text = text,
        onClick = onClick
    )
}


@Composable
fun PrimaryButtonStrawBerry80(
    text: String,
    onClick: () -> Unit
) {
    PrimaryButton(
        backgroundColor = DialogPink,
        text = text,
        onClick = onClick
    )
}


@Composable
fun PrimaryButtonMainBG(
    text: String,
    onClick: () -> Unit
) {
    PrimaryButton(
        backgroundColor = ZipdabangandroidTheme.Colors.MainBackground,
        text = text,
        onClick = onClick
    )
}


@Composable
fun PrimaryButtonStrawberryOutlined(
    text: String,
    onClick: () -> Unit
) {
    PrimaryButtonOutLined(
        borderColor = ZipdabangandroidTheme.Colors.Strawberry,
        text = text,
        onClick = onClick
    )
}

@Composable
fun PrimaryButtonStrawberry80Outlined(
    text: String,
    onClick: () -> Unit
) {
    PrimaryButtonOutLined(
        borderColor= DialogPink,
        text = text,
        onClick = onClick
    )
}


@Composable
fun PrimaryButtonMainBGOutlined(
    text: String,
    onClick: () -> Unit
) {
    PrimaryButtonOutLined(
        borderColor = ZipdabangandroidTheme.Colors.MainBackground,
        text = text,
        onClick = onClick
    )
}



@Composable
fun PrimaryButtonWithStatus(
    text: String,
    onClick: () -> Unit,
    isFormFilled: Boolean = false
) {
    val colors = ZipdabangandroidTheme.Colors

    Button(
        onClick = onClick,
        shape = ZipdabangandroidTheme.Shapes.thin,
        enabled = isFormFilled,
        modifier = Modifier
            .fillMaxWidth(),
        border = BorderStroke(1.dp, colors.Strawberry),
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.Strawberry,
            disabledContainerColor = Color.White
        ),
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = colors.Typo,
                maxLines = 1,
                modifier = Modifier,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium))
            )
        }

    }
}

@Composable
fun LoginButton(
    icon: Int,
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color
) {
    Button(
        onClick = onClick,
        shape = ZipdabangandroidTheme.Shapes.thin,
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        enabled = true,
        contentPadding = PaddingValues(
            horizontal = 20.dp,
            vertical = 8.dp
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {

            Image(
                painter = painterResource(id = icon),
                contentDescription = "login_icon",
                modifier = Modifier
            )

            Text(
                text = text,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = ZipdabangandroidTheme.Colors.Typo,
                maxLines = 1,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium))
            )

        }
    }
}

@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier,
    isScrolled: Boolean,
    icon: Int,
    title: String,
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .padding(bottom = 16.dp),
        shape = RoundedCornerShape(percent = 50),
        text = {
            Text(
                text = title,
                color = Color(0xFF262D31),
                fontFamily = FontFamily(Font(R.font.cafe24ssurroundair)),
                fontSize = 16.sp
            )
        },
        icon = {
            Image(
                modifier = Modifier,
                painter = painterResource(id = icon),
                contentDescription = "floating icon"
            )
        },
        expanded = !isScrolled,
        contentColor = Color.Black,
        containerColor = Color.White
    )
}


@Preview(showBackground = true)
@Composable
fun PrimaryButtonPreview() {
    Column(
        modifier = Modifier.padding(12.dp)
    ) {
        PrimaryButtonWithStatus(text = "작성 완료", onClick = {}, isFormFilled = true)
        PrimaryButtonWithStatus(text = "작성 완료", onClick = {})
        LoginButton(
            icon = R.drawable.login_kakao,
            text = "카카오로 시작하기",
            onClick = {},
            backgroundColor = Color.Yellow
        )
        LoginButton(
            icon = R.drawable.login_google,
            text = "구글로 시작하기",
            onClick = {},
            backgroundColor = Color.White
        )
        PrimaryButtonStrawberry(text = "배송지 추가", onClick = {})
        PrimaryButtonStrawberryOutlined(text = "배송지 추가", onClick = {})
        PrimaryButtonMainBG(text = "배송조회", onClick = {})
        PrimaryButtonMainBGOutlined(text = "주문·배송 취소", onClick = {})
        PrimaryButtonStrawBerry80(text = "임시저장 하기", onClick = {})
        PrimaryButtonStrawberry80Outlined(text = "취소", onClick = {})
    }
}

@Preview
@Composable
fun FloatingActionButtonPreview() {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        FloatingActionButton(isScrolled = true, icon = R.drawable.zipdabanglogo_white, title = "레시피 공유하기") {

        }
        Spacer(modifier = Modifier.height(16.dp))
        FloatingActionButton(isScrolled = false, icon = R.drawable.zipdabanglogo_white, title = "레시피 공유하기") {

        }
    }

}