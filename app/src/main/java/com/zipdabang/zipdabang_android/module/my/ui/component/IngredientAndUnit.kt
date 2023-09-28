package com.zipdabang.zipdabang_android.module.my.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun IngredientAndUnit(
    valueIngredient : String,
    onValueChangedIngredient: (String, Int) -> Unit,
    placeholderValueIngredient: String,
    maxLengthIngredient : Int,
    imeActionIngredient: ImeAction, //default,none이면 엔터키, next면 다음 텍스트필드로 넘어감, done면 완료키
    onClickTrailingiconIngredient : () ->Unit,
    onClickCancelIngredient : ()->Unit,
    valueUnit : String,
    onValueChangedUnit: (String, Int) -> Unit,
    placeholderValueUnit: String,
    maxLengthUnit : Int,
    imeActionUnit: ImeAction, //default,none이면 엔터키, next면 다음 텍스트필드로 넘어감, done면 완료키
    onClickTrailingiconUnit : () ->Unit,
) {
    var isFocusedIngredient by remember { mutableStateOf(false) }
    var isFocusedUnit by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Box(modifier = Modifier.weight(3f)){
            OutlinedTextField(
                value = valueIngredient,
                onValueChange = {
                    onValueChangedIngredient(it, maxLengthIngredient)
                },
                textStyle = ZipdabangandroidTheme.Typography.sixteen_300,
                modifier = Modifier
                    .fillMaxSize()
                    .onFocusChanged {
                        isFocusedIngredient = it.isFocused
                    },
                placeholder = {
                    Text(
                        text = placeholderValueIngredient,
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
                    imeAction = imeActionIngredient,
                ),
                trailingIcon = {
                    if(!valueIngredient.isEmpty() && isFocusedIngredient){
                        Icon(
                            painter = painterResource(R.drawable.ic_recipewrite_btn_cancel),
                            contentDescription = "Icon",
                            tint = ZipdabangandroidTheme.Colors.Strawberry,
                            modifier = Modifier
                                .size(18.dp)
                                .padding(0.dp)
                                .clickable(
                                    onClick = { onClickTrailingiconIngredient() }
                                ),
                        )
                    } else { }
                }
            )
        }
        Box(modifier = Modifier.weight(3f)){
            OutlinedTextField(
                value = valueUnit,
                onValueChange = {
                    onValueChangedUnit(it, maxLengthUnit)
                },
                textStyle = ZipdabangandroidTheme.Typography.sixteen_300,
                modifier = Modifier
                    .fillMaxSize()
                    .onFocusChanged {
                        isFocusedUnit = it.isFocused
                    },
                placeholder = {
                    Text(
                        text = placeholderValueUnit,
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
                    imeAction = imeActionUnit,
                ),
                trailingIcon = {
                    if(!valueUnit.isEmpty() && isFocusedUnit){
                        Icon(
                            painter = painterResource(R.drawable.ic_recipewrite_btn_cancel),
                            contentDescription = "Icon",
                            tint = ZipdabangandroidTheme.Colors.Strawberry,
                            modifier = Modifier
                                .size(18.dp)
                                .padding(0.dp)
                                .clickable(
                                    onClick = { onClickTrailingiconUnit() }
                                ),
                        )
                    } else {

                    }
                }
            )
        }
        Icon(
            modifier = Modifier
                .weight(1f)
                .size(30.dp)
                .padding(0.dp)
                .align(Alignment.CenterVertically)
                .clickable(
                    onClick = { onClickCancelIngredient() }
                ),
            painter = painterResource(R.drawable.ic_recipewrite_trashcan),
            contentDescription = "Icon",
            tint = ZipdabangandroidTheme.Colors.Typo,
        )
    }
}

@Preview
@Composable
fun PreviewIngredientAndUnit() {
    var textStateIngredient by remember { mutableStateOf("") }
    var textStateUnit by remember { mutableStateOf("") }

    IngredientAndUnit(
        valueIngredient = textStateIngredient,
        onValueChangedIngredient = { newText, maxLength ->
            if (newText.length <= maxLength) {
                textStateIngredient = newText
            }
        },
        placeholderValueIngredient = stringResource(id = R.string.my_recipewrite_milk),
        maxLengthIngredient = 10,
        imeActionIngredient = ImeAction.Default,
        onClickTrailingiconIngredient = {
            textStateIngredient = ""
        },
        onClickCancelIngredient = {

        },
        valueUnit = textStateUnit,
        onValueChangedUnit = { newText, maxLength ->
            if (newText.length <= maxLength) {
                textStateUnit = newText
            }
        },
        placeholderValueUnit = stringResource(id = R.string.my_recipewrite_hundredmilli),
        maxLengthUnit = 10,
        imeActionUnit = ImeAction.Default,
        onClickTrailingiconUnit = {
            textStateUnit = ""
        }
    )
}