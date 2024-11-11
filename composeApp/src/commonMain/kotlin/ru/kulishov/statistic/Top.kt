package ru.kulishov.statistic

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import stata.composeapp.generated.resources.Res
import stata.composeapp.generated.resources.first
import stata.composeapp.generated.resources.man
import stata.composeapp.generated.resources.redact
import stata.composeapp.generated.resources.second
import stata.composeapp.generated.resources.swap
import stata.composeapp.generated.resources.third
import stata.composeapp.generated.resources.woman

//=====================================================================================
//topScreenPhone
//=====================================================================================
@Composable
fun topScreenPhone(){

}

//=====================================================================================
//topScreenDesktop
//=====================================================================================
@Composable
fun topScreenDesktop(){

}

//=====================================================================================
//topElement
//Input values:
//              userSex:Int - user sex
//              userPosition:Int - position user in top
//              userBalls:Int - user balls
//              maxBall:Int - best result
//              theme:MaterialTheme - top theme
//=====================================================================================
@Composable
fun topElement(userSex:Int, userPosition:Int, userBalls:Int, maxBall:Int, theme:Color){
    val brush = Brush.horizontalGradient(
        if(userPosition==1){
            listOf(Color(255,174,0), Color(232,51,31))
        }else if(userPosition==2) {
            listOf(Color(109,150,255), Color(255,255,255))
        }else if(userPosition==3){
            listOf(Color(187,105,86), Color(223,73,73))
        }else{
            listOf(Color(169,11,238), Color(246,0,123))
        }
    )
    var animatedWidth by remember { mutableStateOf(0.dp) }
    var animatedPosition by remember { mutableStateOf(0.dp) }
    var boxWidth by remember { mutableStateOf(0.dp) }
    var animatedAlphaIcon by remember { mutableStateOf(0f) }

    LaunchedEffect(userPosition) {
        delay(1000)
        animatedWidth = boxWidth*userBalls/maxBall
        animatedPosition= (boxWidth*userBalls/maxBall)+10.dp
        animatedAlphaIcon=1f
        //println("$userPosition $boxWidth $userPosition $animatedWidth")
    }

    val animatedWidthDp by animateDpAsState(
        targetValue = animatedWidth,
        animationSpec = tween(durationMillis = 1000, delayMillis = when (userPosition) {
            1 -> 0
            2 -> 200
            3 -> 400
            else -> 600
        })
    )
    val animatedPositionDp by animateDpAsState(
        targetValue = animatedPosition,
        animationSpec = tween(durationMillis = if(userPosition!=1) 1000 else 500, delayMillis = when (userPosition) {
            1 -> 0
            2 -> 200
            3 -> 400
            else -> 600
        })
    )
    val animatedAlphaIconS by animateFloatAsState(
        targetValue = animatedAlphaIcon,
        animationSpec = tween(durationMillis = 500, delayMillis = when (userPosition) {
            1 -> 200
            2 -> 400
            3 -> 600
            else -> 800
        })
    )

    Box(Modifier
        .fillMaxWidth()
        .height(85.dp)
        ,contentAlignment = Alignment.BottomStart){
        Row(verticalAlignment = Alignment.CenterVertically){
            Box(modifier = Modifier
                .padding(top = 10.dp)
                .height(50.dp)
                .width(50.dp)
                .border(3.dp, color =theme, shape = RoundedCornerShape(15))
                , contentAlignment = Alignment.Center
            ){
                Image(painterResource(if(userSex==1)Res.drawable.man else Res.drawable.woman), contentDescription = null)
            }
            Box(Modifier.padding(start = 15.dp, end = 140.dp)
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    boxWidth=coordinates.size.width.dp
                    boxWidth-=200.dp

                }
            ){
                    Box(Modifier
                        .fillMaxHeight()
                        .width(animatedWidthDp)
                        , contentAlignment = Alignment.BottomCenter
                    ){
                        Box(Modifier
                            .padding(end=45.dp, bottom = 10.dp)
                            .height(50.dp)
                            .fillMaxWidth()
                            .background(brush, shape = RoundedCornerShape(15))
                        )

                        Box(Modifier.fillMaxSize()
                        , contentAlignment = Alignment.BottomEnd){
                            if (userPosition < 4) {
                                Image(
                                    painterResource(
                                        when (userPosition) {
                                            1 -> Res.drawable.first
                                            2 -> Res.drawable.second
                                            else -> Res.drawable.third
                                        }
                                    ), contentDescription = null, modifier = Modifier
                                        /*.offset {
                                            if(userPosition==1) IntOffset((animatedPositionDp.roundToPx()-500) /325*boxWidth.roundToPx(),30)
                                            else IntOffset(animatedPositionDp.roundToPx()-140,40)
                                        }*/
                                        .alpha(animatedAlphaIconS)
                                        .width(85.dp)
                                        .height(85.dp)

                                )

                            }
                        }
                    }


            }
        }

        Box(Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            , contentAlignment = Alignment.CenterEnd){
            Text(userBalls.toString(), style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 31.sp,
                color = theme
            ))
        }
    }/*
    if (userPosition < 4) {
        Image(
            painterResource(
                when (userPosition) {
                    1 -> Res.drawable.first
                    2 -> Res.drawable.second
                    else -> Res.drawable.third
                }
            ), contentDescription = null, modifier = Modifier
                .padding(start = animatedPositionDp)
                .width(85.dp)
                .height(85.dp)

        )

    }*/
}

//=====================================================================================
//peopleDrawer
//Input values:
//              invent:()->Unit - invent people action
//              updatePeople:()->Unit - update people action
//              userAdmin:Int - admin
//              peopleList:List<User> - users in list
//              backgroundColor:Color - background color
//              primaryColor:Color - primary color
//              themeColor:Color - theme color
//              defaultColor:Color - default color
//              secondColor:Color - second color
//=====================================================================================
data class UserApp(val userId:Int, val sex:Int,val username:String)
@Composable
fun peopleDrawer(invent:()->Unit, updatePeople:()->Unit,userAdmin:Int, peopleList:List<UserApp>, backgroundColor:Color, primaryColor:Color,themeColor:Color, defaultColor:Color, secondColor:Color){
    var drawerState by remember { mutableStateOf(true) }
    val animationDrawer by animateDpAsState(
        targetValue = if(drawerState) 325.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 500,
        )
    )
    val animationDrawerTap by animateFloatAsState(
        targetValue = if(drawerState) 0f else 180f,
        animationSpec = tween(durationMillis = 500
        , delayMillis = 500)
    )
    Row(Modifier.padding(top = 40.dp)) {
        Box(
            Modifier.fillMaxHeight()
                .width(animationDrawer)
                .background(backgroundColor),
            contentAlignment = Alignment.TopCenter
        ){
            Column {
                Box(Modifier.fillMaxWidth()
                    .height(80.dp),
                    contentAlignment = Alignment.Center){
                    Text("Участники", style = TextStyle(
                        fontSize = 36.sp,
                        color = primaryColor
                    ))
                }
                LazyColumn {
                    items(peopleList){
                        pep->
                        Box(Modifier.padding(top=25.dp)) {
                            peopleCard(
                                pep, if (pep.sex == 1) Res.drawable.man else Res.drawable.woman,
                                if (pep.userId == userAdmin) themeColor else defaultColor,
                                primaryColor, ""
                            )
                        }
                    }
                }
            }
            Box(Modifier.width(35.dp)
                .height(80.dp)
                .background(backgroundColor, shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp)),
                contentAlignment = Alignment.CenterStart){
                Icon(painterResource(Res.drawable.swap), contentDescription = null, modifier =
                Modifier.rotate(animationDrawerTap))
            }
        }
    }
}

//=====================================================================================
//peopleCard
//Input values:
//              user:UserApp - user
//              image:DrawableResource  - userImage
//              color:Color - card color
//              primaryColor:Color - primary color
//              textEnd:String - text in end
//=====================================================================================
@Composable
fun peopleCard( user:UserApp, image:DrawableResource, color:Color, primaryColor:Color, textEnd:String){
    Box(Modifier.width(275.dp)
        .height(100.dp)
        .background(color, shape = RoundedCornerShape(15)),
        contentAlignment = Alignment.Center){
        Row(Modifier.padding(start=15.dp,end=15.dp),
            verticalAlignment = Alignment.CenterVertically){
            Box(Modifier.width(50.dp)
                .height(50.dp)
                .border(3.dp,primaryColor, RoundedCornerShape(15)),
                contentAlignment = Alignment.Center){
                Image(painterResource(image), contentDescription = null)
            }
            Text(user.username, style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            ))
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
                Row {
                    Text(textEnd, style = TextStyle(
                        fontSize = 24.sp,
                        color = primaryColor
                    )
                    )
                    Icon(painterResource(Res.drawable.redact), contentDescription = null
                    , tint = primaryColor)
                }
            }
        }
    }
}

//=====================================================================================
//inventCard
//Input values:
//              backgroundColor:Color - background color
//              primaryColor:Color - primary color
//              onClick:()->Unit - click action
//=====================================================================================
@Composable
fun inventCard( backgroundColor:Color, primaryColor:Color, onClick:()->Unit){
    var text by remember { mutableStateOf("Пригласить") }
    var redact by remember { mutableStateOf(false) }
    LaunchedEffect(redact){
        text="Скопировано"
        delay(5000)
        text="Пригласить"
        redact=false
    }
    Box(Modifier.width(275.dp)
        .height(100.dp)
        .background(backgroundColor, shape = RoundedCornerShape(15))
        .clickable {
            redact=true
            onClick()
        },
        contentAlignment = Alignment.Center){
        Text(text, style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor
        ))
    }
}
