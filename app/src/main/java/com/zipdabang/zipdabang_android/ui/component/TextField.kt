package com.zipdabang.zipdabang_android.ui.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

//textfield -> api를 통해 error와 correct check가 필요할때 + checkicon 필요할때
@SuppressLint("SuspiciousIndentation")
@Composable
fun TextFieldErrorAndCorrectIcon(
    value : String,
    onValueChanged : (String) -> Unit,
    isTried : Boolean, //api 시도했으면 true, !!!!!이걸 true로 설정해야 error와 check가 표시되요!!!!!
    labelValue : String,
    placeHolderValue : String,

    isError : Boolean,
    isCorrect : Boolean,
    errorMessage : String,
    correctMessage : String,

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
               if(isCorrect && isTried){
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


//textfield -> api를 통해 error와 correct check가 필요할때 + checkicon 필요없을때
@SuppressLint("SuspiciousIndentation")
@Composable
fun TextFieldErrorAndCorrect(
    value : String,
    maxLength: Int,
    onValueChanged : (String, Int) -> Unit,
    isTried : Boolean, //api 시도했으면 true, !!!!!이걸 true로 설정해야 error와 check가 표시되요!!!!!
    labelValue : String,
    placeHolderValue : String,

    isError : Boolean,
    isCorrect : Boolean,
    errorMessage : String,
    correctMessage : String,

    keyboardType : KeyboardType,
    imeAction : ImeAction,
) {
    //var isFocused by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = { onValueChanged(it , maxLength) },
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
    )
}


//textfield -> local에서 error check만 하면 될때
@SuppressLint("SuspiciousIndentation")
@Composable
fun TextFieldError(
    value : String,
    maxLength: Int,
    onValueChanged : (String, Int) -> Unit,
    labelValue : String,
    placeHolderValue: String,

    isError : Boolean,
    errorMessage : String,

    keyboardType : KeyboardType,
    imeAction : ImeAction,
) {
        TextField(
            value = value,
            onValueChange = {
                onValueChanged(it, maxLength)
            },
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
fun PreviewTextField(){
    var textState by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ){
        TextFieldError(
            value = textState,
            maxLength= 10,
            onValueChanged = { newText, maxLength ->
                if(newText.length <= maxLength){
                    textState = newText
                }
            },
            labelValue = "생년월일",
            placeHolderValue = "6자리 입력부탁",
            isError = false,
            errorMessage = "생년월일 형식이 아닙니다.",
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        )
        TextFieldErrorAndCorrectIcon(
            value = textState,
            onValueChanged = { textState = it },
            isTried = false,
            labelValue = "닉네임",
            placeHolderValue = "2-6자 한글, 영어, 숫자",
            isError = false,
            isCorrect = true,
            errorMessage = "닉네임에 맞지 않습니다.",
            correctMessage = "닉네임에 맞습니다.",
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        )
    }
}


//textfield - for content -> 하현이가 맘껏 고치면서 쓰면 될듯
@Composable
fun TextFieldForDrawerSingleline( //한줄 textfield
    value : String,
    onValueChanged: (String, Int) -> Unit,
    placeholderValue: String,
   //errorMessage : String,
    maxLength : Int, //최대 글자수
    isError : Boolean,
    imeAction: ImeAction, //default,none이면 엔터키, next면 다음 텍스트필드로 넘어감, done면 완료키
){
       OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChanged(it, maxLength)
            },
            textStyle = ZipdabangandroidTheme.Typography.sixteen_300,
            modifier = Modifier.fillMaxSize(),
            placeholder = {
                    Text(
                        text = placeholderValue,
                        style = ZipdabangandroidTheme.Typography.sixteen_300,
                        color = ZipdabangandroidTheme.Colors.Typo.copy(0.5f)
                    )
            },
            isError = isError,
            /*label = {
                //에러일때 label
                if(isError){
                    Text(
                        text = errorMessage,
                        style = ZipdabangandroidTheme.Typography.twelve_500
                    )
                }
            },*/
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
                focusedContainerColor = Color(0xFFF7F6F6),//ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
                focusedBorderColor = ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
                unfocusedContainerColor = Color(0xFFF7F6F6),//ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
                unfocusedBorderColor = ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
                errorCursorColor = Color(0xFFB00020),
                errorContainerColor = Color(0xFFF7F6F6),// ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = imeAction,
            ),
        )
}

@Composable
fun TextFieldForDrawerMultiline( //여러줄 textfield -> height이랑 maxLines를 조정해가며 개발하면 됩니다!!!
    value : String,
    onValueChanged: (String, Int) -> Unit,
    placeholderValue: String,
    //errorMessage : String,
    height : Dp,
    maxLines : Int,
    maxLength : Int, //최대 글자수
    isError : Boolean,
    imeAction: ImeAction, //default,none이면 엔터키, next면 다음 텍스트필드로 넘어감, done면 완료키
) {
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
        isError = isError,
        /*label = {
            //에러일때 label
            if(isError){
                Text(
                    text = errorMessage,
                    style = ZipdabangandroidTheme.Typography.twelve_500
                )
            }
        },*/
        singleLine = false,
        maxLines = maxLines,
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
            focusedContainerColor = Color(0xFFF7F6F6),//ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
            focusedBorderColor = ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
            unfocusedContainerColor = Color(0xFFF7F6F6),//ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
            unfocusedBorderColor = ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
            errorCursorColor = Color(0xFFB00020),
            errorContainerColor = Color(0xFFF7F6F6),// ZipdabangandroidTheme.Colors.Typo.copy(0.1f),
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = imeAction,
        ),
    )
}

@Preview
@Composable
fun PreviewTextFieldForDrawerSingleline(){
    var textState by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(16.dp)
            .height(70.dp)
            .fillMaxWidth()
    ){
        TextFieldForDrawerSingleline(
            value = textState,
            onValueChanged = { newText, maxLength ->
                if(newText.length <= maxLength){
                    textState = newText
                }
            },
            placeholderValue = "레시피 제목 (최대 20자)",
            //errorMessage = "이메일 형식에 맞지 않습니다",
            maxLength = 8,
            isError = if(textState == "ㅁㄴㅇㄹ") true else false,
            imeAction = ImeAction.Default,
        )
        TextFieldForDrawerMultiline(
            value =textState ,
            onValueChanged = { newText, maxLength ->
                if(newText.length <= maxLength){
                    textState = newText
                }
            },
            placeholderValue = "레시피 제목 (최대 20자)",
            height = 300.dp,
            maxLines = 3,
            maxLength = 100,
            isError = if(textState == "ㅁㄴㅇㄹ") true else false,
            imeAction = ImeAction.Default,
        )
    }
}




// textfiled - for recipewrite
@Composable
fun TextFieldForRecipeWriteSingleline(
    value : String,
    onValueChanged: (String, Int) -> Unit,
    placeholderValue: String,
    maxLength : Int, //최대 글자수
    imeAction: ImeAction, //default,none이면 엔터키, next면 다음 텍스트필드로 넘어감, done면 완료키
    onClickTrailingicon : () ->Unit,
){
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = {
            onValueChanged(it, maxLength)
        },
        textStyle = ZipdabangandroidTheme.Typography.sixteen_300,
        modifier = Modifier
            .fillMaxSize()
            .onFocusChanged {
                isFocused = it.isFocused
                Log.e("isFocused", "${isFocused}")
            },
        placeholder = {
            Text(
                text = placeholderValue,
                style = ZipdabangandroidTheme.Typography.sixteen_300,
                color = ZipdabangandroidTheme.Colors.Typo.copy(0.5f)
            )
        },
        singleLine = true,
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
        trailingIcon = {
            if(!value.isEmpty() && isFocused){
                Icon(
                    painter = painterResource(R.drawable.ic_recipewrite_btn_cancel),
                    contentDescription = "Icon",
                    tint = ZipdabangandroidTheme.Colors.Strawberry,
                    modifier = Modifier
                        .size(18.dp)
                        .padding(0.dp)
                        .clickable(
                            onClick = { onClickTrailingicon() }
                        ),
                )
            } else {

            }
        }
    )
}

@Composable
fun TextFieldForRecipeWriteMultiline(
    value : String,
    onValueChanged: (String, Int) -> Unit,
    placeholderValue: String,
    height : Dp,
    maxLines : Int,
    maxLength : Int, //최대 글자수
    imeAction: ImeAction, //default,none이면 엔터키, next면 다음 텍스트필드로 넘어감, done면 완료키
) {
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
}

@Preview
@Composable
fun PreviewTextFieldForRecipeWrite(){
    var textState by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .height(70.dp)
            .fillMaxWidth()
    ){
        TextFieldForRecipeWriteSingleline(
            value = textState,
            onValueChanged = { newText, maxLength ->
                if(newText.length <= maxLength){
                    textState = newText
                }
            },
            placeholderValue = "레시피 제목 (최대 20자)",
            maxLength = 8,
            imeAction = ImeAction.Default,
            onClickTrailingicon = {}
        )
        TextFieldForRecipeWriteMultiline(
            value = textState,
            onValueChanged = { newText, maxLength ->
                if(newText.length <= maxLength){
                    textState = newText
                }
            },
            placeholderValue = "레시피 제목 (최대 20자)",
            maxLines = 1,
            maxLength = 8,
            height = 200.dp,
            imeAction = ImeAction.Default,
        )
    }
}