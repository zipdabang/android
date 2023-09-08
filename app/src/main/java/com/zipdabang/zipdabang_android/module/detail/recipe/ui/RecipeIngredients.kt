package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.detail.recipe.data.Ingredient
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RecipeIngredients(
    ingredients: List<Ingredient> = emptyList(),
    onClickCart: (String) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "재료",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                color = ZipdabangandroidTheme.Colors.Choco,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            ingredients.forEach { item ->
                IngredientItem(ingredient = item, onClickCart = onClickCart)
            }
        }


    }
}

@Composable
fun IngredientItem(
    ingredient: Ingredient,
    onClickCart: (String) -> Unit
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(1.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(5f)
            ) {
                Text(
                    text = ingredient.ingredientName,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                        color = ZipdabangandroidTheme.Colors.Typo,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                modifier = Modifier.weight(4f)
            ) {
                Text(
                    text = ingredient.quantity,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                        color = ZipdabangandroidTheme.Colors.Typo,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                IconButton(
                    modifier = Modifier
                        .size(24.dp)
                        .fillMaxSize(),
                    onClick = {
                        onClickCart(ingredient.ingredientName)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.recipe_cart_black),
                        contentDescription = "search goods",
                        modifier = Modifier
                            .padding(4.dp),
                        tint = ZipdabangandroidTheme.Colors.Typo
                    )
                }
            }
        }
        Divider(
            modifier = Modifier.fillMaxWidth()
                .height(1.dp),
            color = Color(0x1A262D31)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IngredientItemPrev() {
    IngredientItem(Ingredient("우유", "1ml"), { recipe -> })
}

@Preview(showBackground = true)
@Composable
fun RecipeIngredientsPreview() {
    RecipeIngredients(
        ingredients = listOf(
            Ingredient(
                "우유", "1mL"
            ),
            Ingredient(
                "우유", "1mL"
            ),
            Ingredient(
                "우유", "1mL"
            ),
            Ingredient(
                "우유", "1mL"
            ),
            Ingredient(
                "우유", "1mL"
            ),
        ),
        onClickCart = { keyword ->

        }
    )
}