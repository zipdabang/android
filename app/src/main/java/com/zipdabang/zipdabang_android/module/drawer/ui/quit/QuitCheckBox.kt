package com.zipdabang.zipdabang_android.module.drawer.ui.quit

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.ui.component.CheckBoxRectangle
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme


@Composable
fun QuitCheckBoxComponent(
    reason : String,
    addReason : () -> Unit,
    removeReason : () -> Unit
){
    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(modifier = Modifier.size(24.dp)){

      CheckBoxRectangle(
          isChecked = isChecked,
          isCheckedChange =
        {
                selectedChecked ->

            isChecked = selectedChecked
            if(isChecked) addReason()
            else removeReason()
            Log.e("quit_log",selectedChecked.toString())


        }

    )
    }
        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text= reason,
            color = ZipdabangandroidTheme.Colors.Typo,
            style = ZipdabangandroidTheme.Typography.sixteen_500
        )


    }
}