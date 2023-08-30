package com.zipdabang.zipdabang_android.module.guide.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.google.android.material.math.MathUtils.lerp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.guide.HomeCafeTools
import com.zipdabang.zipdabang_android.ui.component.RectangleImage
import com.zipdabang.zipdabang_android.ui.theme.White
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlin.math.abs
import kotlin.math.absoluteValue


@Composable
fun Carousel(
    count: Int,
    parentModifier: Modifier = Modifier
        .fillMaxWidth()
        .height(280.dp),
    contentWidth: Dp,
    contentHeight: Dp,
    content: @Composable (modifier: Modifier, index: Int) -> Unit
) {
    val listState = rememberLazyListState(Int.MAX_VALUE / 2)

    BoxWithConstraints(
        modifier = parentModifier
    ) {
        val halfRowWidth = constraints.maxWidth / 2
        LazyRow(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            contentPadding = PaddingValues(top = 10.dp,
                start = 3.dp,
                end = 3.dp),
            horizontalArrangement = Arrangement
                .spacedBy(- contentWidth * 0.75f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(
                count = Int.MAX_VALUE,
                itemContent = { globalIndex ->
                    val scale by remember {
                        derivedStateOf {
                            val currentItem = listState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == globalIndex } ?: return@derivedStateOf 0.85f
                            (1f - minOf(1f, abs(currentItem.offset + (currentItem.size / 2) - halfRowWidth).toFloat() / halfRowWidth) * 0.25f)
                        }
                    }

                    content(
                        index = globalIndex % count,
                        modifier = Modifier
                            .width(
                                contentWidth * 1.4f
                            )
                            .height(contentHeight * 1.4f)
                            .scale(scale)
                            .zIndex(scale * 10)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(size = 12.dp)
                            )

                    )
                }
            )
        }
    }
}


@Preview
@Composable
fun GuidePreview(){


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyComposableContent(
    item : HomeCafeTools,
    modifier: Modifier
)
{
    var isTitle by remember  {
        mutableStateOf(true)
    }
    Card(
        onClick = { isTitle = !isTitle },
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0x26000000))
    )
    {

            if(isTitle) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        item.title,
                        fontSize = 28.sp,
                        fontFamily = FontFamily(Font(R.font.scdream_bold)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFFDFB7AB),
                        textAlign = TextAlign.Center
                    )

                    Image(
                        painter = painterResource(id = R.drawable.guide_click), null,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }else{
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 5.dp)
                    ){
                Text(
                    item.description,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF262D31),
                    textAlign = TextAlign.Center
                )


            }

            }
        }

    }


