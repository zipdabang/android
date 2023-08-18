package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

//2번 -> 완성
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldBasic(
    value : String,
    onValueChanged : (String) -> Unit,
    expectedValue : String,
    labelText : String,
    placeholderText : String,
    errorMessage : String,
    rightMessage : String,
    keyboardType : KeyboardType,
    imeAction : ImeAction, //default,none이면 엔터키, next면 다음 텍스트필드로 넘어감, done면 완료키
) {
    //var isFocused by remember { mutableStateOf(false) }

    fun isTextMatching(text: String): Boolean {
        return text == expectedValue
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = value,
            onValueChange = { onValueChanged(it) },
            textStyle = ZipdabangandroidTheme.Typography.sixteen_300,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF7F6F6)),
                //.onFocusChanged { isFocused = it.isFocused },
            /*label = {
                if (value.isEmpty() && !isFocused) {
                    Text(
                        text = labelText,
                        style = ZipdabangandroidTheme.Typography.sixteen_300,
                        color = ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f)
                    )
                } else if(value.isEmpty() && isFocused){

                } else if (isTextMatching(value)) {
                    Text(
                        text = rightMessage,
                        style = ZipdabangandroidTheme.Typography.twelve_300,
                        color = Color(0xFF6200EE)
                    )
                } else {
                    Text(
                        text = errorMessage,
                        style = ZipdabangandroidTheme.Typography.twelve_300,
                        color = Color(0xFFB00020)
                    )
                }
            },*/
            placeholder = {
                Text(
                    text = placeholderText,
                    style = ZipdabangandroidTheme.Typography.sixteen_300,
                    color = ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f)
                )
            },
            isError = !isTextMatching(value) && value.isNotEmpty(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = //밑줄
                if (isTextMatching(value)) {
                    Color(0xFF6200EE)
                } else {
                    ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f)
                } ,
                cursorColor = //쓸때 커서
                if (isTextMatching(value)) {
                    Color(0xFF6200EE)
                } else {
                    ZipdabangandroidTheme.Colors.Typo
                } ,
                focusedLabelColor = ZipdabangandroidTheme.Colors.Typo, //쓸때 위에
                focusedIndicatorColor = //쓸때 밑줄
                if (isTextMatching(value)) {
                    Color(0xFF6200EE)
                } else if (value.isEmpty()){
                    ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f)
                } else {
                    Color(0xFFB00020) },
                errorCursorColor = Color(0xFFB00020), //에러 커서
                errorContainerColor = Color(0xFFF7F6F6),
                errorLabelColor = Color(0xFFB00020),  //에러 위에
                errorIndicatorColor = Color(0xFFB00020), //에러 밑줄
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType, //email,text 등이 있다.
                imeAction = imeAction, //done,default은 완료 키가 나온다. none이 엔터 키가 나온다.
            ),
            //visualTransformation = PasswordVisualTransformation(),
            trailingIcon = {
                if(isTextMatching(value)){
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "check icon",
                        tint =  Color(0xFF6200EE),
                        modifier = Modifier
                            .size(22.dp)
                    )
                }
            },
        )
    }
}

@Preview
@Composable
fun PreviewTextFieldBasic(){
    var textState by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.padding(16.dp)
    ){
        TextFieldBasic(
            textState,
            onValueChanged = { textState = it },
            "ㅁㄴㅇㄹ",
            "이름",
            "이름",
            "회원정보가 잘못됐습니다",
            "맞습니다",
            KeyboardType.Text,
            ImeAction.Next
        )
    }
}



//5번 -> 완성
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldForContent(
    value : String,
    onValueChanged : (String, Int) -> Unit,
    singleLine: Boolean,
    maxLines : Int,
    placeholderText: String,
    imeAction: ImeAction, //default,none이면 엔터키, next면 다음 텍스트필드로 넘어감, done면 완료키
    maxLength : Int, //최대 글자수
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChanged(it ,maxLength)
            },
            textStyle = ZipdabangandroidTheme.Typography.sixteen_500,
            modifier = Modifier.fillMaxSize()
                .background(Color(0xFFF7F6F6)),
            placeholder = {
                Text(
                    text = placeholderText,
                    style = ZipdabangandroidTheme.Typography.sixteen_500,
                    color = ZipdabangandroidTheme.Colors.Typo.copy(0.5f)
                )
            },
            singleLine = singleLine,
            maxLines = maxLines,
            colors = TextFieldDefaults.colors(
                cursorColor = ZipdabangandroidTheme.Colors.Typo,  //쓸때 커서
                unfocusedIndicatorColor = ZipdabangandroidTheme.Colors.Typo.copy(0.2f),
                focusedIndicatorColor = ZipdabangandroidTheme.Colors.Typo.copy(0.2f),
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = imeAction,
            ),
        )
    }
}

@Preview
@Composable
fun PreviewTextFieldForContent(){
    var textState by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.padding(16.dp)
    ){
        TextFieldForContent(
            textState,
            onValueChanged = { newText, maxLength ->
                if(newText.length <= maxLength){
                    textState = newText
                }
            },
            false,
            7,
            "레시피 제목 (최대 20자)",
            ImeAction.Default,
            80
        )
    }
}