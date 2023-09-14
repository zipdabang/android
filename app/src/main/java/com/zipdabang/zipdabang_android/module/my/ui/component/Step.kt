package com.zipdabang.zipdabang_android.module.my.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.ImageWithIcon
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun Step(
    value: String,
    onValueChanged: (String, Int) -> Unit,
    placeholderValue: String,
    height: Dp,
    maxLines: Int,
    maxLength: Int, //최대 글자수
    imeAction: ImeAction, //default,none이면 엔터키, next면 다음 텍스트필드로 넘어감, done면 완료키
    onClickAddBtn: () -> Unit,
    onClickDeleteStep: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.my_recipewrite_step),
            style = ZipdabangandroidTheme.Typography.fourteen_500,
            color = ZipdabangandroidTheme.Colors.Typo
        )
        // Step 사진 추가 후
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1.6f)
                    .aspectRatio(1f)
            ) {
                ImageWithIcon(
                    imageUrl = R.drawable.ic_launcher_background,
                    onClick = {

                    }
                )
            }
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFF7F6F6),
                        shape = ZipdabangandroidTheme.Shapes.small
                    )
                    .border(
                        BorderStroke(1.dp, ZipdabangandroidTheme.Colors.Typo.copy(0.1f)),
                        ZipdabangandroidTheme.Shapes.small
                    )
                    .weight(8.4f),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_recipewrite_warning),
                        contentDescription = "Icon",
                        tint = Color(0xFFB00020),
                        modifier = Modifier
                            .size(16.dp)
                            .padding(4.dp, 0.dp, 0.dp, 0.dp),
                    )
                    Text(
                        modifier = Modifier.padding(2.dp, 0.dp, 0.dp, 0.dp),
                        text = stringResource(id = R.string.my_recipewrite_warning_step),
                        style = ZipdabangandroidTheme.Typography.twelve_500,
                        color = Color(0xFFB00020)
                    )
                }
            }
        }
        // Step 사진 추가 버튼
        Button(
            onClick = { onClickAddBtn() },
            shape = ZipdabangandroidTheme.Shapes.thin,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF7F6F6)
            )
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.my_recipewrite_step),
                    style = ZipdabangandroidTheme.Typography.sixteen_500,
                    color = ZipdabangandroidTheme.Colors.Typo
                )
            }
        }
        // Step textfield
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChanged(it, maxLength)
            },
            textStyle = ZipdabangandroidTheme.Typography.sixteen_300,
            modifier = Modifier
                .fillMaxSize()
                .height(height),
            placeholder = {
                Text(
                    text = placeholderValue,
                    style = ZipdabangandroidTheme.Typography.sixteen_300,
                    color = ZipdabangandroidTheme.Colors.Typo.copy(0.5f)
                )
            },
            singleLine = false,
            maxLines = maxLines,
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
                focusedContainerColor = Color(0xFFF7F6F6), //ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
                focusedBorderColor = ZipdabangandroidTheme.Colors.Strawberry,
                unfocusedContainerColor = Color(0xFFF7F6F6), //ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
                unfocusedBorderColor = ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
                errorCursorColor = Color(0xFFB00020),
                errorContainerColor = ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = imeAction,
            ),
        )
        Text(
            modifier = Modifier
                .align(Alignment.End)
                .padding(0.dp, 0.dp, 4.dp, 0.dp),
            text = "0/200",
            style = ZipdabangandroidTheme.Typography.fourteen_300,
            color = ZipdabangandroidTheme.Colors.Typo
        )
    }
}





@Preview
@Composable
fun PreviewStep() {
    var textStateStep by remember { mutableStateOf("") }
    Box(modifier = Modifier.background(Color.White)) {
        Step(
            value = textStateStep,
            onValueChanged = { newText, maxLength ->
                if (newText.length <= maxLength) {
                    textStateStep = newText
                }
            },
            placeholderValue = stringResource(id = R.string.my_recipewrite_step_hint),
            height = 100.dp,
            maxLines = 7,
            maxLength = 200,
            imeAction = ImeAction.None,
            onClickAddBtn = { },
            onClickDeleteStep = {}
        )
    }
}