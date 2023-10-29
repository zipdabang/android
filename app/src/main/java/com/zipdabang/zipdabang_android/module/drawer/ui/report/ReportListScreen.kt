package com.zipdabang.zipdabang_android.module.drawer.ui.report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                centerText = "오류문의 및 신고"
            )
        }
    ) {
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