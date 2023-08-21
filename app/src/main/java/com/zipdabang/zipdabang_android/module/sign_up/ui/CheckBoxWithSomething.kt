package com.zipdabang.zipdabang_android.module.sign_up.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.ui.component.CheckBoxCustom
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme


@Composable
fun CheckBoxWithText(
    isCheckBox: Boolean,
    isChecked : Boolean?,
    isCheckedChange : (Boolean) -> Unit,
    mainValue : String,
    mainTextStyle : TextStyle,
    isDetailValue : Boolean,
    detailValue : String?,
    detailTextStyle: TextStyle?,
){
    var isCheckedLocal by remember { mutableStateOf(isChecked) }
    Log.e("component-checkbox", "${isCheckedLocal}")

    if (isChecked != isCheckedLocal) {
        isCheckedLocal = isChecked
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ){
        Box(
            modifier = Modifier.weight(0.1f)
                .size(24.dp),
            contentAlignment = Alignment.Center
        ){
            if(isCheckBox == false){

            }else{
                Box(
                    modifier = Modifier.size(18.dp)
                ){
                    if (isCheckedLocal != null) {
                        CheckBoxCustom(
                            rounded =true,
                            isChecked  = isCheckedLocal!!,
                            isCheckedChange = {
                                isCheckedLocal = it
                                isCheckedChange(it)
                            }
                        )
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier.weight(0.01f)
        )
        Text(
            text = mainValue,
            modifier = Modifier.weight(0.9f),
            style = mainTextStyle,
            color = ZipdabangandroidTheme.Colors.Typo
        )
    }
    if(isDetailValue == false){

    }else{
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Box(
                modifier = Modifier.weight(0.1f)
                    .size(24.dp),
            )
            Spacer(
                modifier = Modifier.weight(0.01f)
            )
            if (detailValue != null) {
                if (detailTextStyle != null) {
                    Text(
                        text = detailValue,
                        style = detailTextStyle,
                        color = ZipdabangandroidTheme.Colors.Typo,
                        modifier = Modifier.weight(0.9f),
                    )
                }
            }
        }
    }
}


@Composable
fun CheckBoxWithTextAndButton(
    isChecked : Boolean,
    isCheckedChange : (Boolean) -> Unit,
    mainValue : String,
    mainTextStyle: TextStyle,
    onClick : ()->Unit,
){
    var isCheckedLocal by remember { mutableStateOf(isChecked) }
    Log.e("component-checkbox", "${isCheckedLocal}")

    if (isChecked != isCheckedLocal) {
        isCheckedLocal = isChecked
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ){
        //var isChecked by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier.weight(0.1f)
                .size(24.dp),
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier.size(18.dp)
            ){
                CheckBoxCustom(
                    rounded = true,
                    isChecked  = isCheckedLocal,
                    isCheckedChange = {
                        isCheckedLocal = it
                        isCheckedChange(it)
                    }
                )
            }
        }
        Spacer(
            modifier = Modifier.weight(0.01f)
        )
        Text(
            text = mainValue,
            modifier = Modifier.weight(0.7f),
            style = mainTextStyle,
            color = ZipdabangandroidTheme.Colors.Typo
        )

        ClickableText(
            modifier = Modifier.weight(0.2f),
            text= AnnotatedString(
                text="보기",
                spanStyle = SpanStyle(
                    color = ZipdabangandroidTheme.Colors.Typo
                ),
                paragraphStyle = ParagraphStyle(
                    textAlign = TextAlign.End
                )
            ),
            onClick={ onClick() },
            style = ZipdabangandroidTheme.Typography.fourteen_300,
        )

    }
}

@Preview
@Composable
fun PreviewCheckBoxWithTextAndButton(){
    var isChecked by remember { mutableStateOf(true) }
    var isCheckedSecond by remember { mutableStateOf(true) }


    Column(
        modifier = Modifier.padding(16.dp)
    ){
        CheckBoxWithText(
            isCheckBox= false,
            isChecked = isCheckedSecond,
            isCheckedChange = {selectedChecked -> isCheckedSecond = selectedChecked },
            mainValue = "[필수] 필수 제공 항목",
            mainTextStyle = ZipdabangandroidTheme.Typography.fourteen_700,
            isDetailValue = true,
            detailValue = "어쩌구저쩌구",
            detailTextStyle = ZipdabangandroidTheme.Typography.twelve_300,
        )
        CheckBoxWithTextAndButton(
            isChecked = isChecked,
            isCheckedChange = {selectedChecked -> isChecked = selectedChecked },
            mainValue = "[필수] 필수 제공 항목",
            mainTextStyle = ZipdabangandroidTheme.Typography.fourteen_500,
            onClick = { }
        )

    }
}

