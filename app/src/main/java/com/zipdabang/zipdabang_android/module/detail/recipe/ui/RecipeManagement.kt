package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RecipeManagement(
    recipeId: Int,
    deleteIcon: Int,
    editIcon: Int,
    onClickDelete: (Int) -> Unit,
    onClickEdit: (Int) -> Unit
) {
    Row() {
        IconButton(onClick = {
            onClickDelete(recipeId)
        }) {
            Icon(
                painter = painterResource(id = deleteIcon),
                contentDescription = "delete icon",
                modifier = Modifier
                    .padding(4.dp),
                tint = ZipdabangandroidTheme.Colors.Typo
            )
        }

        IconButton(onClick = {
            onClickEdit(recipeId)
        }) {
            Icon(
                painter = painterResource(id = editIcon),
                contentDescription = "edit icon",
                modifier = Modifier
                    .padding(4.dp),
                tint = ZipdabangandroidTheme.Colors.Typo
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeManagementPreview() {
    RecipeManagement(
        recipeId = 1,
        deleteIcon = R.drawable.all_delete_black,
        editIcon = R.drawable.all_edit_black,
        onClickDelete = { int -> },
        onClickEdit = { int -> }
    )
}