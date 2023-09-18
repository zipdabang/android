package com.zipdabang.zipdabang_android.module.guide.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zipdabang.zipdabang_android.R
import com.zipdabang.zipdabang_android.module.guide.CardFace
import com.zipdabang.zipdabang_android.module.guide.GuideConstants
import com.zipdabang.zipdabang_android.module.guide.HomeCafeTools
import com.zipdabang.zipdabang_android.ui.component.RectangleWithRadiusText
import com.zipdabang.zipdabang_android.ui.theme.GuideBrown
import com.zipdabang.zipdabang_android.ui.theme.NavBlack


@Composable
fun GuideScreen1(){

   val scrollState = rememberScrollState()

    Column(
        Modifier.verticalScroll(scrollState)
    ){

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(488.dp)
                .background(color = Color(0xFFEDE9E3)),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(GuideConstants.Guide1_Section1_Title,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.scdream_bold)),
                fontWeight = FontWeight(700),
                color = GuideBrown,
                textAlign = TextAlign.Center,
                modifier =Modifier.padding(top = 60.dp,bottom=5.dp)
            )
            Text(GuideConstants.Guide1_Section1_SubTitle,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                fontWeight = FontWeight(500),
                color = Color(0xFF262D31),
                textAlign = TextAlign.Center,
                modifier= Modifier.padding(horizontal =36.dp)
            )
            Image(painterResource(id = R.drawable.guide1_table_normal),contentDescription = null,
                Modifier.padding(vertical = 30.dp, horizontal = 36.dp))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(606.dp)
                .background(color = Color(0xFFE7D7C9)),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(GuideConstants.Guide1_Section2_Title,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.scdream_bold)),
                fontWeight = FontWeight(700),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier =Modifier.padding(top = 60.dp,bottom=5.dp)
            )
            Text(GuideConstants.Guide1_Section2_Subtitle,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                fontWeight = FontWeight(500),
                color = Color(0xFF262D31),
                textAlign = TextAlign.Center,
                modifier= Modifier.padding(horizontal =30.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            val toolsArray = HomeCafeTools.values()
            Carousel(
                count = toolsArray.size,
                contentWidth = 216. dp,
                contentHeight = 280.dp,
                content = {
                        modifier, index ->
                    FlipCard(
                        item = toolsArray[index],
                       modifier = modifier)
                    }
            )

            Spacer(modifier = Modifier.height(30.dp))
            Text(GuideConstants.Guide1_Section2_Description,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                fontWeight = FontWeight(500),
                color = Color(0xFF262D31),
                textAlign = TextAlign.Center,
                modifier= Modifier.padding(horizontal =30.dp)
            )


        }
        Column(
            Modifier
                .background(Color.White)
                .fillMaxWidth()
                .height(700.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ){

            Text(GuideConstants.Guide1_Section3_Title,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                fontWeight = FontWeight(700),
                color = Color(0xFF262D31),
                textAlign = TextAlign.Center,
                modifier =Modifier.padding(top = 60.dp,bottom=5.dp,start= 40.dp,end=40.dp)
            )
            Text(GuideConstants.Guide1_Section3_Subtitle,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                fontWeight = FontWeight(500),
                color = Color(0xFF262D31),
                textAlign = TextAlign.Center,
                modifier =Modifier.padding(start= 40.dp,end=40.dp)

            )

            Image(painterResource(id = R.drawable.guide1_appdesign_normal),contentDescription = null,
                Modifier
                    .padding(vertical = 15.dp)
                    .size(300.dp))

            Box(Modifier.height(60.dp)
                .padding(horizontal = 30.dp)

                ) {
                RectangleWithRadiusText(
                    text = GuideConstants.Guide1_Section3_Description1,
                    backgroundcolor = Color(0xFFCDC6C3),
                    fontColor = Color(0xFF262D31),
                    fontSize = 12.sp
                )
            }
            Spacer(Modifier.height(10.dp))

            Box(Modifier.height(60.dp)
                .padding(horizontal = 30.dp)
            ) {
                RectangleWithRadiusText(
                    text = GuideConstants.Guide1_Section3_Description2,
                    backgroundcolor = Color(0xFFCDC6C3),
                    fontColor = Color(0xFF262D31),
                    fontSize = 12.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .background(color = Color(0xFFD4B2A7))
                .fillMaxWidth()
                .height(180.dp)
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(GuideConstants.Guide1_Section4_Title,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.kopubworlddotum_bold)),
                fontWeight = FontWeight(700),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(14.dp))
            
            Button(onClick = { /*TODO*/ },
                shape = RoundedCornerShape(size = 20.dp),
                border = BorderStroke(1.dp,Color.White),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD4B2A7)),
                elevation = ButtonDefaults.elevation(0.dp,0.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .height(39.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
            ){
                Box(  contentAlignment = Alignment.Center){
                Text(GuideConstants.GoToRecipe,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.kopubworlddotum_medium)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                    )
            }
                }
        }




    }




}

@Composable
@Preview
fun guidePreview(){
    GuideScreen1()
}