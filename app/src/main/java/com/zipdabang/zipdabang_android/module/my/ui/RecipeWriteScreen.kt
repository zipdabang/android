package com.zipdabang.zipdabang_android.module.my.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.AppBarSignUp
import com.zipdabang.zipdabang_android.ui.component.ImageWithIconAndText
import com.zipdabang.zipdabang_android.ui.component.PrimaryButton
import com.zipdabang.zipdabang_android.ui.component.PrimaryButtonWithStatus
import com.zipdabang.zipdabang_android.ui.component.TextFieldForContent
import com.zipdabang.zipdabang_android.ui.component.TextFieldForRecipeWrite
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RecipeWriteScreen(
    onClickBack : ()->Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            AppBarSignUp(
                navigationIcon = R.drawable.ic_topbar_backbtn,
                onClickNavIcon = { onClickBack() },
                centerText = stringResource(id = R.string.my_recipewrite)
            )
        },
        containerColor = Color.White,
        contentColor = Color.White,
    ){
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color.White)
        ) {
            //썸네일
            Box(
                modifier = Modifier
                    .height(360.dp)
                    .fillMaxWidth()
            ){
                ImageWithIconAndText(
                    addImageClick = {

                    },
                    deleteImageClick = {

                    },
                    imageUrl = "",
                    iconImageVector = R.drawable.ic_my_recipewrtie_camera,
                    iconTint = ZipdabangandroidTheme.Colors.Typo.copy(0.5f),
                    iconModifier = Modifier.size(27.dp, 24.dp),
                    text = stringResource(id = R.string.my_recipewrite_thumbnail_upload),
                    textStyle = ZipdabangandroidTheme.Typography.sixteen_700,
                    textColor = ZipdabangandroidTheme.Colors.Typo.copy(0.5f)
                )
            }

            //레시피 제목
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 20.dp, 16.dp, 10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ){
                Text(
                    text = stringResource(id = R.string.my_recipewrite_title),
                    style = ZipdabangandroidTheme.Typography.sixteen_700,
                    color = ZipdabangandroidTheme.Colors.Choco
                )
                TextFieldForRecipeWrite(
                    value = "",
                    onValueChanged = { newText, maxLength ->
                        if(newText.length <= maxLength){
                            //textState = newTextfr
                        }
                    },
                    singleLine = true,
                    maxLines = 1,
                    placeholderValue = stringResource(id = R.string.my_recipewrite_title_hint),
                    imeAction = ImeAction.Next,
                    maxLength = 1,
                )
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = "0/20",
                    style = ZipdabangandroidTheme.Typography.fourteen_300,
                    color = ZipdabangandroidTheme.Colors.Typo
                )
            }



            // 하단 버튼
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 12.dp, 16.dp, 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Bottom
            ){
                Box(
                    modifier = Modifier.weight(1f)
                ){
                    PrimaryButton(
                        backgroundColor = ZipdabangandroidTheme.Colors.MainBackground,
                        text= stringResource(id = R.string.my_recipewrite_save),
                        onClick={},
                    )
                }
                Box(
                    modifier = Modifier.weight(1f)
                ){
                    PrimaryButtonWithStatus(
                        isFormFilled = false,
                        text= stringResource(id = R.string.my_recipewrite_writedone),
                        onClick={},
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewRecipeWriteScreen() {
    RecipeWriteScreen(
        onClickBack ={}
    )
}