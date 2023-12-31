package com.zipdabang.zipdabang_android.module.guide

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import com.zipdabang.zipdabang_android.R

enum class HomeCafeTools(val title :String, val description : String,val image : Int) {
    Cup("컵과 잔","홈카페의 꽃은 분위기예요. 내 마음에 쏙 드는 컵을 사두면, 레시피를 완성했을 때 기분이 더좋겠죠?",
       R.drawable.cup),
    MeasuringCup("계량컵","눈대중은 저에게 어려워요! 하는 분들에게 필수템이랍니다. 종이컵 계량도 좋지만, 눈금이 있는 만큼 양을 조절하고 가늠하기 좋아요.",  R.drawable.measuringcup),
    IceTray("얼음 틀","얼음은 다다익선이예요! 따뜻한 음료 외의 대부분 레시피에는 얼음이 들어간답니다.",  R.drawable.icetray),
    TeaSpoon("티스푼","없다면 일반 수저를 활용해도 되지만.. 티스푼이 음료를 젓기 훨씬 편하고 감성 한스푼 추가하기 좋아요!",  R.drawable.teaspoon),
    ElectricWhipper("전동 휘핑기","손으로 1000번은 저어야 만들 수 있는 크림을 만들거나 거품을 낼 때 있으면 아주 편리하게 쓰여요. 크기도 그리 크지 않고, 세척도 쉬워서 있으면 여러 모로 편하답니다.",
        R.drawable.electricwhipping),
    Mixer("믹서기(블랜더)","스무디, 주스, 쉐이크 등을 만들 때 필수에요.", R.drawable.mixer),
    CoffeeGrinder("커피 그라인더","그라인더는 로스팅된(볶은) 원두를 분쇄하는 도구에요. 자동 그라인더는 알아서 추출되어 나와 편리해요. 수동 그라인더는 가격이 더 싸고, 커피 향을 즐길 수 있어요.", R.drawable.coffeegrinder),
    Fruits("건조과일과 허브","음료를 만들고 한두개만 올려도 분위기가 확 달라진답니다! 각종 음료, 베이킹 후에 장식하기 좋아요.", R.drawable.teaspoon),
    ElectronicScale("전자 저울","레시피를 처음 제작 때는 계량을 맞추기가 쉽지 않아요. 이럴 때 전자 저울이 있다면 아주 편리해진답니다! 믹서기(블랜더)가 필요한 음료가 아니면 1kg를 사도 괜찮아요.", R.drawable.electricscale)
}