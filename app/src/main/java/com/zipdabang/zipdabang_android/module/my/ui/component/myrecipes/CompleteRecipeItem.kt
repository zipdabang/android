package com.zipdabang.zipdabang_android.module.my.ui.component.myrecipes

import android.annotation.SuppressLint
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CompleteRecipeItem(
    thumbnail: String,
    title: String,
    createdAt: String,
    onClick: () -> Unit,
    onDeleteClick : ()->Unit,
    onEditClick : ()->Unit,
) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val dropDownList = listOf("수정하기", "삭제하기")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clickable(onClick = { onClick() }),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp)
            ) {
                AsyncImage(
                    model = thumbnail,
                    contentScale = ContentScale.Crop,
                    contentDescription = "thumbnail",
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(4.dp)),
                )
            }
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = title,
                    style = ZipdabangandroidTheme.Typography.fourteen_500,
                    color = ZipdabangandroidTheme.Colors.Typo
                )
                Text(
                    text = createdAt,
                    style = ZipdabangandroidTheme.Typography.fourteen_300,
                    color = ZipdabangandroidTheme.Colors.Typo
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.padding(0.dp, 22.dp, 0.dp, 12.dp)
            ){
                Icon(
                    painter = painterResource(id = R.drawable.my_followspot_small),
                    contentDescription = null,
                    tint = ZipdabangandroidTheme.Colors.Typo,
                    modifier = Modifier
                        .indication(interactionSource, LocalIndication.current)
                        .pointerInteropFilter {
                            pressOffset = DpOffset(it.x.dp, it.y.dp)
                            false
                        }
                        .pointerInput(true) {
                            detectTapGestures(
                                onPress = {
                                    isContextMenuVisible = true
                                }
                            )

                        }
                )
                DropdownMenu(
                    expanded = isContextMenuVisible,
                    onDismissRequest = {
                        isContextMenuVisible = false
                    },
                    modifier = Modifier
                ) {
                    dropDownList.forEachIndexed{ index, text ->
                        DropdownMenuItem(
                            onClick = {
                                if(index==0){
                                    isContextMenuVisible = false
                                    onEditClick()
                                }
                                else{
                                    isContextMenuVisible = false
                                    onDeleteClick()
                                }
                            }
                        ) {
                            Text(
                                text = text,
                                style = ZipdabangandroidTheme.Typography.fourteen_500,
                                color = ZipdabangandroidTheme.Colors.Typo
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
        )
    }
}