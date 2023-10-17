package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.detail.recipe.data.RecipeStep
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RecipeSteps(
    steps: List<RecipeStep> = emptyList()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "레시피",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                color = ZipdabangandroidTheme.Colors.Choco,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(10.dp))

        steps.forEach {
            RecipeStepItem(step = it)
        }
    }
}

@Composable
fun RecipeStepItem(
    step: RecipeStep
) {
    Column(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color(0x1A262D31),
                shape = RoundedCornerShape(size = 4.dp)
            )
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier.aspectRatio(21 / 10f)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = step.image,
                contentDescription = "step image",
                contentScale = ContentScale.Crop
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Step ${step.stepNum}. ",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                    color = ZipdabangandroidTheme.Colors.Typo,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.weight(0.1f))

            Text(
                modifier = Modifier.weight(5f),
                text = step.description,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                    color = ZipdabangandroidTheme.Colors.Typo,
                ),
                overflow = TextOverflow.Ellipsis
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@Preview(showBackground = true)
@Composable
fun RecipeStepItemPreview() {
    RecipeStepItem(
        step = RecipeStep(
            description = "라임을 찬물에 잘 씻어줍니다. 어쩌구저쩌구",
            image = "https://github.com/kmkim2689/jetpack-compose-practice/assets/101035437/7dbef4ff-303c-4059-aa67-fec7d77e92c2",
            stepNum = 1
        )
    )
}

@Preview
@Composable
fun RecipeStepsPreview() {
    RecipeSteps(
        steps = listOf(
            RecipeStep(
                description = "라임을 찬물에 잘 씻어줍니다. 어쩌구저쩌구",
                image = "https://github.com/kmkim2689/jetpack-compose-practice/assets/101035437/7dbef4ff-303c-4059-aa67-fec7d77e92c2",
                stepNum = 1
            ),
            RecipeStep(
                description = "라임을 찬물에 잘 씻어줍니다. 어쩌구저쩌구",
                image = "https://github.com/kmkim2689/jetpack-compose-practice/assets/101035437/7dbef4ff-303c-4059-aa67-fec7d77e92c2",
                stepNum = 1
            ),
            RecipeStep(
                description = "라임을 찬물에 잘 씻어줍니다. 어쩌구저쩌구",
                image = "https://github.com/kmkim2689/jetpack-compose-practice/assets/101035437/7dbef4ff-303c-4059-aa67-fec7d77e92c2",
                stepNum = 1
            ),
            RecipeStep(
                description = "라임을 찬물에 잘 씻어줍니다. 어쩌구저쩌구",
                image = "https://github.com/kmkim2689/jetpack-compose-practice/assets/101035437/7dbef4ff-303c-4059-aa67-fec7d77e92c2",
                stepNum = 1
            )
        )
    )
}