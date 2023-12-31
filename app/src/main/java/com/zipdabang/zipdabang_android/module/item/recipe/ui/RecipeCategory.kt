package com.zipdabang.zipdabang_android.module.item.recipe.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.CircleImage
import com.zipdabang.zipdabang_android.ui.component.bounceClick
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RecipeCategory(
    categoryTitle: String,
    categoryId: Int,
    imageUrl: String,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .bounceClick()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick(categoryId)
            }
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .size(76.dp)
        ) {
            CircleImage(imageUrl = imageUrl, contentDescription = categoryTitle)
        }
        Spacer(modifier = Modifier.height(2.dp))
        Column(
            modifier = Modifier.height(18.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier,
                text = categoryTitle,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                maxLines = 1,
                fontSize = 14.sp,
                color = ZipdabangandroidTheme.Colors.Typo
            )
        }

    }
}

@Composable
fun RecipeCategoryLoading() {
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
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(76.dp)
                .clip(CircleShape)
        ) {
            Spacer(modifier = Modifier
                .fillMaxSize()
                .background(brush = brush)
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .width(76.dp)
                .height(18.dp)
                .background(brush = brush)
        ) {
            Spacer(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(brush = brush)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeCategoryPreview() {
    RecipeCategory(
        categoryTitle = "커피",
        categoryId = 1,
        imageUrl = "https://github.com/zipdabang/android/assets/101035437/a844c469-a09d-4333-921a-5f778c786b03",
        onClick = { category ->

        }
    )
}

@Preview
@Composable
fun RecipeCategoryLoadingPreview() {
    RecipeCategoryLoading()
}