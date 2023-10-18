package com.zipdabang.zipdabang_android.module.my.ui.component.myrecipes

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun CompleteRecipeItem(
    thumbnail : String,
    title : String,
    createdAt : String,
    onClick : ()->Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clickable(onClick = { onClick() }),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Box(
                modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp)
            ){
                AsyncImage(
                    model = thumbnail,
                    contentScale = ContentScale.Crop,
                    contentDescription = "thumbnail",
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(4.dp)),
                )
            }
            Column(
                modifier = Modifier.padding(12.dp)
            ){
                Text(
                    text=title,
                    style = ZipdabangandroidTheme.Typography.fourteen_500,
                    color = ZipdabangandroidTheme.Colors.Typo
                )
                Text(
                    text=createdAt,
                    style = ZipdabangandroidTheme.Typography.fourteen_300,
                    color = ZipdabangandroidTheme.Colors.Typo
                )
            }
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
        )
    }
}

@SuppressLint("ResourceType")
@Composable
fun PreviewCompleteRecipeItem() {
    CompleteRecipeItem(
        thumbnail = stringResource(id = R.drawable.img_recipewrite_thumbnail),
        title = "딸기 두 스푼",
        createdAt = "23.08.06",
        onClick = {}
    )
}