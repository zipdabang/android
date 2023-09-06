package com.zipdabang.zipdabang_android.module.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.search.data.SearchCategory

@Composable
fun SearchScreen(
   searchViewModel: SearchViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.padding(top = 24.dp)
            .verticalScroll(scrollState),
    ) {
        Row(
            Modifier
                .height(55.dp)
                .padding(end = 16.dp))
        {
            Icon(
                painter = painterResource(id = R.drawable.search_back_small), null,
                modifier = Modifier.weight(1f),
                tint = Color(0xFF867768)
            )
            Spacer(modifier = Modifier.width(5.dp))

            Box(
                Modifier.weight(7f)
            ) {
                com.zipdabang.zipdabang_android.ui.component.SearchBar(hintText = "찾는 레시피가 있으신가요?")
            }
        }

        val categoryList = SearchCategory.values()

        categoryList.forEach{



        }








    }

}


@Preview
@Composable
fun SearchPreview(){
    SearchScreen()
}

