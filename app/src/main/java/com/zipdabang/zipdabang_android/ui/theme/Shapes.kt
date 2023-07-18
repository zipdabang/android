package com.zipdabang.zipdabang_android.ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

data class ZipdabangShapes(
    val thin : CornerBasedShape = RoundedCornerShape(2.dp),
    val small : CornerBasedShape = RoundedCornerShape(4.dp),
    val medium : CornerBasedShape = RoundedCornerShape(8.dp),
    val large: CornerBasedShape = RoundedCornerShape(20.dp),

    val circle : CornerBasedShape = RoundedCornerShape(100),

    val smallRoundedTop : CornerBasedShape = RoundedCornerShape(
        topStart = 4.dp,
        topEnd = 4.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    ),
    val smallRoundedBottom : CornerBasedShape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp,
        bottomStart = 4.dp,
        bottomEnd = 4.dp
    ),
    val smallRoundedStart : CornerBasedShape = RoundedCornerShape(
        topStart = 4.dp,
        topEnd = 0.dp,
        bottomStart = 4.dp,
        bottomEnd = 0.dp
    ),
    val smallRoundedEnd : CornerBasedShape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 4.dp,
        bottomStart = 0.dp,
        bottomEnd = 4.dp
    ),
    val mediumRoundedTop : CornerBasedShape = RoundedCornerShape(
        topStart = 8.dp,
        topEnd = 8.dp,
        bottomStart = 0.dp,
        bottomEnd = 0.dp
    ),
    val mediumRoundedBottom : CornerBasedShape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 0.dp,
        bottomStart = 8.dp,
        bottomEnd = 8.dp
    ),

)