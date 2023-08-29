package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.CircleImage
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RecipeIntro(
    // 오너 아이디 추가??
    profileUrl: String,
    recipeTitle: String,
    recipeOwner: String,
    recipeIntro: String,
    onClickProfile: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Box(modifier = Modifier
                .width(48.dp)
                .height(48.dp)) {
                CircleImage(imageUrl = profileUrl, contentDescription = "profile")
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = recipeTitle,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                        color = ZipdabangandroidTheme.Colors.Typo,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = recipeOwner,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                        color = ZipdabangandroidTheme.Colors.Typo,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = recipeIntro,
            style = TextStyle(
                fontSize = 16.sp,
                color = ZipdabangandroidTheme.Colors.Typo,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium))
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeIntroPreview() {
    RecipeIntro(
        profileUrl = "https://github.com/kmkim2689/jetpack-compose-practice/assets/101035437/9b9dfe81-c3c6-461e-8b68-7045d09da7de",
        recipeTitle = "모히또 가서 몰디브 한 잔 어때요",
        recipeOwner = "용인사는 데미",
        recipeIntro = "몰디브 안 가도 알 것 같은 맛입니다! 재료도 얼마 안들어가서 만들기 편하고, 여름에 먹기 딱 좋아요 :)",
        onClickProfile = { userId -> }
    )
}