//##################################################################################################
//##################################################################################################
//#####################                 DoD.stat shared code                 #######################
//##################################################################################################
//####  Author:Kulishov Ilua                         ###############################################
//####  Version:0.0.1                                ###############################################
//####  Date:29.10.2024                              ###############################################
//##################################################################################################
//##################################################################################################
package ru.kulishov.statistic
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.DigestAuthCredentials
import io.ktor.client.plugins.auth.providers.digest
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.util.logging.Logger
import io.ktor.utils.io.charsets.Charsets.UTF_8
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Resource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import stata.composeapp.generated.resources.Res
import stata.composeapp.generated.resources.compose_multiplatform
import stata.composeapp.generated.resources.elipse
import stata.composeapp.generated.resources.exit
import stata.composeapp.generated.resources.man
import stata.composeapp.generated.resources.more
import stata.composeapp.generated.resources.woman



var modalState by  mutableStateOf(false)



//=====================================================================================
//shapka for desktop
//=====================================================================================
@Preview
@Composable
fun shapkaDesctop(){
    Box(Modifier.fillMaxWidth().height(80.dp).background(color= darkTheme.background), contentAlignment = Alignment.CenterStart){
        Row(Modifier.padding(start=100.dp, end=100.dp), verticalAlignment = Alignment.CenterVertically){
            Text("DoD.", style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold,
                color = darkTheme.primary
            ))
            Text("stat", style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold,
                color = darkTheme.secondary
            ))
            Box(modifier = Modifier.padding(start=100.dp)){
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Home", style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light,
                        color = darkTheme.primary
                    ), modifier = Modifier.padding(end = 27.dp))
                    Text("Top", style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light,
                        color = darkTheme.primary
                    ), modifier = Modifier.padding(end = 27.dp, start=27.dp))
                    Text("Three", style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light,
                        color = darkTheme.primary
                    ), modifier = Modifier.padding(end = 27.dp, start=27.dp))
                    Text("Award", style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light,
                        color = darkTheme.primary
                    ), modifier = Modifier.padding(end = 27.dp, start=27.dp))
                    Text("Api", style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light,
                        color = darkTheme.primary
                    ), modifier = Modifier.padding( start=27.dp))

                }
            }
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
                Icon(painter = painterResource(Res.drawable.more), contentDescription = "")
            }
        }
    }
}

//=====================================================================================
//shapka for mobile device
//=====================================================================================
@Composable
fun shapkaPhone(){
    Box(Modifier.fillMaxWidth().height(60.dp).background(color= darkTheme.background), contentAlignment = Alignment.Center){
Row {
    Text(
        "DoD.", style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = darkTheme.primary
        )
    )
    Text(
        "stat", style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = darkTheme.secondary
        )
    )
}
            Box(Modifier.padding(end=15.dp).fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
                Icon(painter = painterResource(if(modalState) Res.drawable.exit else Res.drawable.more), contentDescription = "",
                    tint = darkTheme.primary, modifier = Modifier.scale(0.7f).clickable{
                        modalState=!modalState
                    })
            }
    }
}


@Composable
fun phoneNavigateModuleSite(){
    Box(modifier = Modifier.fillMaxSize().background(darkTheme.background), contentAlignment = Alignment.TopCenter){
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top=50.dp)) {
            Text("Profile", style = TextStyle(
                fontSize = 22.sp,
                color = darkTheme.primary
            ), modifier = Modifier.padding(15.dp)
            )
            Text("Main", style = TextStyle(
                fontSize = 22.sp,
                color = darkTheme.primary
            ), modifier = Modifier.padding(15.dp)
            )
            Text("Top", style = TextStyle(
                fontSize = 22.sp,
                color = darkTheme.primary
            ), modifier = Modifier.padding(15.dp)
            )
            Text("Three", style = TextStyle(
                fontSize = 22.sp,
                color = darkTheme.primary
            ), modifier = Modifier.padding(15.dp)
            )
            Text("Award", style = TextStyle(
                fontSize = 22.sp,
                color = darkTheme.primary
            ), modifier = Modifier.padding(15.dp)
            )
            Text("Api", style = TextStyle(
                fontSize = 22.sp,
                color = darkTheme.primary
            ), modifier = Modifier.padding(15.dp)
            )
        }
    }
}
//#####################################################################################################################
//###############################                    Тема приложения                    ###############################
//#####################################################################################################################
val darkTheme =    darkColors(background = Color(7,7,7), primary = Color.White, secondary =Color(246, 0, 123)
, onPrimary = Color(169,11,238))


