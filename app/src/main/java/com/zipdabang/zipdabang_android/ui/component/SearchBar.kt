package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.search.SearchViewModel
import com.zipdabang.zipdabang_android.ui.theme.SearchBorder
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchViewModel: SearchViewModel = viewModel()
) {

    var text by remember { mutableStateOf("") }

        androidx.compose.material3.SearchBar(
            modifier = Modifier
                .wrapContentHeight()
                .clip(ZipdabangandroidTheme.Shapes.small)
                .border(1.dp, SearchBorder, ZipdabangandroidTheme.Shapes.small),
            query = text,
            onQueryChange = {
                text = it
            },
            onSearch = {
                searchViewModel.log(it)
            },
            onActiveChange = {
            },
            active = false,
            placeholder = {
                Text(text = "찾아보기")
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier
                        .noRippleClickable {
                            searchViewModel.log(text)
                        }
                )

            },
          //  shape = ZipdabangandroidTheme.Shapes.small,
            colors = SearchBarDefaults.colors(
                containerColor =
                ZipdabangandroidTheme.Colors.SubBackground
            )
        )
        {
        }
    }


inline fun Modifier.noRippleClickable(crossinline onClick: ()->Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview(){
    SearchBar()
}