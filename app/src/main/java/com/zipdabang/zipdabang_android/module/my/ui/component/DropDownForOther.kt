package com.zipdabang.zipdabang_android.module.my.ui.component

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun showDropdownForOther(
    isBlock : () -> Unit
) {
    val myDropDownList = listOf("회원 차단하기", "회원 신고하기")

    val isContextMenuVisible = remember {
        mutableStateOf(false)
    }

    Icon(
        painter = painterResource(id = R.drawable.all_more_white),
        contentDescription = null,
        tint = Color.White,
        modifier = Modifier
            .pointerInput(true) {
                detectTapGestures(
                    onPress = {
                        isContextMenuVisible.value = true
                    }
                )

            }
    )
    Box(modifier = Modifier.fillMaxWidth()) {
        DropdownMenu(
            expanded = isContextMenuVisible.value,
            onDismissRequest = {
                isContextMenuVisible.value = false
            },
            modifier = Modifier.align(Alignment.CenterEnd),
            //
        ) {
            myDropDownList.forEachIndexed { index, text ->
                DropdownMenuItem(
                    onClick =
                    {
                        if(index==0){
                            isBlock()
                        }
                        else {}

                    }
                ) {
                    Text(
                        text = text, style = ZipdabangandroidTheme.Typography.fourteen_500,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }

            }
        }
    }
}

