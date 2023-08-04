package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
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
fun AppBarHome(
    endIcon1: ImageVector?,
    endIcon2: ImageVector?,
    onClickEndIcon1: () -> Unit,
    onClickEndIcon2: () -> Unit,
    centerText: String
) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 80.dp),
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
            endIcon1?.let {
                IconButton(onClick = { onClickEndIcon1() }) {
                    Icon(
                        imageVector = endIcon1,
                        contentDescription = "search",
                        modifier = Modifier
                            .padding(4.dp),
                    )
                }
            }

            endIcon2?.let {
                IconButton(onClick = { onClickEndIcon2() }) {
                    Icon(
                        imageVector = endIcon2,
                        contentDescription = "menu",
                        modifier = Modifier
                            .padding(4.dp)

                    )
                }
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarDefault(
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
                    Icon(imageVector = it, contentDescription = "", modifier = Modifier
                        .padding(4.dp))
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
                        modifier = Modifier
                            .padding(4.dp),
                    )
                }
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarSignUp(
    navigationIcon: ImageVector?,
    onClickNavIcon: () -> Unit,
    centerText: String
) {
    TopAppBar(
        navigationIcon = {
            navigationIcon?.let {
                IconButton(onClick = { onClickNavIcon() }) {
                    Icon(imageVector = it, contentDescription = "", modifier = Modifier
                        .padding(4.dp)
                    )
                }
            }
        },
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 48.dp),
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
        }
        
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarMy(
    endIcon: ImageVector?,
    onClickEndIcon: () -> Unit,
    centerText: String
) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp),
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
                        contentDescription = "search",
                        modifier = Modifier
                            .padding(4.dp),
                    )
                }
            }

        }
    )
}


@Preview
@Composable
fun AppBarPreview() {
    AppBarHome(
        endIcon1 = Icons.Default.Search,
        endIcon2 = Icons.Default.Menu,
        onClickEndIcon1 = {},
        onClickEndIcon2 = {},
        centerText = "집다방"
    )
}

@Preview
@Composable
fun AppBarDefaultPreview() {
    AppBarDefault(
        startIcon = Icons.Default.KeyboardArrowLeft,
        endIcon = Icons.Default.Menu,
        onClickStartIcon = {},
        onClickEndIcon = {},
        centerText = "집다방"
    )
}

@Preview
@Composable
fun AppBarSignUpPreview() {
    AppBarSignUp(
        navigationIcon = Icons.Default.KeyboardArrowLeft,
        onClickNavIcon = { /*TODO*/ },
        centerText = "회원가입"
    )
}

@Preview
@Composable
fun AppBarMyPreview() {
    AppBarMy(
        endIcon = Icons.Default.Menu,
        onClickEndIcon = { /*TODO*/ },
        centerText = "집다방"
    )
}