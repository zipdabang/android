package com.zipdabang.zipdabang_android.ui.component

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

//textfield -> api를 통해 error와 correct check가 필요할때
@SuppressLint("SuspiciousIndentation")
@Composable
fun TextFieldErrorAndCorrect(
    value : String,
    onValueChanged : (String) -> Unit,
    isTried : Boolean,
    labelValue : String,
    placeHolderValue : String,

    isError : Boolean,
    isCorrect : Boolean,
    errorMessage : String,
    correctMessage : String,
    correctIcon : Boolean,

    keyboardType : KeyboardType,
    imeAction : ImeAction, //default,none이면 엔터키, next면 다음 텍스트필드로 넘어감, done면 완료키
) {
    //var isFocused by remember { mutableStateOf(false) }

        TextField(
            value = value,
            onValueChange = { onValueChanged(it) },
            textStyle = ZipdabangandroidTheme.Typography.sixteen_300,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF7F6F6)),
                //.onFocusChanged { isFocused = it.isFocused },
            label = {
                if (isCorrect && isTried) {
                    Text(
                        text = correctMessage,
                        style = ZipdabangandroidTheme.Typography.twelve_300,
                        color = Color(0xFF6200EE)
                    )
                } else if (isError && isTried){
                    Text(
                        text = errorMessage,
                        style = ZipdabangandroidTheme.Typography.twelve_300,
                        color = Color(0xFFB00020)
                    )
                } else {
                    Text(
                        text = labelValue,
                        style = ZipdabangandroidTheme.Typography.twelve_300,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }
            },
            placeholder = {
                Text(
                    text = placeHolderValue,
                    style = ZipdabangandroidTheme.Typography.sixteen_300,
                    color = ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f)
                )
            },
            isError = isError && isTried,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF7F6F6), //상자
                unfocusedIndicatorColor = //밑줄
                    if (isCorrect && isTried) Color(0xFF6200EE)
                    else ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f),

                focusedContainerColor = Color(0xFFF7F6F6), //쓸때 상자
                focusedLabelColor = ZipdabangandroidTheme.Colors.Typo, //쓸때 위에
                cursorColor = //쓸때 커서
                    if (isCorrect && isTried) Color(0xFF6200EE)
                    else ZipdabangandroidTheme.Colors.Typo,
                focusedIndicatorColor = //쓸때 밑줄
                    if (isCorrect && isTried) {
                        Color(0xFF6200EE)
                    } else if (isError && isTried) {
                        Color(0xFFB00020)
                    } else{
                        ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f)
                    },

                errorCursorColor = Color(0xFFB00020), //에러 커서
                errorLabelColor = Color(0xFFB00020),  //에러 위에
                errorIndicatorColor = Color(0xFFB00020), //에러 밑줄
                errorContainerColor = Color(0xFFF7F6F6), //에러 상자
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType, //email,text 등이 있다.
                imeAction = imeAction, //done,default은 완료 키가 나온다. none이 엔터 키가 나온다.
            ),
            //visualTransformation = PasswordVisualTransformation(),
            trailingIcon = {
                if(correctIcon){
                    if(isCorrect && isTried){
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "check icon",
                            tint =  Color(0xFF6200EE),
                            modifier = Modifier
                                .size(22.dp)
                        )
                    }
                }
                else{

                }
            },
        )
}

@Preview
@Composable
fun PreviewTextFieldErrorAndCorrect(){
    var textState by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.padding(16.dp)
    ){
        TextFieldErrorAndCorrect(
            value = textState,
            onValueChanged = { textState = it },
            isTried = false,
            labelValue = "닉네임",
            placeHolderValue = "2-6자 한글, 영어, 숫자",
            isError = false,
            isCorrect = true,
            errorMessage = "닉네임에 맞지 않습니다.",
            correctMessage = "닉네임에 맞습니다.",
            correctIcon = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        )

    }
}


//textfield -> local에서 error check만 하면 될때
@SuppressLint("SuspiciousIndentation")
@Composable
fun TextFieldBasic(
    value : String,
    onValueChanged : (String) -> Unit,
    labelValue : String,
    placeHolderValue: String,

    isError : Boolean,
    errorMessage : String,

    keyboardType : KeyboardType,
    imeAction : ImeAction, //default,none이면 엔터키, next면 다음 텍스트필드로 넘어감, done면 완료키
) {
        TextField(
            value = value,
            onValueChange = { onValueChanged(it) },
            textStyle = ZipdabangandroidTheme.Typography.sixteen_300,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF7F6F6)),
            //.onFocusChanged { isFocused = it.isFocused },
            label = {
                if (isError){
                    Text(
                        text = errorMessage,
                        style = ZipdabangandroidTheme.Typography.twelve_300,
                        color = Color(0xFFB00020)
                    )
                } else {
                    Text(
                        text = labelValue,
                        style = ZipdabangandroidTheme.Typography.twelve_300,
                        color = ZipdabangandroidTheme.Colors.Typo
                    )
                }
            },
            placeholder = {
                Text(
                    text = placeHolderValue,
                    style = ZipdabangandroidTheme.Typography.sixteen_300,
                    color = ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f)
                )
            },
            isError = isError,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFF7F6F6), //상자
                unfocusedIndicatorColor = ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f), //밑줄

                focusedContainerColor = Color(0xFFF7F6F6), //쓸때 상자
                focusedLabelColor = ZipdabangandroidTheme.Colors.Typo, //쓸때 위에
                cursorColor = ZipdabangandroidTheme.Colors.Typo, //쓸때 커서
                focusedIndicatorColor = //쓸때 밑줄
                if (isError) Color(0xFFB00020)
                else ZipdabangandroidTheme.Colors.Typo.copy(alpha = 0.5f),

                errorCursorColor = Color(0xFFB00020), //에러 커서
                errorLabelColor = Color(0xFFB00020),  //에러 위에
                errorIndicatorColor = Color(0xFFB00020), //에러 밑줄
                errorContainerColor = Color(0xFFF7F6F6), //에러 상자
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType, //email,text 등이 있다.
                imeAction = imeAction, //done,default은 완료 키가 나온다. none이 엔터 키가 나온다.
            ),
        )
}

@Preview
@Composable
fun PreviewTextFieldBasic(){
    var textState by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.padding(16.dp)
    ){
        TextFieldBasic(
            value = textState,
            onValueChanged = {textState = it},
            labelValue = "생년월일",
            placeHolderValue = "6자리 입력부탁",
            isError = false,
            errorMessage = "생년월일 형식이 아닙니다.",
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        )
    }
}



//textfield -> 글쓰기 같은 곳에 쓰임
@Composable
fun TextFieldForContent(
    value : String,
    onValueChanged : (String, Int) -> Unit,
    singleLine: Boolean,
    maxLines : Int,
    placeholderValue: String,
    imeAction: ImeAction, //default,none이면 엔터키, next면 다음 텍스트필드로 넘어감, done면 완료키
    maxLength : Int, //최대 글자수
){
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChanged(it, maxLength)
            },
            textStyle = ZipdabangandroidTheme.Typography.sixteen_500,
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF7F6F6)),
            placeholder = {
                Text(
                    text = placeholderValue,
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