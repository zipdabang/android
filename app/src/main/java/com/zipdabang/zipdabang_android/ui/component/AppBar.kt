package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    startIcon: ImageVector?,
    endIcon: ImageVector?,
    onClickStartIcon: () -> Unit,
    onClickEndIcon: () -> Unit,
    centerText: String
) {
    TopAppBar(
        navigationIcon = {
            startIcon?.let {
                IconButton(onClick = { onClickStartIcon() }) {
                    Icon(imageVector = it, contentDescription = "", modifier = Modifier.fillMaxSize())
                }

            }
        },
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = centerText,
                    fontSize = 30.sp,
                    fontFamily = FontFamily(Font(R.font.cafe24ssurroundair)),
                    color = Color(0xFFA38F85),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 2.dp),
                    textAlign = TextAlign.Center
                )
            }
        },
        actions = {
            endIcon?.let {
                IconButton(onClick = { onClickEndIcon() }) {
                    Icon(
                        imageVector = endIcon,
                        contentDescription = "menu",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

        }
    )
}

@Preview
@Composable
fun AppBarPreview() {
    AppBar(
        startIcon = Icons.Default.KeyboardArrowLeft,
        endIcon = Icons.Default.Menu,
        onClickStartIcon = {},
        onClickEndIcon = {},
        centerText = "집다방"
    )
}