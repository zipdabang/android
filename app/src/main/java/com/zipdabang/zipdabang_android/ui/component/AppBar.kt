package com.zipdabang.zipdabang_android.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImage
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.detail.recipe.common.DeviceScreenSize
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarHome(
    endIcon1: Int?,
    endIcon2: Int?,
    onClickEndIcon1: () -> Unit,
    onClickEndIcon2: () -> Unit,
    centerText: String
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White //예은 - topAppBar containercolor 수정
        ),
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 80.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = centerText,
                    fontSize = 24.sp,
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
                        painter = painterResource(id = endIcon1),
                        contentDescription = "search",
                        modifier = Modifier
                            .padding(4.dp),
                        tint = ZipdabangandroidTheme.Colors.Choco
                    )
                }
            }

            endIcon2?.let {
                IconButton(onClick = { onClickEndIcon2() }) {
                    Icon(
                        painter = painterResource(id = endIcon2),
                        contentDescription = "menu",
                        modifier = Modifier
                            .padding(4.dp),
                        tint = ZipdabangandroidTheme.Colors.Choco
                    )
                }
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarWithFullFunction(
    startIcon: Int?,
    endIcon1: Int?,
    endIcon2: Int?,
    onClickStartIcon: () -> Unit,
    onClickEndIcon1: () -> Unit,
    onClickEndIcon2: () -> Unit,
    centerText: String
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White //예은 - topAppBar containercolor 수정
        ),
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = centerText,
                    fontSize = 24.sp,
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
        navigationIcon = {
            startIcon?.let {
                IconButton(onClick = { onClickStartIcon() }) {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(4.dp),
                        tint = ZipdabangandroidTheme.Colors.Choco
                    )
                }
            }
        },
        actions = {
            endIcon1?.let {
                IconButton(onClick = { onClickEndIcon1() }) {
                    Icon(
                        painter = painterResource(id = endIcon1),
                        contentDescription = "search",
                        modifier = Modifier
                            .padding(4.dp),
                        tint = ZipdabangandroidTheme.Colors.Choco
                    )
                }
            }

            endIcon2?.let {
                IconButton(onClick = { onClickEndIcon2() }) {
                    Icon(
                        painter = painterResource(id = endIcon2),
                        contentDescription = "menu",
                        modifier = Modifier
                            .padding(4.dp),
                        tint = ZipdabangandroidTheme.Colors.Choco
                    )
                }
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarDefault(
    startIcon: Int?,
    endIcon: Int?,
    onClickStartIcon: () -> Unit,
    onClickEndIcon: () -> Unit,
    centerText: String
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White //예은 - topAppBar containercolor 수정
        ),
        navigationIcon = {
            startIcon?.let {
                IconButton(onClick = { onClickStartIcon() }) {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(4.dp),
                        tint = ZipdabangandroidTheme.Colors.Choco
                    )
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
                    fontSize = 24.sp,
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
                        painter = painterResource(id = endIcon),
                        contentDescription = "menu",
                        modifier = Modifier
                            .padding(4.dp),
                        tint = ZipdabangandroidTheme.Colors.Choco
                    )
                }
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarSignUp(
    navigationIcon: Int?,
    onClickNavIcon: () -> Unit,
    centerText: String
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White //예은 - topAppBar containercolor 수정
        ),
        navigationIcon = {
            navigationIcon?.let {
                IconButton(onClick = { onClickNavIcon() }) {
                    Icon(painter = painterResource(id = it), contentDescription = "", modifier = Modifier
                        .padding(4.dp),
                        tint = ZipdabangandroidTheme.Colors.Choco
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
                    fontSize = 24.sp,
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
fun AppBarMy( //내집다방 그라데이션을 위해 특수제작했다고 생각하시면 됩니다(그렇게 수정했어요) -예은-
    endIcon: Int?,
    startIcon: Int?,
    onClickEndIcon: () -> Unit,
    onClickStartIcon: () -> Unit,
    centerText: String
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        navigationIcon = {
            startIcon?.let {
                IconButton(onClick = { onClickStartIcon() }) {
                    Icon(
                        painter = painterResource(id = it),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(4.dp),
                        tint = Color.White
                    )
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
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.cafe24ssurroundair)),
                    color = Color.White,
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
                        painter = painterResource(id = endIcon),
                        contentDescription = "search",
                        modifier = Modifier
                            .padding(4.dp),
                        tint = Color.White
                    )
                }
            }

        }
    )
}

@Composable
fun AppBarCollapsing(
    title: String,
    startIcon: ImageBitmap?,
    endIcon: ImageBitmap?,
    imageUrl: String,
    onClickStartIcon: () -> Unit,
    onClickEndIcon: () -> Unit,
    content: @Composable () -> Unit
) {
    val state = rememberCollapsingToolbarScaffoldState()

    CollapsingToolbarScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {

            val size by remember {
                mutableStateOf((300-10) * state.toolbarState.progress + 70)
            }

            /*val display = applicationContext.resources?.displayMetrics
            val deviceWidth = display?.widthPixels
            val deviceHeight = display?.heightPixels
            val deviceWidthDp = deviceWidth?.div(((applicationContext.resources.displayMetrics.densityDpi.toFloat()) / DisplayMetrics.DENSITY_DEFAULT))
            */

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(size.dp),
                model = imageUrl,
                contentDescription = "thumbnail",
                contentScale = ContentScale.Crop,
                alpha = state.toolbarState.progress
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(size.dp)
                    .alpha(state.toolbarState.progress)
            ) {

            }

            startIcon?.let {
                IconButton(
                    modifier = Modifier
                        .padding(8.dp)
                        .road(
                            whenCollapsed = Alignment.TopStart,
                            whenExpanded = Alignment.TopStart
                        ),
                    onClick = { onClickStartIcon() }
                ) {
                    Icon(
                        bitmap = startIcon,
                        contentDescription = "search",
                        modifier = Modifier
                            .padding(4.dp)
                            .alpha(1f),
                        tint = if (state.toolbarState.progress > 0) Color.White else ZipdabangandroidTheme.Colors.Typo
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth().padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.cafe24ssurroundair)),
                    color = ZipdabangandroidTheme.Colors.Typo
                        .copy(alpha = if (state.toolbarState.progress.toDouble() == 0.0) (1).toFloat() else (0).toFloat()),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 2.dp),
                    textAlign = TextAlign.Center
                )
            }

            endIcon?.let {
                Box(
                    contentAlignment = Alignment.TopEnd,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        modifier = Modifier
                            .padding(8.dp)
                            .road(
                                whenCollapsed = Alignment.TopEnd,
                                whenExpanded = Alignment.TopEnd
                            ),
                        onClick = { onClickEndIcon() }
                    ) {
                        Icon(
                            bitmap = endIcon,
                            contentDescription = "search",
                            modifier = Modifier
                                .padding(4.dp)
                                .alpha(1f),
                            tint = if (state.toolbarState.progress > 0) Color.White else ZipdabangandroidTheme.Colors.Typo
                        )
                    }
                }
            }
        }
    ) {
        content()
    }
}

@Composable
fun CollapsingAppbar(
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    startIcon: ImageBitmap?,
    endIcon: ImageBitmap?,
    imageUrl: String,
    onClickStartIcon: () -> Unit,
    onClickEndIcon: () -> Unit,
) {
    var headerHeightPx = 360f
    var toolbarHeightPx = 72f

    Box(modifier = modifier) {

        ToolbarHeader(scroll = scrollState, imageUrl = imageUrl, headerHeightPx = headerHeightPx)
        PageBody(scroll = scrollState)
        ToolbarBody(
            navigationIcon = startIcon,
            scroll = scrollState,
            headerHeightPx = headerHeightPx,
            toolbarHeightPx = toolbarHeightPx,
            actionIcon = endIcon,
            onClickNavigationIcon = onClickStartIcon,
            onClickActionIcon = onClickEndIcon
        )
    }
}

@Composable
fun ToolbarHeader(
    scroll: ScrollState,
    headerHeightPx: Float,
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    Box(modifier = modifier
        .height(360.dp)
        .graphicsLayer {
            translationY = -scroll.value.toFloat() / 2f
            alpha = (-1f / headerHeightPx) * scroll.value + 1
        }) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth(),
            model = imageUrl,
            contentDescription = "thumbnail",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun PageBody(
    modifier: Modifier = Modifier,
    scroll: ScrollState
) {
    Column(
        modifier = modifier
            .verticalScroll(scroll)
            .fillMaxWidth(),
    ) {
        // TODO height => same with the height of header
        Spacer(modifier = Modifier)
        Column {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarBody(
    modifier: Modifier = Modifier,
    scroll: ScrollState,
    navigationIcon: ImageBitmap?,
    actionIcon: ImageBitmap?,
    onClickNavigationIcon: () -> Unit,
    onClickActionIcon: () -> Unit,
    headerHeightPx: Float,
    toolbarHeightPx: Float
) {
    val toolbarBottomOffset by remember {
        mutableStateOf(headerHeightPx - toolbarHeightPx)
    }

    val showToolbar by remember {
        derivedStateOf {
            scroll.value > toolbarBottomOffset
        }
    }

    AnimatedVisibility(
        visible = showToolbar,
        enter = fadeIn(animationSpec = tween(300)),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        TopAppBar(
            modifier = modifier.background(ZipdabangandroidTheme.Colors.MainBackground),
            navigationIcon = {
                navigationIcon?.let {
                    IconButton(
                        modifier = Modifier
                            .padding(8.dp),
                        onClick = { onClickNavigationIcon() }
                    ) {
                        Icon(
                            bitmap = it,
                            contentDescription = "exit",
                            modifier = Modifier
                                .padding(4.dp),
                            tint = Color.White
                        )
                    }
                }
            },
            actions = {
                actionIcon?.let {
                    IconButton(
                        modifier = Modifier
                            .padding(8.dp),
                        onClick = { onClickActionIcon() }
                    ) {
                        Icon(
                            bitmap = it,
                            contentDescription = "menu",
                            modifier = Modifier
                                .padding(4.dp),
                            tint = Color.White
                        )
                    }
                }
            },
            title = {}
        )
    }

}

@Preview
@Composable
fun AppBarWithFullFunctionPreview() {
    AppBarWithFullFunction(
        startIcon = R.drawable.ic_topbar_backbtn,
        endIcon1 = R.drawable.ic_topbar_search,
        endIcon2 = R.drawable.ic_topbar_menu,
        onClickStartIcon = { /*TODO*/ },
        onClickEndIcon1 = { /*TODO*/ },
        onClickEndIcon2 = { /*TODO*/ },
        centerText = "레시피.zip"
    )
}

@Preview
@Composable
fun AppBarPreview() {
    AppBarHome(
        endIcon1 = com.kakao.sdk.friend.R.drawable.material_ic_keyboard_arrow_left_black_24dp,
        endIcon2 = androidx.appcompat.R.drawable.abc_ic_menu_copy_mtrl_am_alpha,
        onClickEndIcon1 = {},
        onClickEndIcon2 = {},
        centerText = "레시피.zip"
    )
}

@Preview
@Composable
fun AppBarDefaultPreview() {
    AppBarDefault(
        startIcon = com.kakao.sdk.friend.R.drawable.material_ic_keyboard_arrow_left_black_24dp,
        endIcon = androidx.appcompat.R.drawable.abc_ic_menu_copy_mtrl_am_alpha,
        onClickStartIcon = {},
        onClickEndIcon = {},
        centerText = "집다방"
    )
}

@Preview
@Composable
fun AppBarSignUpPreview() {
    AppBarSignUp(
        navigationIcon = com.kakao.sdk.friend.R.drawable.material_ic_keyboard_arrow_left_black_24dp,
        onClickNavIcon = { /*TODO*/ },
        centerText = "회원가입"
    )
}

@Preview
@Composable
fun AppBarMyPreview() {
    AppBarMy(
        startIcon = R.drawable.ic_topbar_backbtn,
        endIcon = com.kakao.sdk.friend.R.drawable.material_ic_keyboard_arrow_left_black_24dp,
        onClickStartIcon = { /*TODO*/ },
        onClickEndIcon = { /*TODO*/ },
        centerText = "집다방"
    )
}

@Preview
@Composable
fun AppBarCollapsingPreview() {
    AppBarCollapsing(
        title = "레시피",
        startIcon = loadXmlDrawable(resId = R.drawable.recipe_arrow_left),
        endIcon = loadXmlDrawable(resId = R.drawable.recipe_more_white),
        imageUrl = "https://github.com/kmkim2689/jetpack-compose-practice/assets/101035437/2bb0c4ab-e42b-4697-87c6-2fbe3c836cd7",
        onClickStartIcon = { /*TODO*/ },
        onClickEndIcon = { /*TODO*/ }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(100) {
                Text("kmkim")
            }
        }
    }
}

// https://slack-chats.kotlinlang.org/t/506477/hello-i-am-trying-to-load-a-layer-list-drawable-with-this-co
@Composable
fun loadXmlDrawable(@DrawableRes resId: Int): ImageBitmap? =
    ContextCompat.getDrawable(
        LocalContext.current,
        resId
    )?.toBitmap()?.asImageBitmap()

