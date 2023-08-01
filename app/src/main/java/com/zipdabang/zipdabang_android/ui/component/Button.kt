package com.zipdabang.zipdabang_android.ui.component

import android.graphics.drawable.Drawable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
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
    imageVector: ImageVector,
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

            Icon(
                imageVector = imageVector,
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


@Preview(showBackground = true)
@Composable
fun PrimaryButtonPreview() {
    Column(
        modifier = Modifier.padding(12.dp)
    ) {
        PrimaryButtonWithStatus(text = "작성 완료", onClick = {}, isFormFilled = true)
        PrimaryButtonWithStatus(text = "작성 완료", onClick = {})
        LoginButton(
            imageVector = Icons.Default.AccountCircle,
            text = "카카오로 시작하기",
            onClick = {},
            backgroundColor = Color.Yellow
        )
        LoginButton(
            imageVector = Icons.Default.AccountCircle,
            text = "구글로 시작하기",
            onClick = {},
            backgroundColor = Color.White
        )
        PrimaryButtonStrawberry(text = "배송지 추가", onClick = {})
        PrimaryButtonStrawberryOutlined(text = "배송지 추가", onClick = {})
        PrimaryButtonMainBG(text = "배송조회", onClick = {})
        PrimaryButtonMainBGOutlined(text = "주문·배송 취소", onClick = {})
    }
}