//##################################################################################################
//##################################################################################################
//#####################                        Awards                        #######################
//##################################################################################################
//####  Author:Kulishov I.V.                         ###############################################
//####  Version:0.1.0                                ###############################################
//####  Date:02.01.2025                              ###############################################
//##################################################################################################
//##################################################################################################
package ru.kulishov.statistic

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import ru.kulishov.statistic.composeapp.generated.resources.Res
import ru.kulishov.statistic.composeapp.generated.resources.elipse
import ru.kulishov.statistic.composeapp.generated.resources.exit
import ru.kulishov.statistic.composeapp.generated.resources.man
import ru.kulishov.statistic.composeapp.generated.resources.trophy
import ru.kulishov.statistic.composeapp.generated.resources.trophyactive
import ru.kulishov.statistic.composeapp.generated.resources.woman

var awardCardView by mutableStateOf(false)
var selectedAward by mutableStateOf(Award(-1,"","", emptyList(),false))
var createAwardState by  mutableStateOf(false)

@Composable
fun awardsScreen(awardListID:Int, system:Int,backgroundColor: Color,primaryColor:Color,themeColor:Color, defaultColor:Color, secondColor:Color){
    val server = Reuests()

    var isAdmin by remember { mutableStateOf(false) }
    var corutState by remember {   mutableStateOf(1)}
    var usernames by remember { mutableStateOf(emptyList<Username>()) }
    var userViews by remember { mutableStateOf(Username(-1,1,"")) }
    val scope = rememberCoroutineScope()

    if(corutState==1){
        LaunchedEffect(corutState){
            launch {
                isAdmin=server.checkAdminAward(awardListID)
                server.getUsernameList(awardActiveBody.users,
                    onSuccess = {res-> usernames=res},
                    onFailure = {res -> println(res) })
                println("$isAdmin $usernames")
                server.getId(
                    onSuccess = {
                            res->
                        val fUser = usernames.find { cr -> cr.userId ==res }
                        if(fUser!=null){
                            userViews=fUser
                        }

                    },
                    onFailure = {res-> println(res) }
                )
                corutState=0
            }
        }
        //scope.launch { }
    }
    if(corutState==3){
        println("corutState 3 start")

        LaunchedEffect(corutState==3){
            launch {
                println("update result launch start")
                server.awardStat(awardActiveBody.id, selectedAward.awardId,userViews.userId,
                    onSuccess = {
                        println("successs")
                    },
                    onFailure = {res->
                        println(res)
                    })
                server.getAwardList(awardListID,
                    onSuccess = {
                    res-> awardActiveBody=res
                        println(awardActiveBody)
                },
                    onFailure = {
                        res -> println(res)
                    })
                corutState=0
            }
        }
    }
    if(corutState==4){
        println("corutState 4 start")

        LaunchedEffect(corutState==4){
            launch {
                server.getAwardList(awardListID,
                    onSuccess = {
                            res-> awardActiveBody=res
                        println(awardActiveBody)
                    },
                    onFailure = {
                            res -> println(res)
                    })
                corutState=0
            }
        }
    }




    println(userViews.userId)
    if(userViews.userId!=-1){
        println("$awardListID $userViews")
        Column(
            Modifier.fillMaxSize()
                .background(backgroundColor)
        ) {
            shapkaDesctop(darkTheme.background, darkTheme.primary, darkTheme.secondary)
      Row {
          if (isAdmin) {
              peopleDrawer(
                  invent = {
                      inventCardView=true
                  },
                  updatePeople = {
                      usernames-> userViews=usernames
                  },
                  awardActiveBody.admin,
                  usernames,
                  Color(32, 32, 32),
                  primaryColor,
                  themeColor,
                  Color(122, 122, 122),
                  secondColor
              )
          }
          Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                  Text(
                      userViews.username, style = TextStyle(
                          fontSize = 40.sp,
                          fontWeight = FontWeight.Bold,
                          color = primaryColor
                      ), modifier = Modifier.padding(top = 40.dp, bottom = 25.dp)
                  )
                  LazyVerticalGrid(columns = GridCells.Adaptive(if(system==1) 200.dp else 150.dp,),
                      contentPadding = PaddingValues(25.dp)
                  ){
                      items(awardActiveBody.awardList){ award ->
                      var isActive = award.userActive.contains(userViews.userId)
                          Box(Modifier.clickable {
                              selectedAward=award
                              awardCardView=true
                          }) {
                              previewAwardCard(
                                  Award(
                                      award.awardId,
                                      award.name,
                                      award.description,
                                      award.userActive,
                                      isActive
                                  ),
                                  system, Color(32, 32, 32), primaryColor
                              )
                          }
                      }
                      if(isAdmin) {
                          item {
                              Box(
                                  Modifier.padding(15.dp).width(if (system == 1) 200.dp else 150.dp)
                                      .height(if (system == 1) 200.dp else 150.dp)
                                      .background(
                                          themeColor,
                                          shape = RoundedCornerShape(if (system == 1) 10 else 7)
                                      )
                                      .clickable {
                                          createAwardState = true
                                      },
                                  contentAlignment = Alignment.Center
                              ) {
                                  Text(
                                      "+", style = TextStyle(
                                          fontSize = 32.sp,
                                          fontWeight = FontWeight.Bold,
                                          color = primaryColor
                                      )
                                  )
                              }
                          }
                      }
                  }
              }
          }
      }

      }
        if(awardCardView){
            inventCardView=false
            createAwardState=false
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                bigAwardCard(Award(selectedAward.awardId
                , selectedAward.name, selectedAward.description, selectedAward.userActive,
                    selectedAward.userActive.contains(userViews.userId)),awardListID,isAdmin,system,Color(32, 32, 32),primaryColor,
                    onSave = {result->
                        println("result check onSave")
                        corutState=3
                    })
            }
        }
        if (inventCardView) {
            awardCardView = false
            createAwardState=false
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                bigUnventCardTop(2, awardListID, Color(32, 32, 32), primaryColor)
            }
        }
        if(createAwardState){
            awardCardView=false
            inventCardView=false
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                createAward(1,awardListID,{corutState=4},Color(32, 32, 32),primaryColor)
            }

        }
    }else{

    }
}

@Composable
fun previewAwardCard(award: Award,system: Int, backgroundColor: Color, primaryColor: Color){
    Box(Modifier.padding(15.dp).width(if(system==1) 200.dp else 150.dp).height(if(system==1) 200.dp else 150.dp)
        .background(backgroundColor, shape = RoundedCornerShape(if(system==1) 10 else 7)),
        contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if(award.status){
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painterResource(Res.drawable.elipse),
                        modifier = Modifier.width(if (system == 1) 100.dp else 120.dp)
                            .height(if (system == 1) 100.dp else 120.dp)
                            .scale(1.5f),
                        contentDescription = ""
                    )

                    Image(
                        painterResource(Res.drawable.trophyactive),
                        modifier = Modifier.width(if (system == 1) 100.dp else 75.dp)
                            .height(if (system == 1) 100.dp else 75.dp),
                        contentDescription = ""
                    )
                }
            }else{
                Image(painterResource(Res.drawable.trophy), modifier =Modifier.width(if(system==1) 100.dp else 75.dp)
                    .height(if(system==1) 100.dp else 75.dp), contentDescription = "" )
            }
            Text(
                award.name, style = TextStyle(
                    fontSize = if(system==1) 24.sp else 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                ), modifier = Modifier.padding(top = 25.dp)
            )
        }

    }
}


//=====================================================================================
//bigAwardCard
//Input values:
//              award:Award - award
//              awardListId:Int - award list id
//              backgroundColor:Color - background color
//              primaryColor:Color - primary color
//              onSave:()->Unit - action on save
//=====================================================================================
@Composable
fun bigAwardCard(award:Award,awardListId:Int,isAdmin:Boolean, system: Int, backgroundColor:Color, primaryColor:Color, onSave:(Boolean)->Unit){
    var result by remember { mutableStateOf(award.status) }
    Box(Modifier.width(500.dp)
        .height(500.dp)
        .background(backgroundColor, RoundedCornerShape(10)),
        contentAlignment = Alignment.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if(result){
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painterResource(Res.drawable.elipse),
                        modifier = Modifier.width(if (system == 1) 120.dp else 120.dp)
                            .height(if (system == 1) 120.dp else 120.dp)
                            .scale(1.5f),
                        contentDescription = ""
                    )

                    Image(
                        painterResource(Res.drawable.trophyactive),
                        modifier = Modifier.width(if (system == 1) 120.dp else 75.dp)
                            .height(if (system == 1) 120.dp else 75.dp),
                        contentDescription = ""
                    )
                }
            }else{
                Image(painterResource(Res.drawable.trophy), modifier =Modifier.width(if(system==1) 120.dp else 75.dp)
                    .height(if(system==1) 120.dp else 75.dp), contentDescription = "" )
            }
            Text(award.name, style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
            )
            Text(award.description, style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium,
                color = primaryColor
            )
            )
            if(isAdmin) {
                Row(Modifier.padding(top = 15.dp)) {
                    Box(
                        Modifier
                            .padding(end = 15.dp)
                            .width(190.dp)
                            .height(70.dp)
                            .background(Color(232, 51, 31), RoundedCornerShape(10))
                            .clickable {

                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Выгнать", style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryColor
                            )
                        )
                    }
                    Box(
                        Modifier
                            .padding(start = 15.dp)
                            .width(190.dp)
                            .height(70.dp)
                            .border(width = 3.dp, primaryColor, RoundedCornerShape(10))
                            .clickable {
                                result = !result
                                //award.status=!award.status
                                onSave(result)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Row() {
                            Box(
                                Modifier.fillMaxHeight().weight(1f)
                                    .background(
                                        if (!result) Color(
                                            109,
                                            150,
                                            255
                                        ) else Color.Transparent
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Нет", style = TextStyle(
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = primaryColor
                                    )
                                )
                            }
                            Box(
                                Modifier.fillMaxHeight().weight(1f)
                                    .background(
                                        if (result) Color(
                                            109,
                                            150,
                                            255
                                        ) else Color.Transparent
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "Есть", style = TextStyle(
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = primaryColor
                                    )
                                )
                            }
                        }

                    }
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

                    awardCardView=false
                })
        }
    }
}

//=====================================================================================
//createGameItembig
//Input values:
//              type:Int - type
//              awardListId:Int - id
//              backgroundColor:Color - background color
//              primaryColor:Color - primary color
//=====================================================================================
@Composable
fun createAward(type:Int,awardListId:Int,onUpdateScreen: () -> Unit,  backgroundColor:Color, primaryColor:Color){
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
                when(type){
                    1->{
                        if(server.createAward(awardListId,name,description)==true){
                            createAwardState=false
                        }
                    }

                }

                updatetoken=false
            }
        }
    }
    Box(Modifier.width(600.dp).height(600.dp)
        .background(backgroundColor, RoundedCornerShape(10)),
        contentAlignment = Alignment.Center){
        Column(Modifier.padding(top=50.dp, bottom = 25.dp).width(500.dp),horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Создать ${if(type==1) "награду"
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

                        onUpdateScreen()
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
                    createAwardState=false
                })
        }
    }
}