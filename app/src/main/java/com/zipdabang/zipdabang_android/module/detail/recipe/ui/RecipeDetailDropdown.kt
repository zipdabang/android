package com.zipdabang.zipdabang_android.module.detail.recipe.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RecipeDetailDropdown(
    isDropdownExpandedForOwner: Boolean,
    onDismissDropdown: () -> Unit,
    onClickRecipeEdit: () -> Unit,
    onClickRecipeDelete: () -> Unit,
    isDropdownExpandedForNotOwner: Boolean,
    showRecipeReport: (Boolean) -> Unit,
    showRecipeBlock: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(vertical = 20.dp, horizontal = 12.dp)
        ) {
            DropdownMenu(
                modifier = Modifier.background(Color.White),
                expanded = isDropdownExpandedForOwner,
                onDismissRequest = {
                    onDismissDropdown()
                },
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(id = R.string.dropdown_edit_recipe),
                            style = ZipdabangandroidTheme.Typography.fourteen_500
                        )
                    },
                    onClick = {
                        onClickRecipeEdit()
                        onDismissDropdown()
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(id = R.string.dropdown_delete_recipe),
                            style = ZipdabangandroidTheme.Typography.fourteen_500
                        )
                    },
                    onClick = {
                        onClickRecipeDelete()
                        onDismissDropdown()
                    }
                )
            }

            DropdownMenu(
                modifier = Modifier.background(Color.White),
                expanded = isDropdownExpandedForNotOwner,
                onDismissRequest = {
                    onDismissDropdown()
                },
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(id = R.string.dropdown_report_recipe),
                            style = ZipdabangandroidTheme.Typography.fourteen_500
                        )
                    },
                    onClick = {
                        // showCommentReport(recipeId, commentItem.commentId, 1)
                        showRecipeReport(true)
                        onDismissDropdown()
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(id = R.string.dropdown_block_user),
                            style = ZipdabangandroidTheme.Typography.fourteen_500
                        )
                    },
                    onClick = {
                        showRecipeBlock(true)
                        onDismissDropdown()
                    }
                )
            }
        }
    }
}
