package com.zipdabang.zipdabang_android.module.recipes.ui.hot

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.TogglePreferenceException
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.module.item.recipe.ui.IconToggle
import com.zipdabang.zipdabang_android.module.recipes.data.hot.HotRecipeItem
import com.zipdabang.zipdabang_android.module.recipes.ui.state.PreferenceToggleState
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun HotRecipeItem(
    modifier: Modifier = Modifier,
    index: Int,
    item: HotRecipeItem,
    onRecipeClick: (Int) -> Unit,
    onBlockedRecipeClick: (Int, Int) -> Unit,
    checkLoggedIn: () -> Boolean,
    onScrapClick: (Int) -> Deferred<Boolean>,
    onLikeClick: (Int) -> Deferred<Boolean>,
    showSnackbar: (String) -> Unit
) {

    var isLiked by remember { mutableStateOf(item.isLiked) }
    var isScraped by remember { mutableStateOf(item.isScrapped) }
    var likes by remember { mutableStateOf(item.likes) }

    val scope = rememberCoroutineScope()

    val scrapChecked = R.drawable.recipe_bookmark_checked
    val scrapNotChecked = R.drawable.recipe_bookmark_normal
    val favoriteChecked = R.drawable.recipe_favorite_checked
    val favoriteNotChecked = R.drawable.recipe_favorite_normal
    val favoriteCountIcon = R.drawable.recipe_favorite_count
    val commentCountIcon = R.drawable.recipe_comment_count

    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) {
                if (item.isBlocked) {
                    onBlockedRecipeClick(item.recipeId, item.ownerId)
                } else {
                    onRecipeClick(item.recipeId)
                }
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(85f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = index.toString(),
                style = ZipdabangandroidTheme.Typography.twelve_700
            )

            Spacer(modifier = Modifier.width(20.dp))

            AsyncImage(
                model = item.thumbnailUrl,
                contentDescription = "hot",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(ZipdabangandroidTheme.Shapes.medium)
                    .size(52.dp),
                placeholder = painterResource(id = R.drawable.zipdabanglogo_white)
            )

            Spacer(modifier = Modifier.width(12.dp))


            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {

                Text(
                    text = item.nickname,
                    overflow = TextOverflow.Ellipsis,
                    style = ZipdabangandroidTheme.Typography.ten_300.copy(letterSpacing = 0.sp)
                )

                Text(
                    text = item.recipeName,
                    overflow = TextOverflow.Ellipsis,
                    style = ZipdabangandroidTheme.Typography.fourteen_500.copy(letterSpacing = 0.sp),
                    maxLines = 1
                )

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = favoriteCountIcon),
                        contentDescription = "likes",
                        tint = ZipdabangandroidTheme.Colors.Strawberry
                    )

                    Spacer(modifier = Modifier.width(2.dp))

                    Text(
                        text = likes.toString(),
                        style = TextStyle(
                            color = ZipdabangandroidTheme.Colors.Typo,
                            fontSize = 8.sp,
                            fontWeight = FontWeight(300),
                            fontFamily = FontFamily(Font(R.font.kopubworlddotum_light))
                        )
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        painter = painterResource(id = commentCountIcon),
                        contentDescription = "comments",
                        tint = ZipdabangandroidTheme.Colors.Cream
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = item.comments.toString(),
                        style = TextStyle(
                            color = ZipdabangandroidTheme.Colors.Typo,
                            fontSize = 8.sp,
                            fontWeight = FontWeight(300),
                            fontFamily = FontFamily(Font(R.font.kopubworlddotum_light))
                        )
                    )
                }
            }
        }

        Row(
            modifier = Modifier.weight(15f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(26.dp)) {
                IconToggle(
                    iconChecked = scrapChecked,
                    iconNotChecked = scrapNotChecked,
                    checked = isScraped,
                    onClick = {
                        try {
                            val isLoggedIn = checkLoggedIn()
                            if (isLoggedIn) {
                                scope.launch {
                                    val isSuccessful = onScrapClick(item.recipeId).await()
                                    if (isSuccessful) {
                                        item.isScrapped = !item.isScrapped
                                        isScraped = item.isScrapped
                                    } else {
                                        showSnackbar("레시피를 스크랩할 수 없습니다.")
                                    }
                                }
                            }
/*                            if (currentPlatform != CurrentPlatform.NONE
                                && currentPlatform != CurrentPlatform.TEMP) {
                                onScrapClick(item.recipeId)
                                item.isScrapped = !item.isScrapped
                                isScraped = item.isScrapped
                            } else {
                                onScrapClick(item.recipeId)
                            }*/
                        } catch (e: TogglePreferenceException) {
                            Log.e("HotRecipeItem", "like toggle failure ${e.message}")
                        } catch (e: Exception) {
                            Log.e("HotRecipeItem", "like toggle failure ${e.message}")
                        }
                    },
                    checkedColor = ZipdabangandroidTheme.Colors.Cream
                )
            }

            Spacer(modifier = Modifier.width(2.dp))

            Box(modifier = Modifier.size(26.dp)) {
                IconToggle(
                    iconChecked = favoriteChecked,
                    iconNotChecked = favoriteNotChecked,
                    checked = isLiked,
                    onClick = {
                        try {
                            val isLoggedIn = checkLoggedIn()
                            if (isLoggedIn) {
                                scope.launch {
                                    val isSuccessful = onLikeClick(item.recipeId).await()
                                    if (isSuccessful) {
                                        item.isLiked = !item.isLiked
                                        isLiked = item.isLiked
                                        if (isLiked) {
                                            item.likes += 1
                                        } else {
                                            item.likes -= 1
                                        }
                                        likes = item.likes
                                    } else {
                                        showSnackbar("레시피에 좋아요를 누를 수 없습니다.")
                                    }
                                }
                            }
                        } catch (e: TogglePreferenceException) {
                            Log.e("HotRecipeItem", "like toggle failure ${e.message}")
                        } catch (e: Exception) {
                            Log.e("HotRecipeItem", "like toggle failure ${e.message}")
                        }
                    },
                    checkedColor = ZipdabangandroidTheme.Colors.Strawberry
                )
            }
        }
    }
}


@Composable
fun HotRecipeItemLoading() {

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        // the lightest color should be in the middle
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.35f)
    )

    // animate shimmer as long as we want
    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .width(6.dp)
                    .height(20.dp)
                    .background(brush)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Spacer(
                modifier = Modifier
                    .clip(ZipdabangandroidTheme.Shapes.medium)
                    .size(52.dp)
                    .background(brush)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.Start,
                ) {

                    Spacer(
                        modifier = Modifier
                            .width(40.dp)
                            .height(15.dp)
                            .background(brush)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Spacer(
                        modifier = Modifier
                            .width(80.dp)
                            .height(20.dp)
                            .background(brush)
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Spacer(
                        modifier = Modifier
                            .width(50.dp)
                            .height(12.dp)
                            .background(brush)
                    )
                }
            }
        }

        Row(
            modifier = Modifier.width(56.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(
                modifier = Modifier
                    .size(26.dp)
                    .background(brush)
            )

            Spacer(
                modifier = Modifier
                    .size(26.dp)
                    .background(brush)
            )
        }
    }
}

@Preview
@Composable
fun HotRecipeItemPreview() {
    val scope = rememberCoroutineScope()

    com.zipdabang.zipdabang_android.module.recipes.ui.hot.HotRecipeItem(
        index = 1,
        item = HotRecipeItem(false, false, 1, "false", 1, "dsafs", 1, "https://github.com/zipdabang/android/assets/101035437/3711da12-6056-47df-b177-94ba33bfdecc", 1, false),
        onRecipeClick = { int -> },
        onBlockedRecipeClick = { int, a -> },
        onScrapClick = { int -> scope.async { true } },
        onLikeClick = { int -> scope.async { true } },
        showSnackbar = { k -> },
        checkLoggedIn = { true }
    )
}