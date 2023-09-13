package com.zipdabang.zipdabang_android.module.guide.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.guide.CardFace
import com.zipdabang.zipdabang_android.module.guide.HomeCafeTools

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlipCard(
    item : HomeCafeTools,
    modifier: Modifier = Modifier,
    back: @Composable () -> Unit = {},
    front: @Composable () -> Unit = {},
    ) {

    var cardFace by remember {
        mutableStateOf(CardFace.Front)
    }
        val rotation = animateFloatAsState(
            targetValue = cardFace.angle,
            animationSpec = tween(
                durationMillis = 400,
                easing = FastOutSlowInEasing,
            ), label = ""
        )
        Card(
            onClick = {  cardFace = cardFace.next  },
            modifier = modifier
                .graphicsLayer {
                    rotationY = rotation.value
                    cameraDistance = 12f * density
                },
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0x26000000))
        ) {
            if (rotation.value <= 90f) {
                Box(
                    Modifier.fillMaxSize()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 8.dp)
                    ) {


                        Image(painter = painterResource(id = item.image), contentDescription = null,
                            modifier = Modifier.size(176.dp))

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
                }
            } else {
                Box(
                    Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            rotationY = 180f
                        },
                ) {
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
    }
