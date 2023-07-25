package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.layout.Box
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
    textState : MutableState<String>,
    expectedText : String,
    labelText : String,
    placeholderText : String,
    errorMessage : String,
    rightMessage : String,
    keyboardType : KeyboardType,
    imeAction : ImeAction, //default,none이면 엔터키, next면 다음 텍스트필드로 넘어감, done면 완료키
) {
    //var textState by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    fun isTextMatching(text: String): Boolean {
        return text == expectedText
    }

    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        TextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            textStyle = ZipdabangandroidTheme.Typography.sixteen_300,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused },
            label = {
                if (textState.value.isEmpty() && !isFocused) {
                    Text(
                        text = labelText,
                        style = ZipdabangandroidTheme.Typography.sixteen_300,
                        color = ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f)
                    )
                } else if(textState.value.isEmpty() && isFocused){

                } else if (isTextMatching(textState.value)) {
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
            },
            placeholder = {
                Text(
                    text = placeholderText,
                    style = ZipdabangandroidTheme.Typography.sixteen_300,
                    color = ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f)
                )
            },
            isError = !isTextMatching(textState.value) && textState.value.isNotEmpty(),
            singleLine = true,
            //maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                textColor = ZipdabangandroidTheme.Colors.Typo,
                containerColor = Color(0xFFF7F6F6),
                unfocusedIndicatorColor = //밑줄
                if (isTextMatching(textState.value)) {
                    Color(0xFF6200EE)
                } else {
                    ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f)
                } ,
                placeholderColor = ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f),
                cursorColor = //쓸때 커서
                if (isTextMatching(textState.value)) {
                    Color(0xFF6200EE)
                } else {
                    ZipdabangandroidTheme.Colors.Typo
                } ,
                focusedLabelColor = ZipdabangandroidTheme.Colors.Typo, //쓸때 위에
                focusedIndicatorColor = //쓸때 밑줄
                if (isTextMatching(textState.value)) {
                    Color(0xFF6200EE)
                } else if (textState.value.isEmpty()){
                    ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f)
                } else {
                    Color(0xFFB00020) },
                errorCursorColor = Color(0xFFB00020), //에러 커서
                errorLabelColor = Color(0xFFB00020),  //에러 위에
                errorIndicatorColor = Color(0xFFB00020), //에러 밑줄
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType, //email,text 등이 있다.
                imeAction = imeAction, //done,default은 완료 키가 나온다. none이 엔터 키가 나온다.
            ),
            //visualTransformation = PasswordVisualTransformation(),
            trailingIcon = {
                if(isTextMatching(textState.value)){
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "check icon",
                        tint =  Color(0xFF6200EE),
                        modifier = Modifier
                            .size(22.dp))
                } },
        )
    }
}

//5번 -> 완성
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldForContent(
    textState : MutableState<String>,
    singleLine: Boolean,
    maxLines : Int,
    height : Dp,
    placeholderText: String,
    imeAction: ImeAction, //default,none이면 엔터키, next면 다음 텍스트필드로 넘어감, done면 완료키
    maxLength : Int, //최대 글자수
){
    Box(
        modifier = Modifier.padding(16.dp)
    ){
        OutlinedTextField(
            value = textState.value,
            onValueChange = {newText ->
                if(newText.length <= maxLength){
                    textState.value = newText
                }
            },
            //{textState.value = it },
            textStyle = ZipdabangandroidTheme.Typography.sixteen_500,
            modifier = Modifier
                .fillMaxWidth()
                .height(height),
            placeholder = {
                Text(text = placeholderText,
                    style = ZipdabangandroidTheme.Typography.sixteen_500,
                    color = ZipdabangandroidTheme.Colors.Typo.copy(0.5f)
                ) },
            singleLine = singleLine,
            maxLines = maxLines,
            colors = TextFieldDefaults.textFieldColors(
                textColor = ZipdabangandroidTheme.Colors.Typo,
                containerColor =  Color(0xFFF7F6F6),
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
fun PreviewTextFieldBasic(){
    var textState = remember { mutableStateOf("") }
    TextFieldBasic(textState,"ㅁㄴㅇㄹ","이름","이름", "회원정보가 잘못됐습니다","맞습니다", KeyboardType.Text, ImeAction.Next)
}


@Preview
@Composable
fun PreviewTextFieldForContent(){
    var textState = remember { mutableStateOf("") }
    TextFieldForContent(textState, false,7, 100.dp,"레시피 제목 (최대 20자)", ImeAction.Default,5)
}