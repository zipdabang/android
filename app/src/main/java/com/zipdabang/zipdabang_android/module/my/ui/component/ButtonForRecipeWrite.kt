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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun ButtonForIngredient(
    borderColor: Color,
    containerColor: Color,
    enabled : Boolean,
    onClickBtn : ()->Unit,
) {
    Button(
        onClick = { onClickBtn() },
        shape = ZipdabangandroidTheme.Shapes.thin,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = containerColor,
        )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text= stringResource(id = R.string.my_recipewrite_addingredient),
                color = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
                style= ZipdabangandroidTheme.Typography.sixteen_500
            )
        }
    }
}

@Composable
fun ButtonForStep(
    borderColor: Color,
    containerColor: Color,
    enabled : Boolean,
    onClickBtn : ()->Unit,
) {
    Button(
        onClick = { onClickBtn() },
        shape = ZipdabangandroidTheme.Shapes.thin,
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = containerColor,
        )
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            /*Text(
                text= stringResource(id = R.string.my_recipewrite_addstep),
                color = ZipdabangandroidTheme.Colors.Typo,
                style= ZipdabangandroidTheme.Typography.sixteen_500
            )*/
            Text(
                text= stringResource(id = R.string.my_recipewrite_writedone),
                color = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
                style= ZipdabangandroidTheme.Typography.sixteen_500
            )
        }
    }
}