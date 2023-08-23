package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun GroupHeader(
    modifier: Modifier = Modifier,
    groupName: String,
    formerHeaderStrawberry: String,
    latterHeaderChoco: String,
    onClick: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 26.dp,
                top = 10.dp,
                bottom = 10.dp
            )
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple()
            ) {
                onClick(groupName)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = AnnotatedString(
                    text = formerHeaderStrawberry,
                    spanStyle = SpanStyle(
                        color = ZipdabangandroidTheme.Colors.Strawberry,
                        fontFamily = FontFamily(Font(R.font.cafe24ssurroundair)),
                        fontSize = 16.sp
                    )
                ) + AnnotatedString(
                    text = latterHeaderChoco,
                    spanStyle = SpanStyle(
                        color = ZipdabangandroidTheme.Colors.Choco,
                        fontFamily = FontFamily(Font(R.font.cafe24ssurroundair)),
                        fontSize = 16.sp
                    )
                )
            )
        }
        Icon(
            modifier = Modifier
                .padding(12.dp),
            painter = painterResource(id = R.drawable.all_arrow_right),
            contentDescription = null,
            tint = ZipdabangandroidTheme.Colors.Choco
        )
    }
}

@Composable
fun GroupHeaderReversed(
    modifier: Modifier = Modifier,
    groupName: String,
    formerHeaderChoco: String,
    latterHeaderStrawberry: String,
    onClick: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 26.dp,
                top = 10.dp,
                bottom = 10.dp
            )
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = rememberRipple()
            ) {
                onClick(groupName)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = AnnotatedString(
                    text = formerHeaderChoco,
                    spanStyle = SpanStyle(
                        color = ZipdabangandroidTheme.Colors.Choco,
                        fontFamily = FontFamily(Font(R.font.cafe24ssurroundair)),
                        fontSize = 16.sp
                    )
                ) + AnnotatedString(
                    text = latterHeaderStrawberry,
                    spanStyle = SpanStyle(
                        color = ZipdabangandroidTheme.Colors.Strawberry,
                        fontFamily = FontFamily(Font(R.font.cafe24ssurroundair)),
                        fontSize = 16.sp
                    )
                )
            )
        }
        Icon(
            modifier = Modifier
                .padding(12.dp),
            painter = painterResource(id = R.drawable.all_arrow_right),
            contentDescription = null,
            tint = ZipdabangandroidTheme.Colors.Choco
        )
    }
}

@Preview
@Composable
fun GroupHeaderPreview() {
    Column {
        GroupHeader(
            formerHeaderStrawberry = "주간 베스트",
            latterHeaderChoco = " 레시피",
            groupName = "best"
        ) { groupName ->

        }
        Spacer(modifier = Modifier.height(16.dp))
        GroupHeaderReversed(
            formerHeaderChoco = "주간 베스트",
            latterHeaderStrawberry = " 레시피",
            groupName = "best"
        ) { groupName ->

        }
    }

}