package com.zipdabang.zipdabang_android.module.my.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.module.my.ui.viewmodel.MyForOthersViewModel
import com.zipdabang.zipdabang_android.ui.component.CircleImage
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun ProfileForOthers(
    viewModel : MyForOthersViewModel = hiltViewModel()
){

    val profileState = viewModel.profileState
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 16.dp)
    ) {

        Text(
            text = "한 줄 소개",
            style = ZipdabangandroidTheme.Typography.sixteen_700,
            color = ZipdabangandroidTheme.Colors.Typo
        )

        profileState.value.caption?.let {
            Text(
                text = it,
                style = ZipdabangandroidTheme.Typography.sixteen_500,
                color = ZipdabangandroidTheme.Colors.Typo
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "선호하는 카페 음료",
            style = ZipdabangandroidTheme.Typography.sixteen_700,
            color = ZipdabangandroidTheme.Colors.Typo
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            items(profileState.value.preferCategory) {
                Box(
                    modifier = Modifier.size(70.dp)
                ) {
                    CircleImage(
                        imageUrl = it.imageUrl,
                        contentDescription = it.name
                    )
                }
            }

        }



    }

}