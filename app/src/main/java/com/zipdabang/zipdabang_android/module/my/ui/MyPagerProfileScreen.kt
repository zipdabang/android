package com.zipdabang.zipdabang_android.module.my.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.my.data.remote.myinfo.MemberPreferCategoryDto
import com.zipdabang.zipdabang_android.ui.component.CircleImage
import com.zipdabang.zipdabang_android.ui.shimmeringEffect
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun MyPagerProfileScreen(
    shimmering: Boolean,
    oneline: String,
    preferCategoryList: MemberPreferCategoryDto,
    onClickUserInfo: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Log.e("my-info", "${oneline}")

    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        // 한 줄 소개
        Column(
            modifier = Modifier
                .padding(16.dp, 20.dp, 16.dp, 20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.my_profile_oneline),
                style = ZipdabangandroidTheme.Typography.sixteen_700,
                color = ZipdabangandroidTheme.Colors.Typo,
            )
            if (!oneline.isEmpty()) {
                Text(
                    text = oneline,
                    style = ZipdabangandroidTheme.Typography.sixteen_500,
                    color = ZipdabangandroidTheme.Colors.Typo,
                )
            } else if (shimmering) {
                Text(
                    text = "",
                    style = ZipdabangandroidTheme.Typography.sixteen_500,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (shimmering) {
                                Modifier.shimmeringEffect()
                            } else {
                                Modifier
                            }
                        )
                )
            } else {
                Box(
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(
                            color = ZipdabangandroidTheme.Colors.Cream,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable(onClick = { onClickUserInfo() })
                        .then(
                            if (shimmering) {
                                Modifier.shimmeringEffect()
                            } else {
                                Modifier
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = AnnotatedString(
                            text = stringResource(id = R.string.my_profile_oneline_none),
                            spanStyle = SpanStyle(
                                color = ZipdabangandroidTheme.Colors.Typo,
                                fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                                fontSize = 14.sp
                            )
                        ) + AnnotatedString(
                            text = stringResource(id = R.string.my_profile_oneline_write),
                            spanStyle = SpanStyle(
                                color = ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f),
                                fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                                fontSize = 14.sp
                            )
                        )
                    )
                }
            }
        }

        // 선호 카페 음료
        Column(
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.my_profile_prefercategory),
                style = ZipdabangandroidTheme.Typography.sixteen_700,
                color = ZipdabangandroidTheme.Colors.Typo,
            )
            if (shimmering) {
                Text(
                    text = "",
                    style = ZipdabangandroidTheme.Typography.sixteen_500,
                    color = ZipdabangandroidTheme.Colors.Typo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (shimmering) {
                                Modifier.shimmeringEffect()
                            } else {
                                Modifier
                            }
                        )
                )
            } else if (preferCategoryList.size == 0) {
                Box(
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(
                            color = ZipdabangandroidTheme.Colors.Cream,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable(onClick = { onClickUserInfo() }),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = AnnotatedString(
                            text = stringResource(id = R.string.my_profile_prefercategory_none),
                            spanStyle = SpanStyle(
                                color = ZipdabangandroidTheme.Colors.Typo,
                                fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                                fontSize = 14.sp
                            )
                        ) + AnnotatedString(
                            text = stringResource(id = R.string.my_profile_prefercategory_choose),
                            spanStyle = SpanStyle(
                                color = ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f),
                                fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                                fontSize = 14.sp
                            )
                        )
                    )
                }
            } else {
                val imageUrls = preferCategoryList.categories.map { it.imageUrl }
                LazyRow {
                    items(imageUrls) { imageUrl ->
                        Box(
                            modifier = Modifier.size(56.dp)
                        ) {
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .fillMaxSize()
                                    .then(
                                        if (shimmering) {
                                            Modifier.shimmeringEffect()
                                        } else {
                                            Modifier
                                        }
                                    ),
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }
                }
            }
        }

    }
}