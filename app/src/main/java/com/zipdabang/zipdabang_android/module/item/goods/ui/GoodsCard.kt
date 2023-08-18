package com.zipdabang.zipdabang_android.module.item.goods.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.CardTitleReversed
import com.zipdabang.zipdabang_android.ui.component.RectangleImage
import com.zipdabang.zipdabang_android.ui.theme.CardBorder
import com.zipdabang.zipdabang_android.ui.theme.StarYellow
import com.zipdabang.zipdabang_android.ui.theme.ZipdabangandroidTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun GoodsCard(
    image : Any,
    isBasket : Boolean,
    isFavorite : Boolean,
    title : String,
    price : String,
    star : String,
    star_users : String
){

    Box(modifier = Modifier.fillMaxSize()){
      Card(
          onClick = { /*TODO*/ },
          modifier = Modifier.fillMaxSize(),
          shape = ZipdabangandroidTheme.Shapes.small,
          backgroundColor = Color.White,
          border = BorderStroke(1.dp, CardBorder)
          ) {
          Column {
              Box(modifier = Modifier
                  .weight(3.8f)
                  ) {
                  RectangleImage(imageUrl = image, contentDescription = "Goods_Image")
              }
              Column(modifier = Modifier
                                 .weight(1f)
                                 .padding(start= 4.dp,top = 4.dp)
              ) {
                  CardTitleReversed(title = price, subTitle = title)
                  Row(verticalAlignment = Alignment.Top,
                      modifier = Modifier.padding(bottom=2.dp).offset(y = (-2).dp)) {
                      Icon(
                          painter = painterResource(R.drawable.market_goods_star),
                          contentDescription = null,
                          modifier = Modifier.align(Alignment.CenterVertically),
                          tint = StarYellow
                      )
                      Text(text = star, fontWeight = FontWeight(300) , fontSize = 8.sp)
                      Text("(" + star_users + ")", fontWeight = FontWeight(300) , fontSize = 8.sp)
                  }
              }
          }
       }
    }
}

@Preview
@Composable
fun cardPreview(){

    Box(modifier = Modifier.size(width = 160.dp,height= 224.dp )){
        GoodsCard(
            image = "https://www.jungle.co.kr/image/9795b5664b049958cef6619c",
            isBasket = false,
            isFavorite =false ,
            title = "맛있는 우유" ,
            price = "20000원",
            star = "4.2",
            star_users ="20"
        )
    }
}