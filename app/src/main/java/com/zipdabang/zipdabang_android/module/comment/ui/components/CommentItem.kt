package com.zipdabang.zipdabang_android.module.comment.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.IconButton
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.common.CommentMgtFailureException
import com.zipdabang.zipdabang_android.core.data_store.proto.CurrentPlatform
import com.zipdabang.zipdabang_android.core.data_store.proto.ProtoDataViewModel
import com.zipdabang.zipdabang_android.core.data_store.proto.Token
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentState
import com.zipdabang.zipdabang_android.module.recipes.common.ReportContent
import com.zipdabang.zipdabang_android.ui.component.CircleImage
import com.zipdabang.zipdabang_android.ui.component.CommentReportDialog
import com.zipdabang.zipdabang_android.ui.theme.DialogGray
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CommentItem(
    recipeId: Int,
    commentItem: RecipeCommentState,
    // 여기서 onClickEdit은 textfield 활성화를 의미
    onClickEdit: (Int, String) -> Unit,
    onClickDelete: (Int) -> Unit,
    onClickProfile: (Int) -> Unit,
    showCommentReport: (Int, Int, Int) -> Unit,
    showCommentBlock: (Int) -> Unit,
) {
    val tokenViewModel = hiltViewModel<ProtoDataViewModel>()

    suspend fun currentPlatform() = withContext(Dispatchers.IO) {
        tokenViewModel.tokens.first().platformStatus
    }

    val status = tokenViewModel.tokens
        .collectAsState(
            initial = Token(
                null,
                null,
                null,
                CurrentPlatform.TEMP,
                null)
        ).value.platformStatus

    var isExpandedForOwner by remember { mutableStateOf(false) }
    var isExpandedForNotOwner by remember { mutableStateOf(false) }


    var reportId by remember { mutableStateOf(1) }

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.size(32.dp).clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple()
        ) {
            onClickProfile(commentItem.ownerId)
        }) {
            CircleImage(
                imageUrl = commentItem.ownerImage, contentDescription = "profile image"
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = commentItem.ownerNickname,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                            color = ZipdabangandroidTheme.Colors.Typo,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = commentItem.createdAt,
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontFamily = FontFamily(Font(R.font.kopubworlddotum_light)),
                            color = DialogGray,
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (status != CurrentPlatform.TEMP && status != CurrentPlatform.NONE) {
                    Box(modifier = Modifier
                        .size(18.dp)
                        .wrapContentSize(Alignment.TopEnd)
                    ) {
                        IconButton(
                            modifier = Modifier.fillMaxSize(),
                            onClick = {
                                CoroutineScope(Dispatchers.Main).launch {
                                    val currentPlatform = currentPlatform()
                                    if (currentPlatform == CurrentPlatform.NONE
                                        || currentPlatform == CurrentPlatform.TEMP) {
                                        
                                    } else {
                                        if (commentItem.isOwner) {
                                            isExpandedForOwner = !isExpandedForOwner
                                        } else {
                                            isExpandedForNotOwner = !isExpandedForNotOwner
                                        }
                                    }
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.all_more_white),
                                contentDescription = "comment menu",
                                tint = ZipdabangandroidTheme.Colors.Typo
                            )
                        }

                        DropdownMenu(
                            modifier = Modifier.background(Color.White),
                            expanded = isExpandedForOwner,
                            onDismissRequest = { isExpandedForOwner = false },
                        ) {
                            DropdownMenuItem(
                                text = { Text("댓글 삭제하기") },
                                onClick = {
                                    try {
                                        onClickDelete(commentItem.commentId)
                                        isExpandedForOwner = !isExpandedForOwner
                                    } catch (e: CommentMgtFailureException) {
                                        Log.d("comment submit", "delete failure")
                                    } catch (e: Exception) {
                                        Log.d("comment submit", "delete failure")

                                    }
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("댓글 수정하기") },
                                onClick = {
                                    onClickEdit(commentItem.commentId, commentItem.content)
                                    isExpandedForOwner = !isExpandedForOwner
                                }
                            )
                        }

                        DropdownMenu(
                            modifier = Modifier.background(Color.White),
                            expanded = isExpandedForNotOwner,
                            onDismissRequest = { isExpandedForNotOwner = false },
                        ) {
                            DropdownMenuItem(
                                text = { Text("댓글 신고하기") },
                                onClick = {
                                    showCommentReport(commentItem.commentId, 1, commentItem.ownerId)
                                    isExpandedForNotOwner = !isExpandedForNotOwner
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("이용자 차단하기") },
                                onClick = {
                                    showCommentBlock(commentItem.ownerId)
                                    isExpandedForNotOwner = !isExpandedForNotOwner
                                }
                            )
                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(2.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = commentItem.content,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_light)),
                    color = ZipdabangandroidTheme.Colors.Typo,
                ),
            )
            
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun CommentItemPreview() {
    CommentItem(
        RecipeCommentState(
            "", "",
            true, "", "",
            1, 1)
    )
}*/
