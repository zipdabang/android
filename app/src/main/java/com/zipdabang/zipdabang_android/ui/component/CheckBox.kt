package com.zipdabang.zipdabang_android.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

//15번 -> 완성
@Composable
fun CheckBoxCustom(
    rounded : Boolean,
    isToggled : Boolean,
    onIstoggledChange : (Boolean) -> Unit,
){
    var isToggled by remember { mutableStateOf(isToggled) }

    val containerColor = if (isToggled) ZipdabangandroidTheme.Colors.Strawberry else Color.White
    val iconColor = if (isToggled) Color.White else ZipdabangandroidTheme.Colors.Strawberry
    val shapeType = if (rounded) CircleShape else RoundedCornerShape(1.dp)

    Box(
        modifier = Modifier
            .clip(shapeType)
            .border(1.dp, color = ZipdabangandroidTheme.Colors.Strawberry, shape = shapeType)
            .background(Color.Transparent, shapeType)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = {
                isToggled = !isToggled
                onIstoggledChange(isToggled)
                      },
            modifier = Modifier.fillMaxSize(),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = containerColor,
                contentColor = ZipdabangandroidTheme.Colors.Strawberry
            ),
        ){
            Icon(
                imageVector = Icons.Filled.Done,
                contentDescription = "check icon",
                tint = iconColor,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp)
            )
        }
    }
}


@Preview
@Composable
fun PreviewCheckBoxRounded() {
    var isToggled by remember { mutableStateOf(true) }
    Box(modifier = Modifier.padding(16.dp)
        .size(18.dp)){
        CheckBoxCustom(
            rounded =true,
            isToggled = isToggled,
            onIstoggledChange = {selectedToggled -> isToggled = selectedToggled })
    }
}

