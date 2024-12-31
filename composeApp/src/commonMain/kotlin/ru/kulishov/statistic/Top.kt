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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope

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
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ru.kulishov.statistic.composeapp.generated.resources.Res
import ru.kulishov.statistic.composeapp.generated.resources.exit
import ru.kulishov.statistic.composeapp.generated.resources.first
import ru.kulishov.statistic.composeapp.generated.resources.man
import ru.kulishov.statistic.composeapp.generated.resources.redact
import ru.kulishov.statistic.composeapp.generated.resources.second
import ru.kulishov.statistic.composeapp.generated.resources.swap
import ru.kulishov.statistic.composeapp.generated.resources.third
import ru.kulishov.statistic.composeapp.generated.resources.topinvent
import ru.kulishov.statistic.composeapp.generated.resources.woman


data class toptrun(val userId:Int,val name:String, val userSex:Int, val userPosition:Int, val userBalls:Int)
//var updateUserList by mutableStateOf(true)
var  userCardView by mutableStateOf(false)
var  inventCardView by mutableStateOf(false)
var updateTop by mutableStateOf(1)
//обновляемый юзер
var updateUser by  mutableStateOf(-1)
//обновляемое значение
var updateResult by  mutableStateOf(0)
//=====================================================================================
//topScreenPhone
//=====================================================================================
@Composable
fun topScreenPhone(){

}

//=====================================================================================
//topScreenDesktop
//Input values:
//              key:String - user key
//              topId:Int - top id
//              backgroundColor:Color - background color
//              primaryColor:Color - primary color
//              themeColor:Color - theme color
//              defaultColor:Color - default color
//              secondColor:Color - second color
//=====================================================================================
@Composable
fun topScreenDesktop( topId:Int,backgroundColor: Color,primaryColor:Color,themeColor:Color, defaultColor:Color, secondColor:Color){
    var top by remember {  mutableStateOf( Top(-1,"","","", UserTop(-1,0), emptyList()))}
    var userTopList by remember {  mutableStateOf( emptyList<Int>())}
    var usernames by remember { mutableStateOf(emptyList<Username>()) }
    var toptrunList by remember { mutableStateOf(emptyList<toptrun>()) }
    var usersSort by remember { mutableStateOf(emptyList<UserTop>()) }
    var isAdmin by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    if(updateTop==1){
        var flagId=false
        scope.launch {
            usersSort= emptyList()
            userTopList= emptyList()
            toptrunList= emptyList()
            usernames= emptyList()
            val server = Reuests()
            server.getTop(topId,onSuccess = {
                    res->
                top=res
                if (top.id!=-1) flagId=true
            }, onFailure = {
                    res-> println(res)
            })
            println("top: $top")

            if(flagId){

                for(x in top.users){
                    if(userTopList.find { cr-> cr==x.user }==null){
                        userTopList+=x.user
                    }
                }
                server.getUsernameList(userTopList,
                    onFailure = {
                        res->println(res)
                    },
                    onSuccess = {
                        res-> usernames=res
                    })
                println(usernames)
            }
            usersSort=top.users
            usersSort=usersSort.sortedBy { cr -> (cr.value * -1) }
            var count=0
            for (x in usersSort) {
                count++
                val name = usernames.find { cr -> cr.userId == x.user }
                if(toptrunList.find { cr-> cr==toptrun(x.user, name!!.username, name!!.sex, count, x.value) }==null){
                    toptrunList += toptrun(x.user, name!!.username, name!!.sex, count, x.value)
                }

            }
            isAdmin=server.checkAdminTop(topId)
            println("admin: $isAdmin")
            updateTop=0
        }
    }
    if(updateTop==2){
        val scope = rememberCoroutineScope()
        scope.launch {
            val server = Reuests()
            server.kickUpUser(topId, updateUser)
            updateTop=1
        }
    }
    if(updateTop==3){
        val scope = rememberCoroutineScope()
        scope.launch {
            val server = Reuests()
            server.updateUserTopResult(topId, updateUser, updateResult)
            usersSort= emptyList()
            userTopList= emptyList()
            toptrunList= emptyList()
            usernames= emptyList()
            updateTop=1
        }
    }
    var userCardTrun by remember {  mutableStateOf( toptrun(-1,"",-1,-1,0))}
    if(0==0) {
        if (top.id != -2) {

            Column(
                Modifier.fillMaxSize()
                    .background(backgroundColor)
            ) {
                shapkaDesctop(darkTheme.background, darkTheme.primary, darkTheme.secondary)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isAdmin) {
                        peopleDrawer(
                            invent = {
                                inventCardView = true
                            },
                            updatePeople = { usr ->
                                val ball = toptrunList.find { cr -> cr.userId == usr.userId }!!.userBalls
                                userCardTrun =
                                    toptrun(usr.userId, usr.username, usr.sex, 0, ball)
                                userCardView = true
                            },
                            top.admin.user,
                            usernames,
                            Color(32, 32, 32),
                            primaryColor,
                            themeColor,
                            Color(122, 122, 122),
                            secondColor
                        )
                    }
                    Box(
                        Modifier.fillMaxWidth(0.8f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                top.name, style = TextStyle(
                                    fontSize = 40.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = primaryColor
                                ), modifier = Modifier.padding(top = 40.dp, bottom = 25.dp)
                            )
                            LazyColumn(
                                Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                contentPadding = PaddingValues(25.dp)
                            ) {

                                items(toptrunList) { line ->
                                    if(line.name== userTek.username){
                                        topElement(
                                            line.userSex,
                                            line.userPosition,
                                            line.userBalls,
                                            toptrunList[0].userBalls,
                                            themeColor
                                        )
                                    }else {
                                        topElement(
                                            line.userSex,
                                            line.userPosition,
                                            line.userBalls,
                                            toptrunList[0].userBalls,
                                            primaryColor
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }
            if (userCardView) {
                inventCardView = false
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    bigUserCardTop(userCardTrun, top.id, Color(32, 32, 32), primaryColor,
                        onSave = {

                        })
                }
            }
            if (inventCardView) {
                userCardView = false
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    bigUnventCardTop(key, top.id, Color(32, 32, 32), primaryColor)
                }
            }


        } else {

        }
    }
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
        if(maxBall==0){
            animatedWidth = boxWidth * userBalls / 1
            animatedPosition = (boxWidth * userBalls / 1) + 10.dp
        }else {
            animatedWidth = boxWidth * userBalls / maxBall
            animatedPosition = (boxWidth * userBalls / maxBall) + 10.dp
        }
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
                Image(painterResource(if(userSex==1) Res.drawable.man else Res.drawable.woman), contentDescription = null)
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
//              updatePeople:(Username)->Unit - update people action
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
fun peopleDrawer(invent:()->Unit, updatePeople:(Username)->Unit,userAdmin:Int, peopleList:List<Username>, backgroundColor:Color, primaryColor:Color,themeColor:Color, defaultColor:Color, secondColor:Color){
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
    Row(Modifier.padding(top = 40.dp)
        .width(animationDrawer+35.dp)) {
        Box(
            Modifier.fillMaxHeight()
                .width(animationDrawer)
                .background(backgroundColor),
            contentAlignment = Alignment.TopCenter
        ){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(Modifier.fillMaxWidth()
                    .height(80.dp),
                    contentAlignment = Alignment.Center){
                    Text("Участники", style = TextStyle(
                        fontSize = 36.sp,
                        color = primaryColor
                    ))
                }
                LazyColumn {
                    item{
                        inventCard(themeColor,primaryColor, onClick = {
                            invent()
                        })
                    }
                    items(peopleList){
                        pep->
                        Box(Modifier.padding(top=25.dp).clickable {
                            updatePeople(pep)
                        }) {
                            peopleCard(
                                pep, if (pep.sex == 1) Res.drawable.man else Res.drawable.woman,
                                if (pep.userId == userAdmin) themeColor else defaultColor,
                                primaryColor, ""
                            )
                        }
                    }
                }
            }

        }
        Box(Modifier.width(35.dp)
            .height(80.dp)
            .background(backgroundColor, shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp))
            .clickable {
                drawerState=!drawerState
            },
            contentAlignment = Alignment.CenterStart){
            Icon(painterResource(Res.drawable.swap), contentDescription = null, modifier =
            Modifier.rotate(animationDrawerTap), tint = primaryColor)
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
fun peopleCard(user:Username, image:DrawableResource, color:Color, primaryColor:Color, textEnd:String){
    Box(Modifier
        .width(275.dp)
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
            ), modifier = Modifier.width(150.dp))
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
        Text("Пригласить", style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor
        ))
    }
}

//=====================================================================================
//bigUserCardTop
//Input values:
//              user:toptrun - user
//              topId:Int - top id
//              backgroundColor:Color - background color
//              primaryColor:Color - primary color
//              onSave:()->Unit - action on save
//=====================================================================================
@Composable
fun bigUserCardTop(user:toptrun,topId:Int, backgroundColor:Color, primaryColor:Color, onSave:()->Unit){
    var result by remember { mutableStateOf(user.userBalls.toString()) }
    Box(Modifier.width(500.dp)
        .height(500.dp)
        .background(backgroundColor, RoundedCornerShape(10)),
        contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painterResource(if(user.userSex==1) Res.drawable.man else Res.drawable.woman), contentDescription = null,
                modifier = Modifier.height(180.dp))
            Text(user.name, style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
            )
            Column(Modifier.padding(top=25.dp)) {
                Text("Результат:", style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = primaryColor
                )
                )
                Box(Modifier.width(400.dp)
                    .height(100.dp)
                    .border( 3.dp,primaryColor, RoundedCornerShape(10))
                    .padding(top = 15.dp, bottom = 15.dp),
                    contentAlignment = Alignment.Center) {
                    TextField(
                        value = result,
                        onValueChange = { result = it },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryColor,
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = primaryColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            backgroundColor = Color.Transparent,
                            textColor = primaryColor,
                            disabledTextColor = primaryColor,
                            errorLeadingIconColor = Color(232, 51, 31)
                        ),
                        modifier = Modifier
                            .height(100.dp)
                    )
                }
            }
            Row(Modifier.padding(top=15.dp)) {
                Box(Modifier
                    .padding(end=15.dp)
                    .width(190.dp)
                    .height(70.dp)
                    .background(Color(232,51,31), RoundedCornerShape(10))
                    .clickable {
                        updateUser=user.userId
                        updateTop=2
                        userCardView=false
                    },
                    contentAlignment = Alignment.Center){
                    Text("Выгнать", style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color=primaryColor
                    )
                    )
                }
                Box(Modifier
                    .padding(start=15.dp)
                    .width(190.dp)
                    .height(70.dp)
                    .background(Color(109,150,255), RoundedCornerShape(10))
                    .clickable {
                        updateUser=user.userId
                        updateResult=result.toInt()
                        updateTop=3
                        userCardView=false
                        onSave()
                    },
                    contentAlignment = Alignment.Center){
                    Text("Сохранить", style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color=primaryColor
                    )
                    )
                }
            }
        }
        Box(Modifier
            .padding(end=25.dp,top=25.dp)
            .fillMaxSize()
            , contentAlignment = Alignment.TopEnd
        ){
            Icon(painterResource(Res.drawable.exit), contentDescription = null,
                tint = primaryColor, modifier = Modifier.clickable {
                    userCardView=false
                })
        }
    }
}

//=====================================================================================
//bigUnventCardTop
//Input values:
//              key:String - user key
//              topId:Int - top id
//              backgroundColor:Color - background color
//              primaryColor:Color - primary color
//=====================================================================================
@Composable
fun bigUnventCardTop(key:String,topId:Int, backgroundColor:Color, primaryColor:Color){
    var token by remember { mutableStateOf("") }
    var updatetoken by remember { mutableStateOf(true) }
    val server = Reuests()
    val scope = rememberCoroutineScope()
    if(updatetoken) {
        scope.launch {
            token=server.getTopToken(topId)
            updatetoken=false
        }
    }
    Box(Modifier.width(500.dp)
        .height(500.dp)
        .background(backgroundColor, RoundedCornerShape(10)),
        contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painterResource(Res.drawable.topinvent), contentDescription = null,
                modifier = Modifier.padding(bottom = 25.dp).height(180.dp))
                Text("Пригласить в топ", style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = primaryColor
                )
                )
                Box(Modifier
                    .padding(top = 25.dp)
                    .width(400.dp)
                    .height(100.dp)
                    .border( 3.dp,primaryColor, RoundedCornerShape(10))
                    ,contentAlignment = Alignment.Center) {
                    TextField(
                        value = token,
                        onValueChange = {  },
                        singleLine = true,
                        //enabled = false,
                        textStyle = TextStyle(
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryColor,
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = primaryColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            backgroundColor = Color.Transparent,
                            textColor = primaryColor,
                            disabledTextColor = primaryColor,
                            errorLeadingIconColor = Color(232, 51, 31)
                        ),
                        modifier = Modifier
                            .height(100.dp)
                    )
                }

        }
        Box(Modifier
            .padding(end=25.dp,top=25.dp)
            .fillMaxSize()
            , contentAlignment = Alignment.TopEnd
        ){
            Icon(painterResource(Res.drawable.exit), contentDescription = null,
                tint = primaryColor, modifier = Modifier.clickable {
                    inventCardView=false
                })
        }
    }
}


//=====================================================================================
//bigSignCard
//Input values:
//              key:String - user key
//              topId:Int - top id
//              backgroundColor:Color - background color
//              primaryColor:Color - primary color
//=====================================================================================
@Composable
fun bigSignCard(type:Int, backgroundColor:Color, primaryColor:Color){
    var token by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var updatetoken by remember { mutableStateOf(false) }
    val server = Reuests()
    val scope = rememberCoroutineScope()
    if(updatetoken) {
        scope.launch {
            isError = server.signTop(token)
            if(!isError) signState=false
            updateProfile=true
            updatetoken=false

        }
    }
    Box(Modifier.width(500.dp)
        .height(500.dp)
        .background(backgroundColor, RoundedCornerShape(10)),
        contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painterResource(Res.drawable.topinvent), contentDescription = null,
                modifier = Modifier.padding(bottom = 25.dp).height(180.dp)
            )
            Text(
                "Присоединиться", style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = primaryColor
                )
            )
            Box(
                Modifier
                    .padding(top = 25.dp)
                    .width(400.dp)
                    .height(100.dp)
                    .border(3.dp, primaryColor, RoundedCornerShape(10)), contentAlignment = Alignment.Center
            ) {
                TextField(
                    value = token,
                    onValueChange = { token = it },
                    singleLine = true,
                    isError = isError,
                    //enabled = false,
                    textStyle = TextStyle(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor,
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = primaryColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Color.Transparent,
                        textColor = primaryColor,
                        disabledTextColor = primaryColor,
                        errorLeadingIconColor = Color(232, 51, 31)
                    ),
                    modifier = Modifier
                        .height(100.dp)
                )
            }
            Box(
                Modifier
                    .padding(top = 15.dp)
                    .width(250.dp)
                    .height(70.dp)
                    .background(Color(109, 150, 255), RoundedCornerShape(10))
                    .clickable {
                        updatetoken = true
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Присоединиться", style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )
                )
            }
        }
        Box(Modifier
            .padding(end=25.dp,top=25.dp)
            .fillMaxSize()
            , contentAlignment = Alignment.TopEnd
        ){
            Icon(painterResource(Res.drawable.topinvent), contentDescription = null,
                tint = primaryColor, modifier = Modifier.clickable {
                    signState=false
                })
        }
    }
}

//=====================================================================================
//createGameItembig
//Input values:
//              type:Int - type
//              backgroundColor:Color - background color
//              primaryColor:Color - primary color
//=====================================================================================
@Composable
fun createGameItembig(type:Int, backgroundColor:Color, primaryColor:Color){
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isErrorName by remember { mutableStateOf(false) }
    var updatetoken by remember { mutableStateOf(false) }
    val server = Reuests()
    val scope = rememberCoroutineScope()
    if(updatetoken) {
        scope.launch {
            if(name=="") isErrorName=true
            else{
                isErrorName=false
                if(server.createTop(name,description)==true){
                    createWindow=false
                }
                updateProfile=true
                updatetoken=false
            }
        }
    }
    Box(Modifier.width(600.dp).height(600.dp)
        .background(backgroundColor, RoundedCornerShape(10)),
        contentAlignment = Alignment.Center){
        Column(Modifier.padding(top=50.dp, bottom = 25.dp).width(500.dp),horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Создать ${if(type==1) "топ"
                else if(type==2) "награды"
                else "дерево"}", style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = primaryColor
                )
            )
            Box(Modifier.padding(top=25.dp, bottom = 15.dp).fillMaxWidth(), contentAlignment = Alignment.CenterStart){
                Text("Название:", style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryColor
                    )
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(3.dp, primaryColor, RoundedCornerShape(10)), contentAlignment = Alignment.Center
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    singleLine = true,
                    isError = isErrorName,
                    //enabled = false,
                    textStyle = TextStyle(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor,
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = primaryColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Color.Transparent,
                        textColor = primaryColor,
                        disabledTextColor = primaryColor,
                        errorLeadingIconColor = Color(232, 51, 31)
                    ),
                    modifier = Modifier
                        .height(100.dp)
                )
            }
            Box(Modifier.padding(top=25.dp, bottom = 15.dp).fillMaxWidth(), contentAlignment = Alignment.CenterStart){
                Text("Описание:", style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = primaryColor
                )
                )
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(3.dp, primaryColor, RoundedCornerShape(10)), contentAlignment = Alignment.Center
            ) {
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    singleLine = true,
                    //isError = isErrorName,
                    //enabled = false,
                    textStyle = TextStyle(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor,
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = primaryColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        backgroundColor = Color.Transparent,
                        textColor = primaryColor,
                        disabledTextColor = primaryColor,
                        errorLeadingIconColor = Color(232, 51, 31)
                    ),
                    modifier = Modifier
                        .height(200.dp)
                )
            }
            Box(
                Modifier
                    .padding(top = 25.dp)
                    .width(250.dp)
                    .height(70.dp)
                    .background(Color(109, 150, 255), RoundedCornerShape(10))
                    .clickable {
                        updatetoken = true
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Создать", style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )
                )
            }
        }
        Box(Modifier
            .padding(end=25.dp,top=25.dp)
            .fillMaxSize()
            , contentAlignment = Alignment.TopEnd
        ){
            Icon(painterResource(Res.drawable.exit), contentDescription = null,
                tint = primaryColor, modifier = Modifier.clickable {
                    createWindow=false
                })
        }
    }
}