package com.zipdabang.zipdabang_android.module.item.recipe.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.CardTitleDefault
import com.zipdabang.zipdabang_android.ui.component.RectangleImage
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RecipeCard(
    recipeId: Int,
    title: String,
    user: String,
    thumbnail: String,
    date: String,
    // likes, comments : Int or Long?
    likes: Int,
    comments: Int,
    isLikeSelected: Boolean,
    isScrapSelected: Boolean,
    onLikeClick: (Int) -> Boolean,
    onScrapClick: (Int) -> Boolean,
    // id
    onItemClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .height(228.dp)
            .border(
                width = 1.dp,
                color = Color(0x1A262D31),
                shape = ZipdabangandroidTheme.Shapes.small
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) {
                onItemClick(recipeId)
            },
    ) {
        Column(
            modifier = Modifier
                .padding(top = 2.dp, start = 4.dp, end = 4.dp)
        ) {
            CardTitleDefault(title = title, subTitle = user)
        }

        Column(
            modifier = Modifier
                .height(160.dp)
        ) {
            RectangleImage(imageUrl = thumbnail, contentDescription = title)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            val scrapChecked = R.drawable.recipe_bookmark_checked
            val scrapNotChecked = R.drawable.recipe_bookmark_normal
            val favoriteChecked = R.drawable.recipe_favorite_checked
            val favoriteNotChecked = R.drawable.recipe_favorite_normal
            val favoriteCountIcon = R.drawable.recipe_favorite_count
            val commentCountIcon = R.drawable.recipe_comment_count

            // date, likes, comments
            Column {
                Text(
                    text = "$date 작성",
                    style = TextStyle(
                        fontSize = 6.sp,
                        fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                        fontWeight = FontWeight(500),
                        color = Color(0x80262D31),
                    )
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
                        text = "$likes",
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
                        text = "$comments",
                        style = TextStyle(
                            color = ZipdabangandroidTheme.Colors.Typo,
                            fontSize = 8.sp,
                            fontWeight = FontWeight(300),
                            fontFamily = FontFamily(Font(R.font.kopubworlddotum_light))
                        )
                    )
                }
            }

            // IconButtons
            Row {
                Box(modifier = Modifier.size(26.dp)) {
                    IconToggle(
                        iconChecked = scrapChecked,
                        iconNotChecked = scrapNotChecked,
                        checked = isScrapSelected,
                        onClick = { isScrapSelected ->
                            val result = onScrapClick(recipeId)
                            if (result) !isScrapSelected

                        },
                        checkedColor = ZipdabangandroidTheme.Colors.Cream
                    )
                }

                Spacer(modifier = Modifier.width(2.dp))

                Box(modifier = Modifier.size(26.dp)) {
                    IconToggle(
                        iconChecked = favoriteChecked,
                        iconNotChecked = favoriteNotChecked,
                        checked = isLikeSelected,
                        onClick = { isLikeSelected ->
                            val result = onLikeClick(recipeId)
                            if (result) !isLikeSelected
                        },
                        checkedColor = ZipdabangandroidTheme.Colors.Strawberry
                    )
                }

            }
        }

    }
}

@Composable
fun RecipeCardLoading() {
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

    Column(
        modifier = Modifier
            .width(160.dp)
            .height(228.dp)
            .border(
                width = 1.dp,
                color = Color(0x1A262D31),
                shape = ZipdabangandroidTheme.Shapes.small
            )
    ) {
        Spacer(modifier = Modifier
            .fillMaxSize()
            .background(brush))
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeCardPreview() {
    var like by remember {
        mutableStateOf(false)
    }

    var scrap by remember {
        mutableStateOf(false)
    }

    RecipeCard(
        recipeId = 1,
        title = "혈관폭발 초코스무디",
        user = "집다방",
        thumbnail = "https://github.com/kmkim2689/jetpack-compose-practice/assets/101035437/209e1adb-56bd-478a-8a3e-08973f03a190",
        date = "23. 08. 10",
        likes = 10,
        comments = 10,
        isLikeSelected = like,
        isScrapSelected = scrap,
        onLikeClick = { state ->
            like = !like
            true
        },
        onScrapClick = { state ->
            scrap = !scrap
            true
        },
        onItemClick = { recipeId ->

        }
    )
}

@Preview
@Composable
fun RecipeCardLoadingPreview() {
    RecipeCardLoading()
}