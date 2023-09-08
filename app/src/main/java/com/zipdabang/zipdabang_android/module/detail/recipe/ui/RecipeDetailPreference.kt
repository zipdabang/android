package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.item.recipe.ui.IconToggle
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import java.text.DecimalFormat

@Composable
fun RecipeDetailPreference(
    recipeId: Int,
    isOwner: Boolean,
    likes: Int,
    scraps: Int,
    isLikeChecked: Boolean,
    isScrapChecked: Boolean,
    onLikeClick: (Boolean) -> Unit,
    onScrapClick: (Boolean) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit
) {

    val thousands = DecimalFormat("##.0K")

    val likeString = if (likes >= 1000) {
        val likeNum = likes / 1000.0
        thousands.format(likeNum)
    } else likes.toString()

    val scrapString = if (scraps >= 1000) {
        val scrapNum = scraps / 1000.0
        thousands.format(scrapNum)
    } else scraps.toString()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val scrapChecked = R.drawable.recipe_bookmark_checked
        val scrapNotChecked = R.drawable.recipe_bookmark_normal
        val favoriteChecked = R.drawable.recipe_favorite_checked
        val favoriteNotChecked = R.drawable.recipe_favorite_normal

        Row(
            modifier = Modifier
        ) {
            // like
            Row(
                modifier = Modifier
                    .width(80.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Box(modifier = Modifier.size(30.dp)) {
                    IconToggle(
                        iconChecked = favoriteChecked,
                        iconNotChecked = favoriteNotChecked,
                        checked = isLikeChecked,
                        onClick = onLikeClick,
                        checkedColor = ZipdabangandroidTheme.Colors.Strawberry
                    )
                }


                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    modifier = Modifier.padding(top = 4.dp).width(60.dp),
                    text = likeString,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.kopubworlddotum_light)),
                        color = ZipdabangandroidTheme.Colors.Typo,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // scrap
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.Bottom
            ) {
                Box(modifier = Modifier.size(30.dp)) {
                    IconToggle(
                        iconChecked = scrapChecked,
                        iconNotChecked = scrapNotChecked,
                        checked = isScrapChecked,
                        onClick = onScrapClick,
                        checkedColor = ZipdabangandroidTheme.Colors.Cream
                    )
                }


                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = scrapString,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.kopubworlddotum_light)),
                        color = ZipdabangandroidTheme.Colors.Typo,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (isOwner) {
            RecipeManagement(
                recipeId = recipeId,
                deleteIcon = R.drawable.all_delete_black,
                editIcon = R.drawable.all_edit_black,
                onClickDelete = onDeleteClick,
                onClickEdit = onEditClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailPreferencePreview() {

    var likes by remember {
        mutableStateOf(1000)
    }

    var scraps by remember {
        mutableStateOf(2120)
    }

    var isLikeChecked by remember {
        mutableStateOf(true)
    }

    var isScrapChecked by remember {
        mutableStateOf(false)
    }

    RecipeDetailPreference(
        recipeId = 1,
        likes = likes,
        scraps = scraps,
        isLikeChecked = isLikeChecked,
        isScrapChecked = isScrapChecked,
        onLikeClick = { changed ->
            isLikeChecked = changed
            if (isLikeChecked) likes += 1 else likes -= 1
        },
        onScrapClick = { changed ->
            isScrapChecked = changed
            if (isScrapChecked) scraps += 1 else scraps -= 1
        },
        onDeleteClick = { delete -> },
        onEditClick = { edit -> },
        isOwner = true
    )
}