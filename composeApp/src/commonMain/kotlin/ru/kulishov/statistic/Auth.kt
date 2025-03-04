package ru.kulishov.statistic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

var userTek by mutableStateOf(WhoamiRequest("",0,1, emptyList(), emptyList(), emptyList(),
    emptyList(), emptyList(), emptyList()
))
//=====================================================================================
//bigAuthBlock
//Input values:
//              backgroundColor:Color - background color
//              primaryColor:Color - primary color
//              themeColor:Color - theme color
//              secondColor:Color - second color
//=====================================================================================

@Composable
fun bigAuthBlock(backgroundColor:Color,primaryColor:Color,themeColor:Color,secondColor: Color){
    var isError by remember { mutableStateOf(false) }
    var authFlag by remember { mutableStateOf(false) }
    if(authFlag){
        val scope = rememberCoroutineScope()
        scope.launch {
            val server = Reuests()
            var flag = false
            server.whoami(onSuccess = {
                res-> flag=true
                userTek=res
                //state=1
            }, onFailure = {
                res-> println(res)
                isError=true
            })
            if(flag){
                listMyTop = emptyList()
                listFriendTop = emptyList()

                listMyAward = emptyList()
                listFriendAward = emptyList()

                listMyThree= emptyList()
                listFriendThree = emptyList()
                for(x in userTek.myTop){
                    server.getTop(x, onSuccess ={
                        res-> listMyTop+=res
                    }, onFailure = {
                        res-> println(res)
                    })
                }
                for(x in userTek.inTop){
                    server.getTop(x, onSuccess ={
                            res-> listFriendTop+=res
                    }, onFailure = {
                            res-> println(res)
                    })
                }
                println(userTek)
                for(x in userTek.myAward){
                    server.getAwardList(x, onSuccess ={
                            res-> listMyAward+=res
                    }, onFailure = {
                            res-> println(res)
                    })
                }
                for(x in userTek.inAward){
                    server.getAwardList(x, onSuccess ={
                            res-> listFriendAward+=res
                    }, onFailure = {
                            res-> println(res)
                    })
                }

                flag=false
                state =1
            }
            authFlag=false
        }
    }
    Box(Modifier.width(500.dp)
        .height(457.dp)
        .background(backgroundColor, shape = RoundedCornerShape(10)),
        contentAlignment = Alignment.TopCenter){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top=25.dp, bottom = 25.dp)){
                Text("DoD.", style = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = primaryColor
                )
                )
                Text("stat", style = TextStyle(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = themeColor
                )
                )

            }
            Text("Вход в аккаунт", style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            ))
            TextField(value = loginInp,
                onValueChange ={ loginInp = it},
                label = {
                    Text(text = "Логин", style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )
                    )
                },
                isError = isError,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = primaryColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = primaryColor,
                    backgroundColor = secondColor,
                    textColor = primaryColor,
                    disabledTextColor = primaryColor,
                   errorLeadingIconColor = Color(232,51,31)
                ),
                modifier = Modifier.padding(top=25.dp)
                    .width(400.dp)
            )
            TextField(value = passwordInp,
                onValueChange ={ passwordInp = it},
                label = {
                    Text(text = "Пароль", style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )
                    )
                },
                visualTransformation = PasswordVisualTransformation() ,
                isError = isError,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = primaryColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = primaryColor,
                    backgroundColor = secondColor,
                    textColor = primaryColor,
                    disabledTextColor = primaryColor,
                    errorLeadingIconColor = Color(232,51,31)
                ),
                modifier = Modifier.padding(top=25.dp, bottom = 25.dp)
                    .width(400.dp)
            )
            Box(Modifier
                .padding(bottom = 15.dp)
                .width(400.dp)
                .height(70.dp)
                .background(themeColor, RoundedCornerShape(15))
                .clickable {
                    /*val req = server.authorization(login,password)
                    if(req!=null){
                        isError=false
                        key=req
                        state=1
                    }else isError=true*/
                    authFlag=true
                },
                contentAlignment = Alignment.Center){
                Text("Войти", style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("У меня нет акаунта. ", style = TextStyle(
                    fontSize = 20.sp,
                    color = primaryColor
                )
                )
                Text("Создать", style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = themeColor
                ))
            }
        }
    }
}

//=====================================================================================
//authScreenDesktop
//Input values:
//              backgroundColor:Color - background color
//              primaryColor:Color - primary color
//              themeColor:Color - theme color
//              secondColor:Color - second color
//=====================================================================================
@Composable
fun authScreenDesktop(backgroundColor:Color,primaryColor:Color,themeColor:Color, secondColor:Color){
    Box(modifier = Modifier.fillMaxSize()
        .background(backgroundColor),
        contentAlignment = Alignment.Center){
        bigAuthBlock(secondColor,primaryColor,themeColor,backgroundColor)
    }
}
//=====================================================================================
//compactAuthBlock
//Input values:
//              backgroundColor:Color - background color
//              primaryColor:Color - primary color
//              themeColor:Color - theme color
//              secondColor:Color - second color
//=====================================================================================
var loginInp by   mutableStateOf("")
var passwordInp by mutableStateOf("")
@Composable
fun compactAuthBlock(backgroundColor:Color,primaryColor:Color,themeColor:Color,secondColor: Color){

    var isError by remember { mutableStateOf(false) }
    var authFlag by remember { mutableStateOf(false) }
    if(authFlag){
        val scope = rememberCoroutineScope()
        scope.launch {

        }
    }
    Box(Modifier.width(300.dp)
        .height(400.dp)
        .background(backgroundColor, shape = RoundedCornerShape(10)),
        contentAlignment = Alignment.TopCenter){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top=15.dp, bottom = 15.dp)){
                Text("DoD.", style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = primaryColor
                )
                )
                Text("stat", style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = themeColor
                )
                )

            }
            Text("Вход в аккаунт", style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            ))
            TextField(value = loginInp,
                onValueChange ={ loginInp = it},
                label = {
                    Text(text = "Логин", style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )
                    )
                },
                isError = isError,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = primaryColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = primaryColor,
                    backgroundColor = secondColor,
                    textColor = primaryColor,
                    disabledTextColor = primaryColor,
                    errorLeadingIconColor = Color(232,51,31)
                ),
                modifier = Modifier.padding(top=15.dp)
                    .width(250.dp)
            )
            TextField(value = passwordInp,
                onValueChange ={ passwordInp = it},
                label = {
                    Text(text = "Пароль", style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )
                    )
                },
                visualTransformation = PasswordVisualTransformation() ,
                isError = isError,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = primaryColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = primaryColor,
                    backgroundColor = secondColor,
                    textColor = primaryColor,
                    disabledTextColor = primaryColor,
                    errorLeadingIconColor = Color(232,51,31)
                ),
                modifier = Modifier.padding(top=15.dp, bottom = 15.dp)
                    .width(250.dp)
            )
            Box(Modifier
                .padding(bottom = 5.dp)
                .width(250.dp)
                .height(50.dp)
                .background(themeColor, RoundedCornerShape(15))
                .clickable {
                    /*val req = server.authorization(login,password)
                    if(req!=null){
                        isError=false
                        key=req
                        state=1
                    }else isError=true*/
                    authFlag=true
                },
                contentAlignment = Alignment.Center){
                Text("Войти", style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor
                )
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("У меня нет акаунта. ", style = TextStyle(
                    fontSize = 16.sp,
                    color = primaryColor
                )
                )
                Text("Создать", style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = themeColor
                ))
            }
        }
    }
}

//=====================================================================================
//authScreenPhone
//Input values:
//              backgroundColor:Color - background color
//              primaryColor:Color - primary color
//              themeColor:Color - theme color
//              secondColor:Color - second color
//=====================================================================================
@Composable
fun authScreenPhone(backgroundColor:Color,primaryColor:Color,themeColor:Color, secondColor:Color){
    Box(modifier = Modifier.fillMaxSize()
        .background(backgroundColor),
        contentAlignment = Alignment.Center){
        compactAuthBlock(secondColor,primaryColor,themeColor,backgroundColor)
    }
}