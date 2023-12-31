package com.zipdabang.zipdabang_android.module.item.goods.ui

import android.util.Log
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.noRippleClickable
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun GoodsToggle(
    iconChecked: Int,
    iconNotChecked: Int,
    checked: Boolean,
    enabled: Boolean = true,
    onClick: (Boolean) -> Unit,
    checkedColor: Color
) {

    IconToggleButton(
        checked = checked,
        onCheckedChange = {
            onClick(it)
        },
        enabled = enabled,
        modifier = Modifier
            .width(24.dp)
            .height(28.dp),
        interactionSource = NoRippleInteractionSource()
    ) {
        Icon(
            painter =
            if (checked) painterResource(id = iconChecked)
            else painterResource(id = iconNotChecked),
            contentDescription = "toggle",
            tint = if (checked) checkedColor else LocalContentColor.current,
           )

    }

        Log.d("icon state", "$checked")
    }

class NoRippleInteractionSource : MutableInteractionSource{
    override val interactions: Flow<Interaction>
        get() = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction): Boolean = true
}
@Preview
@Composable
fun IconTogglePreview() {

    var checked1 by remember {
        mutableStateOf(false)
    }

    var checked2 by remember {
        mutableStateOf(false)
    }

    Column {
        GoodsToggle(
            iconChecked = R.drawable.recipe_bookmark_checked,
            iconNotChecked = R.drawable.recipe_bookmark_normal,
            checked = checked1,
            onClick = { state -> checked1 = state },
            checkedColor = ZipdabangandroidTheme.Colors.Cream
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = checked1.toString())

        Spacer(modifier = Modifier.height(12.dp))

        GoodsToggle(
            iconChecked = R.drawable.recipe_favorite_checked,
            iconNotChecked = R.drawable.recipe_favorite_normal,
            checked = checked2,
            onClick = { state -> checked2 = state },
            checkedColor = ZipdabangandroidTheme.Colors.Strawberry
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = checked2.toString())
    }
}