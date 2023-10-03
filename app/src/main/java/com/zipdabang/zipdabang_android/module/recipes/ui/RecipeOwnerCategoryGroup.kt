package com.zipdabang.zipdabang_android.module.recipes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.module.recipes.common.OwnerCategory
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RecipeOwnerCategoryGroup(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
    ownerTypeList: List<OwnerCategory>,
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .background(color = ZipdabangandroidTheme.Colors.Cream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ownerTypeList.forEach { ownerType ->
                RecipeOwnerCategory(
                    groupName = ownerType.groupName,
                    title = ownerType.title,
                    subTitle = ownerType.subTitle,
                    borderColor = ownerType.borderColor,
                    backgroundColor = ownerType.backgroundColor,
                    textColor = ownerType.textColor,
                    drawable = ownerType.drawable,
                    onClick = onClick
                )
            }
        }
    }
}