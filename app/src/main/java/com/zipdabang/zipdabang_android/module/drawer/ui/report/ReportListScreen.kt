package com.zipdabang.zipdabang_android.module.drawer.ui.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.drawer.ui.component.ReportItem
import com.zipdabang.zipdabang_android.module.drawer.ui.viewmodel.ErrorListViewModel
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun ReportListScreen(
    onReportClick : (Int) -> Unit,
    onClickBack : () -> Unit,
    viewModel : ErrorListViewModel = hiltViewModel()
) {

    val reportItems = viewModel.inqueryItems.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = {  onClickBack() },
                centerText = "나의 문의내역"
            )
        }
    ) {
        if(reportItems.itemCount == 0){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    textAlign = TextAlign.Center,
                    text= "문의한 내역이 없습니다.",
                    style= ZipdabangandroidTheme.Typography.fourteen_300,
                    color= ZipdabangandroidTheme.Colors.Typo
                )
            }
        }
        else{
            LazyColumn(
                modifier = Modifier
                    .background(Color.White)
                    .padding(
                        top = it.calculateTopPadding(),
                        start = 16.dp, end = 16.dp
                    )
                    .fillMaxSize()

            ) {
                items(reportItems.itemCount){
                    Column {

                        ReportItem(
                            title = reportItems[it]!!.title,
                            createdAt = reportItems[it]!!.createdAt,
                            onClick = {
                                onReportClick(reportItems[it]!!.id)
                            }
                        )

                        Divider(
                            modifier = Modifier.fillMaxWidth(),
                            color = ZipdabangandroidTheme.Colors.Typo.copy(0.2f),
                        )
                    }
                }
            }
        }
    }

}