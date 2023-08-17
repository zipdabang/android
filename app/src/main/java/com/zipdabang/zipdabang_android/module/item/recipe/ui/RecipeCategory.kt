package com.zipdabang.zipdabang_android.module.item.recipe.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.CircleImage
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RecipeCategory(
    category: String,
    imageUrl: String,
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable {
                onClick(category)
            }
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .size(76.dp)
        ) {
            CircleImage(imageUrl = imageUrl, contentDescription = category)
        }
        Text(
            modifier = Modifier
                .padding(top = 2.dp),
            text = category,
            fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
            maxLines = 1,
            fontSize = 14.sp,
            color = ZipdabangandroidTheme.Colors.Typo
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeCategoryPreview() {
    RecipeCategory(
        category = "커피",
        imageUrl = "https://github.com/zipdabang/android/assets/101035437/a844c469-a09d-4333-921a-5f778c786b03",
        onClick = { category ->

        }
    )
}