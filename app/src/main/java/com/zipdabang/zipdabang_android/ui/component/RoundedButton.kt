package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

//4번 -> 매개변수 수정
@Composable
fun RoundedButton(
    buttonText : String,
    //onclick
){
    var isToggled by remember { mutableStateOf(false) }

    val containerColor = if (isToggled) ZipdabangandroidTheme.Colors.Strawberry else Color.White
    val textColor = if (isToggled) Color.White else ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f)
    val borderColor = if (isToggled) ZipdabangandroidTheme.Colors.Strawberry else ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f)

    Button(
        onClick = { isToggled = !isToggled },
        shape = ZipdabangandroidTheme.Shapes.large,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
        ),
        border = BorderStroke(1.dp, borderColor),
        contentPadding = PaddingValues(4.dp, 0.dp, 4.dp, 0.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Box(){
               //하현, 3번 component
            }
            Text(
                text = buttonText,
                color = textColor,
                style = ZipdabangandroidTheme.Typography.eighteen_300,
                modifier = Modifier.padding(0.dp, 0.dp, 2.dp, 0.dp)
            )
        }
    }
}

/*@Composable
fun CustomBox(){
    Box(modifier = Modifier
        .size(24.dp)
        .background(color = Color.Gray, shape = ZipdabangandroidTheme.Shapes.circle))
}
@Preview
@Composable
fun PreviewCustomBox(){
    CustomBox()
}*/

@Preview
@Composable
fun PreviewRoundedRectangle(){
    RoundedButton( buttonText = "생과일 음료" )
}