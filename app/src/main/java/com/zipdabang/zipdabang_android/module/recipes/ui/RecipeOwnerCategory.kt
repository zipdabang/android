package com.zipdabang.zipdabang_android.module.recipes.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RecipeOwnerCategory(
    modifier: Modifier = Modifier,
    groupName: String,
    title: String,
    subTitle: String,
    borderColor: Color,
    backgroundColor: Color,
    textColor: Color,
    drawable: Int,
    onClick: (String) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                shape = ZipdabangandroidTheme.Shapes.medium,
                color = borderColor
            )
            /*            .border(
                width = 2.dp,
                shape = ZipdabangandroidTheme.Shapes.medium,
                color = backgroundColor
            )*/
            .background(
                color = backgroundColor,
                shape = ZipdabangandroidTheme.Shapes.medium,
            )
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple()
            ) {
                onClick(groupName)
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
            ) {
                Text(
                    text = subTitle,
                    style = ZipdabangandroidTheme.Typography.ten_500
                        .copy(color = textColor, letterSpacing = 0.sp),
                )


                Text(
                    text = title,
                    style = ZipdabangandroidTheme.Typography.eighteen_700
                        .copy(
                            color = textColor,
                            lineHeight = 22.sp,
                            letterSpacing = 0.sp
                        )
                )
            }

            Image(
                painter = painterResource(id = drawable),
                contentDescription = "background pic"
            )
        }
    }
}

@Preview
@Composable
fun RecipeOwnerCategoryPreview() {
    Column {
        RecipeOwnerCategory(
            groupName = "zipdabang",
            title = "집다방의\n모든 레시피",
            subTitle = "맛있는 것만 골라 모아온",
            borderColor = Color(0xFFB8AFAB),
            backgroundColor = ZipdabangandroidTheme.Colors.BlackSesame,
            textColor = ZipdabangandroidTheme.Colors.Typo,
            drawable = R.drawable.recipe_category_zipdabang_revised,
            onClick = { groupName -> }
        )

        RecipeOwnerCategory(
            groupName = "barista",
            title = "집다방 바리스타의\n" +
                    "연구 레시피",
            subTitle = "집다방이 심사숙고해 선정한",
            borderColor = Color(0xFFC29789),
            backgroundColor = ZipdabangandroidTheme.Colors.Strawberry,
            textColor = Color.White,
            drawable = R.drawable.recipe_category_barista,
            onClick = { groupName -> }
        )

        RecipeOwnerCategory(
            groupName = "user",
            title = "우리들의\n" +
                    "자체 제작 레시피",
            subTitle = "언제든지 만들어 먹을 수 있는",
            borderColor = Color(0xFF856F65),
            backgroundColor = ZipdabangandroidTheme.Colors.Choco,
            textColor = Color.White,
            drawable = R.drawable.recipe_category_our,
            onClick = { groupName -> }
        )

    }
}