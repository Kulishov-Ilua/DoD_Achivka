//##################################################################################################
//##################################################################################################
//#####################                 DoD.stat web module                  #######################
//##################################################################################################
//####  Author:Kulishov Ilua                         ###############################################
//####  Version:0.0.1                                ###############################################
//####  Date:29.10.2024                              ###############################################
//##################################################################################################
//##################################################################################################

package ru.kulishov.statistic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.launch


enum class WindowSizeClass { Compact, Medium, Expanded }


// Функция для определения класса размеров экрана
fun calculateWindowSizeClass(windowDpSize: DpSize): WindowSizeClass {
    return when {
        windowDpSize.width < 1000.dp -> WindowSizeClass.Compact
        windowDpSize.width < 840.dp -> WindowSizeClass.Medium
        else -> WindowSizeClass.Expanded
    }
}

@Composable
fun rememberWindowSizeClass(): WindowSizeClass {
    // Получаем ширину окна в dp, корректируя на devicePixelRatio
    val windowWidthDp: Dp = remember {
        (window.innerWidth / window.devicePixelRatio).dp
    }

    // Используем функцию для определения класса размера
    return calculateWindowSizeClass(DpSize(windowWidthDp, 0.dp))
}




@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        var whoami by remember { mutableStateOf(WhoamiRequest("",1,1, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList())) }


        val userAgent = window.navigator.userAgent.lowercase()
        if(userAgent.contains("mobile")||userAgent.contains("android")||userAgent.contains("iphone")){

        }else{
            when(state){
                -1 -> authScreenDesktop(
                    darkTheme.background, darkTheme.primary, darkTheme.secondary,
                    Color(32,32,32))
                1 -> ExpandedProfile(key)
                11 -> {
                    var viewInt by remember { mutableStateOf(false) }
                    LaunchedEffect(true){
                        launch {
                            var flagId = false
                            usersSort = emptyList()
                            userTopList = emptyList()
                            toptrunList = emptyList()
                            usernames = emptyList()
                            val server = Reuests()
                            server.getTop(topActive, onSuccess = { res ->
                                top = res
                                if (top.id != -1) flagId = true
                            }, onFailure = { res ->
                                println(res)
                            })
                            println("top: $top")

                            if (flagId) {

                                for (x in top.users) {
                                    if (userTopList.find { cr -> cr == x.user } == null) {
                                        userTopList += x.user
                                    }
                                }
                                server.getUsernameList(userTopList,
                                    onFailure = { res ->
                                        println(res)
                                    },
                                    onSuccess = { res ->
                                        usernames = res
                                    })
                                println(usernames)
                            }
                            usersSort = top.users
                            usersSort = usersSort.sortedBy { cr -> (cr.value * -1) }
                            var count = 0
                            for (x in usersSort) {
                                count++
                                val name = usernames.find { cr -> cr.userId == x.user }
                                if (toptrunList.find { cr ->
                                        cr == toptrun(
                                            x.user,
                                            name!!.username,
                                            name!!.sex,
                                            count,
                                            x.value
                                        )
                                    } == null) {
                                    toptrunList += toptrun(
                                        x.user,
                                        name!!.username,
                                        name!!.sex,
                                        count,
                                        x.value
                                    )
                                }

                            }
                            isAdmin = server.checkAdminTop(topActive)
                            println("admin: $isAdmin")
                            viewInt=true

                        }
                    }
                    when(viewInt){
                        true -> {
                            topScreenDesktop(
                                topActive,
                                darkTheme.background,
                                darkTheme.primary,
                                darkTheme.secondary,
                                Color(32, 32, 32),
                                darkTheme.onPrimary
                            )
                        }
                        false->{
                            topScreenDesktop(
                                topActive,
                                darkTheme.background,
                                darkTheme.primary,
                                darkTheme.secondary,
                                Color(32, 32, 32),
                                darkTheme.onPrimary
                            )
                        }
                    }

                }
                21 ->{
                    awardsScreen(awardActive, 1,darkTheme.background, darkTheme.primary, darkTheme.secondary,
                        Color(32,32,32),
                        darkTheme.onPrimary)
                }

            }
        }
    }

}

@Composable
fun compactSite(){
    Column() {
            if(modalState){
                Box(modifier = Modifier.fillMaxSize().background(darkTheme.background), contentAlignment = Alignment.Center){
                    Column {

                    }
                }
            }else {
                if(state==-1) authScreenPhone(darkTheme.background, darkTheme.primary, darkTheme.secondary,
                    Color(32,32,32))
                if (state == 1) CompactProfile(key)
            }

    }

}


