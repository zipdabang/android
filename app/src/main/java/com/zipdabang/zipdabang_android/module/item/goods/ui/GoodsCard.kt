package com.zipdabang.zipdabang_android.module.item.goods.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
    image: Any,
    isBasket: Boolean,
    isFavorite: Boolean,
    title: String,
    price: String,
    star: Int,
    star_users: String,
    onBasketClick: (Boolean) -> Unit,
    onLikeClick: (Boolean) -> Unit,
){


      Card(
          modifier = Modifier
              .size(height = 224.dp, width = 160.dp)
              .padding(horizontal = 4.dp),
          shape = ZipdabangandroidTheme.Shapes.small,
          backgroundColor = Color.White,
          border = BorderStroke(1.dp, CardBorder)
          ) {
          Column(
              verticalArrangement = Arrangement.Center
          ) {
              Box(modifier = Modifier
                  .weight(3.4f)
                  ) {
                  RectangleImage(imageUrl = image, contentDescription = "Goods_Image")
              }
              Row(modifier = Modifier
                  .weight(1f)
                  .padding(start = 4.dp, top = 4.dp)
              ) {
                      Column(
                          modifier = Modifier
                              .weight(2f)
                      ) {
                          CardTitleReversed(title = price, subTitle = title)
                          Row(
                              verticalAlignment = Alignment.CenterVertically,
                              modifier = Modifier
                                  .padding(bottom = 1.dp)
                                  .offset(y = (-4).dp)
                                  .height(20.dp)
                          ) {
                              Icon(
                                  painter = painterResource(R.drawable.market_goods_star),
                                  contentDescription = null,
                                  modifier = Modifier.align(Alignment.CenterVertically),
                                  tint = StarYellow
                              )
                              Text(
                                  text = star.toString(),
                                  fontWeight = FontWeight(300),
                                  fontSize = 8.sp,
                                  lineHeight = 20.sp
                              )
                              Text(
                                  "(" + star_users + ")",
                                  fontWeight = FontWeight(300),
                                  fontSize = 8.sp
                              )
                          }
                  }
                  Column(Modifier.weight(1f)
                      .fillMaxSize()
                  ) {
                     Row(Modifier.weight(1f)){}
                      Row(
                          modifier = Modifier
                              .weight(1f)
                              .fillMaxWidth()
                              .padding(bottom= 4.dp,end= 3.dp),
                          verticalAlignment = Alignment.CenterVertically
                      ) {
                          GoodsToggle(
                              iconChecked = R.drawable.market_itembasket_active_small,
                              iconNotChecked = R.drawable.market_itembasket_inactive_small,
                              checked = isBasket,
                              onClick =  onBasketClick ,
                              checkedColor = ZipdabangandroidTheme.Colors.Cream
                          )
                          Spacer(Modifier.width(1.dp))

                          GoodsToggle(
                              iconChecked = R.drawable.market_itemliked_active_small,
                              iconNotChecked = R.drawable.market_itemliked_inactive_small,
                              checked = isFavorite,
                              onClick =  onLikeClick ,
                              checkedColor = ZipdabangandroidTheme.Colors.Cream
                          )

                      }
                  }
              }
          }
       }
    }


@Preview
@Composable
fun cardPreview(){

        GoodsCard(
            image = "https://www.jungle.co.kr/image/9795b5664b049958cef6619c",
            isBasket = false,
            isFavorite = true,
            title = "맛있는 우유" ,
            price = "20000원",
            star = 4,
            star_users ="20",
            onBasketClick = {},
            onLikeClick = {}
        )

}