package com.zipdabang.zipdabang_android.module.my.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCard
import com.zipdabang.zipdabang_android.module.item.recipe.ui.RecipeCardLoading
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.MyRecipesViewModel
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.MyViewModel
import com.zipdabang.zipdabang_android.ui.shimmeringEffect
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun MyPagerRecipesScreen(
    shimmering : Boolean,
    nickname : String,
    onClickMyRecipeList : (String)->Unit,
    onClickMyrecipe : ()->Unit,
    onRecipeItemClick : (Int) ->Unit,
    showSnackBar: (String) -> Unit,
    viewModel : MyViewModel = hiltViewModel()
) {
    val stateCompleteRecipesPreview = viewModel.stateCompleteRecipesPreview

    // 레시피
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 20.dp, 16.dp, 4.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(),
                    onClick = {
                        onClickMyRecipeList(nickname)
                    }
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(shimmering){

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.width(40.dp)
                            .height(20.dp)
                            .shimmeringEffect()
                    )
                    Text(
                        text = "님의 레시피",
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = ZipdabangandroidTheme.Colors.Typo,
                    )
                }
            }
            else{
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${nickname}님의 레시피",
                        style = ZipdabangandroidTheme.Typography.sixteen_700,
                        color = ZipdabangandroidTheme.Colors.Typo,
                    )
                }
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.all_arrow_right),
                    contentDescription = null,
                    tint = ZipdabangandroidTheme.Colors.Typo
                )
            }
        }

        // 내 레시피 미리보기 목록
        if(stateCompleteRecipesPreview.isLoading){
            LazyRow(
                modifier = Modifier.padding(start = 16.dp, end =16.dp, top= 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(5) {
                    RecipeCardLoading()
                }
            }
        }
        else if(stateCompleteRecipesPreview.totalElements == 0){
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .padding(16.dp, 0.dp, 16.dp, 0.dp)
                    .fillMaxWidth()
                    .background(
                        color = ZipdabangandroidTheme.Colors.Cream,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable(onClick = { onClickMyrecipe() })
                    .then(
                        if (shimmering) {
                            Modifier.shimmeringEffect()
                        } else {
                            Modifier
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier,
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = ZipdabangandroidTheme.Colors.Typo,
                                fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                                fontSize = 14.sp
                            )
                        ) {
                            append("제작해 본 레시피가 아직 없습니다")
                        }

                        append("  ")

                        withStyle(
                            style = SpanStyle(
                                color = ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f),
                                fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                                fontSize = 14.sp
                            )
                        ) {
                            append("(만들어보기)")
                        }
                    }
                )
            }
        }
        else{
            LazyRow(
                modifier = Modifier.padding(start = 16.dp, end =16.dp, top= 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(stateCompleteRecipesPreview.totalElements) {
                    RecipeCard(
                        recipeId = stateCompleteRecipesPreview.recipeList[it]!!.recipeId,
                        title = stateCompleteRecipesPreview.recipeList[it]!!.recipeName,
                        user = stateCompleteRecipesPreview.recipeList[it]!!.nickname,
                        thumbnail = stateCompleteRecipesPreview.recipeList[it]!!.thumbnailUrl,
                        date = stateCompleteRecipesPreview.recipeList[it]!!.createdAt,
                        likes = stateCompleteRecipesPreview.recipeList[it]!!.likes,
                        comments = stateCompleteRecipesPreview.recipeList[it]!!.comments,
                        isLikeSelected = stateCompleteRecipesPreview.recipeList[it]!!.isLiked,
                        isScrapSelected = stateCompleteRecipesPreview.recipeList[it]!!.isScrapped,
                        onLikeClick = {
                            showSnackBar("본인 레시피에 좋아요를 누를 수 없습니다.")
                        },
                        onScrapClick = {
                            showSnackBar("본인 레시피를 스크랩 할 수 없습니다.")
                        },
                        onItemClick = {
                            onRecipeItemClick(it)
                        }
                    )
                }
            }
        }
    }
}