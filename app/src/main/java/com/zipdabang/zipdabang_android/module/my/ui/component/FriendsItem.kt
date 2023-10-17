package com.zipdabang.zipdabang_android.module.my.ui.component

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.CircleImage
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FollowItem(
    imageUrl : String,
    nickName : String,
    isFollow : Boolean,
    isFollowEach : Boolean = false,
    followOrCancelClick : () -> Unit,
    userReport : () -> Unit,
    onClickOthers : () -> Unit
) {

    //for DropDown
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemWidth by remember {
        mutableStateOf(0.dp)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current

    val isFollowDropDown = listOf("팔로우 끊기", "신고하기")
    val isFollowingDropDown = listOf("맞팔로우 하기", "신고하기")


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Row(
            modifier = Modifier.align(Alignment.CenterStart)
                .clickable {
                           onClickOthers()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,

        ) {
            Box(modifier = Modifier.size(40.dp)) {
                CircleImage(
                    imageUrl = imageUrl,
                    contentDescription = "FriendItem Image"
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = nickName,
                style = ZipdabangandroidTheme.Typography.sixteen_500,
                color = ZipdabangandroidTheme.Colors.Typo
            )
        }

        Box(modifier = Modifier.align(Alignment.CenterEnd)) {
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
            modifier = Modifier.align(Alignment.CenterEnd),
  //
            ) {

            if (isFollow) {
                isFollowDropDown.forEachIndexed {
                        index, text ->
                    DropdownMenuItem(
                        onClick = {
                            isContextMenuVisible = false
                            if(index == 0) followOrCancelClick()
                            else userReport()

                        }
                    ) {
                        Text(
                            text = text, style = ZipdabangandroidTheme.Typography.fourteen_500,
                            color = ZipdabangandroidTheme.Colors.Typo
                        )
                    }
                }

            } else {
                if (isFollowEach) {
                    //맞팔로우된 상태
                    DropdownMenuItem(
                        onClick = {
                            isContextMenuVisible = false
                        }
                    ) {
                        Text(
                            text = "신고하기",
                            style = ZipdabangandroidTheme.Typography.fourteen_500,
                            color = ZipdabangandroidTheme.Colors.Typo
                        )
                    }
                } else {
                    //맞팔로우 아닌 상태
                    isFollowingDropDown.forEachIndexed { index, text ->
                        DropdownMenuItem(
                            onClick = {
                                isContextMenuVisible = false
                                if(index == 0) followOrCancelClick()
                                else userReport()
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

        }
        }




}
        }




//
//@Composable
//fun FriendDropDown(){
//
//
//
//}
