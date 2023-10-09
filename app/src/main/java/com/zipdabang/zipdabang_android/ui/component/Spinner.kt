package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Spinner(
    optionList: List<String>,
    onItemChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (optionList.isNotEmpty()) {
            var expanded by remember {
                mutableStateOf(false)
            }
            var selectedOption by remember {
                mutableStateOf(optionList[0])
            }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedOption,
                    onValueChange = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .background(
                            color = if (expanded) Color(0x33262D31) else ZipdabangandroidTheme.Colors.SubBackground,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors =
                    if (expanded)
                        ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0x33262D31),
                            unfocusedBorderColor = ZipdabangandroidTheme.Colors.SubBackground,
                            focusedLabelColor = ZipdabangandroidTheme.Colors.Typo,
                            unfocusedLabelColor = ZipdabangandroidTheme.Colors.Typo
                        )
                    else
                        ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                            focusedBorderColor = ZipdabangandroidTheme.Colors.SubBackground,
                            unfocusedBorderColor = ZipdabangandroidTheme.Colors.SubBackground,
                            focusedLabelColor = ZipdabangandroidTheme.Colors.Typo,
                            unfocusedLabelColor = ZipdabangandroidTheme.Colors.Typo
                        ),
                    shape = ZipdabangandroidTheme.Shapes.small
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .background(ZipdabangandroidTheme.Colors.SubBackground)
                ) {
                    optionList.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = option) },
                            onClick = {
                                selectedOption = option
                                expanded = false
                                // item change에 대한 처리는 textfield onValueChange에서
                                onItemChange(selectedOption)
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            modifier = Modifier
                                .background(ZipdabangandroidTheme.Colors.SubBackground)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortSpinner(
    modifier: Modifier = Modifier,
    optionList: List<String>,
    onItemChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        if (optionList.isNotEmpty()) {
            var expanded by remember {
                mutableStateOf(false)
            }
            var selectedOption by remember {
                mutableStateOf(optionList[0])
            }

            val padding = 2.dp
            val density = LocalDensity.current

            ExposedDropdownMenuBox(
                modifier = Modifier,
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedOption,
                    textStyle = ZipdabangandroidTheme.Typography.fourteen_500,
                    maxLines = 1,
                    onValueChange = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawWithContent {
                            val paddingPx = with(density) { padding.toPx() }
                            clipRect(
                                left = -paddingPx,
                                top = 0f,
                                right = size.width + paddingPx,
                                bottom = size.height + paddingPx
                            ) {
                                this@drawWithContent.drawContent()
                            }
                        }
                        .shadow(
                            elevation = 2.dp
                        )
                        .menuAnchor()
                        .background(
                            color = Color.White
                        ),
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors =
                    if (expanded)
                        ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedLabelColor = ZipdabangandroidTheme.Colors.Typo,
                            unfocusedLabelColor = ZipdabangandroidTheme.Colors.Typo
                        )
                    else
                        ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedLabelColor = ZipdabangandroidTheme.Colors.Typo,
                            unfocusedLabelColor = ZipdabangandroidTheme.Colors.Typo
                        ),
                    shape = ZipdabangandroidTheme.Shapes.small
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .background(Color.White)
                        .padding(bottom = 2.dp)
                        .clip(RectangleShape)
                ) {
                    optionList.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(
                                text = option,
                                style = ZipdabangandroidTheme.Typography.fourteen_500
                            ) },
                            onClick = {
                                selectedOption = option
                                expanded = false
                                // item change에 대한 처리는 textfield onValueChange에서
                                onItemChange(selectedOption)
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                            modifier = Modifier
                                .background(Color.White)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SpinnerPreview() {

    val names = listOf<String>("김기문", "강하현", "김예은")

    var state by remember {
        mutableStateOf(names[0])
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .width(120.dp)
        ) {
            Spinner(optionList = names, onItemChange = { selectedOption -> state = selectedOption })
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "selected : $state", color = Color.White)
    }
}
