package com.zipdabang.zipdabang_android.module.my.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun ButtonForRecipeWrite(
    borderColor: Color,
    containerColor: Color,
    onClickBtn : ()->Unit,
) {
    Button(
        onClick = { onClickBtn() },
        shape = ZipdabangandroidTheme.Shapes.thin,
        modifier = Modifier.fillMaxWidth(),
        enabled = true,
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_recipewrite_btn_add),
                contentDescription = "Icon",
                tint = ZipdabangandroidTheme.Colors.Strawberry,
                modifier = Modifier
                    .size(18.dp)
                    .padding(0.dp)
            )
        }
    }
}