package com.zipdabang.zipdabang_android.module.sign_up.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.ui.component.CheckBoxCustom
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun CheckBotWithTextAndButton(
    //"보기" 버튼 있는거 없는거 나누기
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ){
        var isChecked by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier.weight(0.1f)
                .size(24.dp),
            contentAlignment = Alignment.Center
        ){
            Box(
                modifier = Modifier.size(18.dp)
            ){
                CheckBoxCustom(
                    rounded =true,
                    isChecked  = isChecked,
                    isCheckedChange = {selectedToggled -> isChecked = selectedToggled })
            }
        }
        Spacer(
            modifier = Modifier.weight(0.01f)
        )
        Text(
            text = "[필수] 필수 제공 항목",
            modifier = Modifier.weight(0.7f),
            style = ZipdabangandroidTheme.Typography.fourteen_500,
            color = ZipdabangandroidTheme.Colors.Typo
        )
        Text(
            text= "보기",
            textAlign = TextAlign.Center,
            style = ZipdabangandroidTheme.Typography.fourteen_300,
            color = ZipdabangandroidTheme.Colors.Typo,
            modifier = Modifier.weight(0.2f)
                .clickable(onClick = { }),
        )

    }
}

@Preview
@Composable
fun PreviewCheckBoxWithTextAndButton(){
    Column(
        modifier = Modifier.padding(16.dp)
    ){
        CheckBotWithTextAndButton()
    }
}

@Composable
fun CheckBoxWithTextAndButtonColumn(

){

}
