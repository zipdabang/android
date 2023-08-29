package com.zipdabang.zipdabang_android.module.item.goods.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.ui.component.RectangleImage
import com.zipdabang.zipdabang_android.ui.component.RectangleWithRadiusImage
import com.zipdabang.zipdabang_android.ui.theme.NavBlack
import com.zipdabang.zipdabang_android.ui.theme.RankPink

@Composable
fun HotItem(
    rank : String,
    imageUrl : String,
    marketName : String,
    productName : String,
    price : String
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom= 10.dp)
    ){
        Box(modifier= Modifier.weight(1f)){
            Text(
                text= rank,
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                fontWeight = FontWeight(700),
                color = NavBlack
                )
        }
        Box(
            Modifier
                .weight(1f)
                .size(52.dp)){
            RectangleWithRadiusImage(imageUrl = imageUrl, contentDescription = "hot item image")
        }
        Column(
            Modifier.weight(4f)
                .padding(start = 8.dp)
            ,horizontalAlignment = Alignment.Start,
            ){
            Text(marketName,
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                fontWeight = FontWeight(300),
                color = NavBlack
            )
            Text(productName,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                fontWeight = FontWeight(500),
                color = NavBlack
            )
            Text(price,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                fontWeight = FontWeight(300),
                color = NavBlack
                )

        }
        Box(modifier= Modifier.weight(0.7f)){
            Icon(painter = painterResource(id = R.drawable.ic_nav_inactive_basket),tint = RankPink, contentDescription = "rank")
        }
    }
}

@Preview
@Composable
fun hotItemPreview(){
    HotItem(rank = "1", imageUrl = "https://menu.moneys.co.kr/moneyweek/thumb/2019/07/04/06/2019070409188047775_1.jpg/dims/thumbnail/620/optimize/", marketName = "집다방", productName = "레모네이드", price ="3000원" )
}