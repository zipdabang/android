package com.zipdabang.zipdabang_android.module.search.ui

import android.util.Log
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
import androidx.navigation.NavController
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.core.navigation.SharedScreen
import com.zipdabang.zipdabang_android.module.search.data.SearchCategory
import com.zipdabang.zipdabang_android.module.search.data.dto.searchpreview.SearchCategoryList

@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel()

) {
    val scrollState = rememberScrollState()

    var keyword = searchViewModel.searchText.value

    val searchState= searchViewModel.searchState
    Column(
        modifier = Modifier
            .padding(top = 24.dp)
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
                com.zipdabang.zipdabang_android.ui.component.SearchBar(hintText = "찾는 레시피가 있으신가요?", keyword = keyword, viewModel = searchViewModel)
            }
        }
        var categoryList : List<SearchCategoryList> = emptyList()

        val categoryTitleList = SearchCategory.values()

        if(searchState.value.isLoading){
            //shimmering
        }else if(searchState.value.isError){
            Box(Modifier.height(60.dp)){

            }
            Log.e("SearchScreen Error",searchState.value.error)

        }else{
            categoryList = searchState.value.searchList

            categoryList.forEachIndexed{
                index, item ->
                SearchCategoryPreview(
                    title = categoryTitleList[index].categoryName,
                    previewList = categoryList[index].recipeList
                ) {
                    navController.navigate(
                        SharedScreen.SearchRecipeCategory.passQuery(
                            categoryId = index + 1,
                            keyword = keyword
                        )
                    ) {
                        launchSingleTop = true
                    }
                }
            }

        }










    }

}


@Preview
@Composable
fun SearchPreview(){
   // SearchScreen()
}

