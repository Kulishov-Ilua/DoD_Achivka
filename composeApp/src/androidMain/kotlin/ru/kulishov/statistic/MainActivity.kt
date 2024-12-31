package ru.kulishov.statistic

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.lightColors
import androidx.compose.material.rememberDrawerState
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                shapkaPhone()
                if(modalState){
                    phoneNavigateModule()
                }else{
                    CompactProfile("")
                }

            }

        }

    }
}






@Composable
fun phoneNavigateModule(){
    Box(modifier = Modifier.fillMaxSize().background(darkTheme.background), contentAlignment = Alignment.TopCenter){
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top=50.dp)) {
            Text("Profile", style = TextStyle(
                fontSize = 22.sp,
                color = if(state==1) darkTheme.secondary else darkTheme.primary
            ), modifier = Modifier.padding(15.dp).clickable {
                state=1
                modalState=false
            }
            )
            Text("Top", style = TextStyle(
                fontSize = 22.sp,
                color = if(state==2) darkTheme.secondary else darkTheme.primary
            ), modifier = Modifier.padding(15.dp).clickable {
                state=2
                modalState=false
            }
            )
            Text("Three", style = TextStyle(
                fontSize = 22.sp,
                color = if(state==3) darkTheme.secondary else darkTheme.primary
            ), modifier = Modifier.padding(15.dp).clickable {
                state=3
                modalState=false
            }
            )
            Text("Award", style = TextStyle(
                fontSize = 22.sp,
                color = if(state==4) darkTheme.secondary else darkTheme.primary
            ), modifier = Modifier.padding(15.dp).clickable {
                state=4
                modalState=false
            }
            )
        }
    }
}

