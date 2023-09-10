package com.zipdabang.zipdabang_android.module.comment.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.comment.ui.RecipeCommentState
import com.zipdabang.zipdabang_android.ui.component.CircleImage
import com.zipdabang.zipdabang_android.ui.theme.DialogGray
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun CommentItem(
    commentItem: RecipeCommentState,
    onClickReport: (Int) -> Unit,
    onClickBlock: (Int) -> Unit,
    onClickEdit: (Int) -> Unit,
    onClickDelete: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.size(32.dp)) {
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

                IconButton(
                    modifier = Modifier.size(18.dp),
                    onClick = {
                        if (commentItem.isOwner) {

                        } else {

                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.all_more_white),
                        contentDescription = "comment menu",
                        tint = ZipdabangandroidTheme.Colors.Typo
                    )
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
