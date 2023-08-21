package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

//4번 -> 완성, 얘는 size 설정 안해줘도 됨.
@Composable
fun RoundedButton(
    imageUrl : Any,
    buttonText : String,
    isClicked : Boolean,
    isClickedChange : (Boolean) -> Unit,
){
    var localIsClicked by remember { mutableStateOf(isClicked) }

    val containerColor = if (localIsClicked) ZipdabangandroidTheme.Colors.Strawberry else Color.White
    val textColor = if (localIsClicked) Color.White else ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f)
    val borderColor = if (localIsClicked) ZipdabangandroidTheme.Colors.Strawberry else ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f)

    Button(
        modifier = Modifier.padding(4.dp,0.dp,4.dp,0.dp),
        onClick = {localIsClicked = !localIsClicked
            isClickedChange(localIsClicked)},
        shape = ZipdabangandroidTheme.Shapes.large,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
        ),
        border = BorderStroke(1.dp, borderColor),
        contentPadding = PaddingValues(8.dp, 0.dp, 8.dp, 0.dp),
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.wrapContentSize()
        ){
            Box(
                modifier = Modifier.size(24.dp),
            ){
                CircleImage(
                    imageUrl = imageUrl ,
                    contentDescription = "음료 카테고리 사진"
                )
            }
            Text(
                text = buttonText,
                color = textColor,
                style = ZipdabangandroidTheme.Typography.eighteen_300,
                modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp)
            )
        }
    }
}


@Preview
@Composable
fun PreviewRoundedRectangle(){
    var isClicked by remember { mutableStateOf(true) }

    Box(modifier = Modifier.padding(4.dp)){
        RoundedButton(
            imageUrl = R.drawable.ic_launcher_foreground,
            buttonText = "생과일 음료" ,
            isClicked = isClicked,
            isClickedChange = { selectedClicked -> isClicked = selectedClicked}) //isClicked 값이 바뀌면서 재렌더링한다.
    }
}