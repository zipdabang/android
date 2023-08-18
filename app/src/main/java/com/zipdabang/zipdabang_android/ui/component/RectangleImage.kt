package com.zipdabang.zipdabang_android.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@Composable
fun RectangleImage(
    imageUrl: Any,
    contentDescription : String?
) {

    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = Modifier
                  .clip(RectangleShape)
                  .fillMaxSize()
                ,
        contentScale = ContentScale.FillBounds
    )

}

@Composable
fun RectangleWithRadiusImage(
    imageUrl: Any,
    contentDescription : String?
) {

    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = Modifier
            .clip(ZipdabangandroidTheme.Shapes.small)
            .fillMaxSize(),
        contentScale = ContentScale.FillBounds
    )

}

@Preview
@Composable
fun RectangleImagePreview(){
    RectangleImage(
        imageUrl ="https://pds.joongang.co.kr/svcimg/newsletter/content/202203/14/3450436a-5c23-4cc9-b8a4-2ab7c2ca8b76.jpg",
        contentDescription = "레시피 이미지 사진"
    )
}