package com.zipdabang.zipdabang_android.module.my.ui.component.myrecipes

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun TempRecipeItem(
    thumbnail : String?,
    title : String?,
    createdAt : String,
    onEditClick : ()->Unit,
    onDeleteClick : ()->Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ){
            Row{
                Box(
                    modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp)
                ){
                    if(thumbnail == null){
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(ZipdabangandroidTheme.Colors.Typo.copy(0.1f))
                        )
                    } else{
                        AsyncImage(
                            model = thumbnail,
                            contentScale = ContentScale.Crop,
                            contentDescription = "thumbnail",
                            modifier = Modifier
                                .size(52.dp)
                                .clip(RoundedCornerShape(4.dp)),
                        )
                    }
                }
                Column(
                    modifier = Modifier.padding(12.dp)
                ){
                    Text(
                        text= if(title==null)"제목이 정해지지 않았습니다." else title,
                        style = ZipdabangandroidTheme.Typography.fourteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                    Text(
                        text= createdAt,
                        style = ZipdabangandroidTheme.Typography.fourteen_300,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .padding(0.dp, 16.dp, 0.dp, 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Icon(
                    painter = painterResource(id = R.drawable.ic_recipewrite_trashcan),
                    contentDescription = "Icon",
                    tint = ZipdabangandroidTheme.Colors.Typo,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable(onClick = { onDeleteClick() }),
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_my_edit),
                    contentDescription = "Icon",
                    tint = ZipdabangandroidTheme.Colors.Typo,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable(onClick = { onEditClick() }),
                )
            }
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
        )
    }
}

@Composable
fun TempRecipeItemLoading() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        // the lightest color should be in the middle
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.35f)
    )
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
            .fillMaxWidth()
            .height(72.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ){
            Row{
                Box(
                    modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp)
                ){
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(ZipdabangandroidTheme.Shapes.medium)
                            .background(brush)
                    )
                }
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ){
                    Spacer(
                        modifier = Modifier
                            .width(50.dp)
                            .height(20.dp)
                            .background(brush)
                    )
                    Spacer(
                        modifier = Modifier
                            .width(50.dp)
                            .height(20.dp)
                            .background(brush)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .padding(0.dp, 16.dp, 0.dp, 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Spacer(
                    modifier = Modifier
                        .size(28.dp)
                        .background(brush)
                )
                Spacer(
                    modifier = Modifier
                        .size(28.dp)
                        .background(brush)
                )
            }
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
        )
    }

}