package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.recipes.common.ReportContent
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RadioGroupHorizontal(
    selectedIndex : Int,
    optionList: List<String>,
    onOptionChange: (String) -> Unit
) {
    var selectedOption by remember {
        mutableStateOf(optionList[selectedIndex])
    }

    Row(
        modifier = Modifier
            .selectableGroup()
            .fillMaxWidth()
    ) {
        optionList.forEach { option ->
            Row(
                modifier = Modifier
                    .selectable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null,
                        selected = (selectedOption == option),
                        onClick = {
                            selectedOption = option
                            onOptionChange(selectedOption)
                        },
                        role = Role.RadioButton
                    )
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(
                        id = if (selectedOption == option) R.drawable.ic_radio_checked else R.drawable.ic_radio_unchecked
                    ),
                    contentDescription = "radio",
                    tint = ZipdabangandroidTheme.Colors.Strawberry,
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(text = option, textAlign = TextAlign.Center, style=ZipdabangandroidTheme.Typography.fourteen_300)
                
            }
        }
    }
}

@Composable
fun RadioGroupVertical(
    optionList: List<String>,
    onOptionChange: (String) -> Unit
) {
    var selectedOption by remember {
        mutableStateOf(optionList[0])
    }

    Column(
        modifier = Modifier
            .selectableGroup()
            .fillMaxWidth()
    ) {
        optionList.forEach { option ->
            Row(
                modifier = Modifier
                    .selectable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null,
                        selected = (selectedOption == option),
                        onClick = {
                            selectedOption = option
                            onOptionChange(selectedOption)
                        },
                        role = Role.RadioButton
                    )
                    .padding(20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(
                        id = if (selectedOption == option) R.drawable.ic_radio_checked else R.drawable.ic_radio_unchecked
                    ),
                    contentDescription = "radio",
                    tint = ZipdabangandroidTheme.Colors.Strawberry
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(text = option, textAlign = TextAlign.Center ,style=ZipdabangandroidTheme.Typography.fourteen_300)

            }

            Spacer(modifier = Modifier.width(12.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally),
            )
        }

    }
}

@Composable
fun RadioGroupReportVertical(
    optionList: List<ReportContent>,
    onOptionChange: (Int) -> Unit
) {
    var selectedOption by remember {
        mutableStateOf(optionList[0].id)
    }

    Column(
        modifier = Modifier
            .selectableGroup()
            .fillMaxWidth()
    ) {
        optionList.forEach { option ->
            Row(
                modifier = Modifier
                    .selectable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null,
                        selected = (selectedOption == option.id),
                        onClick = {
                            selectedOption = option.id
                            onOptionChange(selectedOption)
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 24.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(
                        id = if (selectedOption == option.id) R.drawable.ic_radio_checked else R.drawable.ic_radio_unchecked
                    ),
                    contentDescription = "radio",
                    tint = ZipdabangandroidTheme.Colors.Strawberry
                )

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = option.content,
                    textAlign = TextAlign.Center ,
                    style=ZipdabangandroidTheme.Typography.fourteen_300)

            }

            Spacer(modifier = Modifier.width(12.dp))

        }

    }
}


@Preview(showBackground = true)
@Composable
fun RadioGroupHorizontalPreview() {
    RadioGroupHorizontal(selectedIndex = 0, optionList = listOf("카드", "현금", "수표"), onOptionChange = {})
}

@Preview(showBackground = true)
@Composable
fun RadioGroupVerticalPreview() {
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        val options = listOf<String>("카드", "현금", "수표")
        var state by remember {
            mutableStateOf(options[0])
        }

        RadioGroupVertical(optionList = options, onOptionChange = { selectedValue -> state = selectedValue })
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = state)
    }

}

@Preview(showBackground = true)
@Composable
fun RadioGroupReportVerticalPreview() {
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        val options = ReportContent.contents
        var state by remember {
            mutableStateOf(options[0].id)
        }

        RadioGroupReportVertical(optionList = options, onOptionChange = { selectedValue -> state = selectedValue })
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = state.toString())
    }

}


