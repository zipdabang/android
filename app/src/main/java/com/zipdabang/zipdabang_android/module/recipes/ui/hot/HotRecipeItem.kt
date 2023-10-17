package com.zipdabang.zipdabang_android.module.recipes.ui.hot

import android.util.Log
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HotRecipeItem(
    modifier: Modifier = Modifier,
    index: Int,
    item: HotRecipeItem,
    onRecipeClick: (Int) -> Unit,
    onScrapClick: (Int) -> Unit,
    onLikeClick: (Int) -> Unit,
    likeState: PreferenceToggleState,
    scrapState: PreferenceToggleState,
    setShowLoginRequestDialog: () -> Unit,
    currentPlatform: CurrentPlatform,
    showSnackbar: (String) -> Unit
) {

    var isLiked by remember { mutableStateOf(item.isLiked) }
    var isScraped by remember { mutableStateOf(item.isScrapped) }
    var likes by remember { mutableStateOf(item.likes) }

    if (likeState.errorMessage != null
        || scrapState.errorMessage != null) {
        throw TogglePreferenceException
    }

    if (likeState.isLoading || likeState.isLoading) {
        CircularProgressIndicator(color = ZipdabangandroidTheme.Colors.Strawberry)
    }

    val scrapChecked = R.drawable.recipe_bookmark_checked
    val scrapNotChecked = R.drawable.recipe_bookmark_normal
    val favoriteChecked = R.drawable.recipe_favorite_checked
    val favoriteNotChecked = R.drawable.recipe_favorite_normal
    val favoriteCountIcon = R.drawable.recipe_favorite_count
    val commentCountIcon = R.drawable.recipe_comment_count

    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) {
                onRecipeClick(item.recipeId)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier
            .width(8.dp)
            .height(72.dp))

        Text(
            modifier = Modifier.weight(1f),
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
                .size(52.dp)
                .weight(3f),
            placeholder = painterResource(id = R.drawable.zipdabanglogo_white)
        )

        Column(
            modifier = Modifier.weight(10f),
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.width(52.dp))

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
                    style = ZipdabangandroidTheme.Typography.fourteen_500.copy(letterSpacing = 0.sp)
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
                        // TODO comment 수 추가하기
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
            modifier = Modifier.weight(3f)
        ) {
            Box(modifier = Modifier.size(26.dp)) {
                IconToggle(
                    iconChecked = scrapChecked,
                    iconNotChecked = scrapNotChecked,
                    checked = isScraped,
                    onClick = {
                        try {
                            if (currentPlatform != CurrentPlatform.NONE
                                && currentPlatform != CurrentPlatform.TEMP) {
                                onScrapClick(item.recipeId)
                                item.isScrapped = !item.isScrapped
                                isScraped = item.isScrapped
                            } else {
                                onScrapClick(item.recipeId)
                            }
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
                            if (currentPlatform != CurrentPlatform.NONE
                                && currentPlatform != CurrentPlatform.TEMP) {
                                onLikeClick(item.recipeId)
                                item.isLiked = !item.isLiked
                                isLiked = item.isLiked
                                if (isLiked) {
                                    item.likes += 1
                                } else {
                                    item.likes -= 1
                                }
                                likes = item.likes
                            } else {
                                onLikeClick(item.recipeId)
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

@Preview
@Composable
fun HotRecipeItemPreview() {
    com.zipdabang.zipdabang_android.module.recipes.ui.hot.HotRecipeItem(
        index = 1,
        item = HotRecipeItem(false, false, 1, "false", 1, "dsafs", 1, ""),
        onRecipeClick = { int -> },
        onScrapClick = { int -> },
        onLikeClick = { int -> },
        likeState = PreferenceToggleState(),
        scrapState = PreferenceToggleState(),
        setShowLoginRequestDialog = {  },
        showSnackbar = { k -> },
        currentPlatform = CurrentPlatform.NONE
    )
}