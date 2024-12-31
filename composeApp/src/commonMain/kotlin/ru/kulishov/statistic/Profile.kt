package ru.kulishov.statistic
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
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
import ru.kulishov.statistic.composeapp.generated.resources.man
import ru.kulishov.statistic.composeapp.generated.resources.woman


var listMyTop by mutableStateOf( emptyList<Top>())
var listFriendTop by mutableStateOf( emptyList<Top>())

var listMyAward by mutableStateOf( emptyList<UserAwardList>())
var listFriendAward by mutableStateOf( emptyList<UserAwardList>())

var listMyThree by mutableStateOf( emptyList<Three>())
var listFriendThree by mutableStateOf( emptyList<Three>())

var topActive by  mutableStateOf(0)
var topActiveBody by  mutableStateOf(Top(-1,"","","", UserTop(-1,0), emptyList()))

var sigType by mutableStateOf(1)

var updateProfile by mutableStateOf(false)
var signState by mutableStateOf(false)

var createWindow by mutableStateOf(false)

//=====================================================================================
//profile block for desktop
//Input values:
//              sex:Int - user sex
//              name:String - username
//              three:Int - count of three
//              top:Int - count of top
//              award:Int - count of award
//=====================================================================================
@Composable
fun mainProfileDesctop(sex:Int, name:String, three:Int, top:Int, award:Int){

    Box(Modifier.fillMaxWidth().height(700.dp).background(darkTheme.background), contentAlignment = Alignment.Center){
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.padding(end=110.dp).width(434.dp).height(434.dp), contentAlignment = Alignment.Center){
                Image(painterResource(Res.drawable.elipse), null, modifier = Modifier.scale(2f))
                Image(painter = painterResource(if(sex==0) Res.drawable.woman else Res.drawable.man), null
                    , modifier = Modifier.scale(1f))

            }
            Box(Modifier.padding(start = 110.dp)){
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = name, style = TextStyle(
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = darkTheme.primary
                    )
                    )
                    Row(Modifier.padding(top=25.dp),verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier
                            .padding(end=15.dp)
                            .width(140.dp)
                            .height(78.dp)
                            .border(width = 3.dp, color = darkTheme.primary, shape = RoundedCornerShape(5)),
                            contentAlignment = Alignment.Center){
                            Text("Threes: $three", style = TextStyle(
                                fontSize = 20.sp,
                                color = darkTheme.primary
                            )
                            )
                        }
                        Box(
                            Modifier
                            .padding(start=15.dp, end=15.dp)
                            .width(140.dp)
                            .height(78.dp)
                            .border(width = 3.dp, color = darkTheme.primary, shape = RoundedCornerShape(5)),
                            contentAlignment = Alignment.Center){
                            Text("Tops: $top", style = TextStyle(
                                fontSize = 20.sp,
                                color = darkTheme.primary
                            )
                            )
                        }
                        Box(
                            Modifier
                            .padding(start=15.dp)
                            .width(140.dp)
                            .height(78.dp)
                            .border(width = 3.dp, color = darkTheme.primary, shape = RoundedCornerShape(5)),
                            contentAlignment = Alignment.Center){
                            Text("Awards: $award", style = TextStyle(
                                fontSize = 20.sp,
                                color = darkTheme.primary
                            )
                            )
                        }
                    }
                }
            }
        }
    }
}

//=====================================================================================
//profile block for desktop
//Input values:
//              sex:Int - user sex
//              name:String - username
//              three:Int - count of three
//              top:Int - count of top
//              award:Int - count of award
//=====================================================================================
@Composable
fun mainProfilePhone(sex:Int, name:String, three:Int, top:Int, award:Int){
    Box(Modifier.fillMaxWidth().background(darkTheme.background)){
        Box(Modifier.padding(end=0.dp).fillMaxWidth(), contentAlignment = Alignment.Center){
            Image(painterResource(Res.drawable.elipse), null, modifier = Modifier.scale(2f))
            Image(painter = painterResource(if(sex==0)Res.drawable.woman else Res.drawable.man), null
                , modifier = Modifier.scale(1f))

        }
        Box(Modifier.padding(top=300.dp).fillMaxWidth(), contentAlignment = Alignment.Center){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = name, style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkTheme.primary
                )
                )
                Row(Modifier.padding(top=15.dp, bottom = 25.dp),verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                        .padding(end=7.5.dp)
                        .width(100.dp)
                        .height(55.dp)
                        .border(width = 3.dp, color = darkTheme.primary, shape = RoundedCornerShape(5)),
                        contentAlignment = Alignment.Center){
                        Text("Threes: $three", style = TextStyle(
                            fontSize = 16.sp,
                            color = darkTheme.primary
                        )
                        )
                    }
                    Box(
                        Modifier
                        .padding(end=7.5.dp, start = 7.5.dp)
                        .width(100.dp)
                        .height(55.dp)
                        .border(width = 3.dp, color = darkTheme.primary, shape = RoundedCornerShape(5)),
                        contentAlignment = Alignment.Center){
                        Text("Tops: $top", style = TextStyle(
                            fontSize = 16.sp,
                            color = darkTheme.primary
                        )
                        )
                    }
                    Box(
                        Modifier
                        .padding(start=7.5.dp)
                        .width(100.dp)
                        .height(55.dp)
                        .border(width = 3.dp, color = darkTheme.primary, shape = RoundedCornerShape(5)),
                        contentAlignment = Alignment.Center){
                        Text("Awards: $award", style = TextStyle(
                            fontSize = 16.sp,
                            color = darkTheme.primary
                        )
                        )
                    }
                }
            }
        }

    }
}

//=====================================================================================
//element of game mechanic for desctop
//=====================================================================================
@Composable
fun gameItemDesktop(name:String,description:String, color: Color){

    Box(Modifier.padding(start=25.dp)
        .width(400.dp)
        .height(200.dp)
        .background(color, shape = RoundedCornerShape(10))){
        Column(Modifier.padding(25.dp),horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    name, style = TextStyle(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = darkTheme.primary
                    )
                )
            }
            Text(description, style = TextStyle(
                fontSize = 24.sp,
                color = darkTheme.primary
            )
            )
        }
    }

}

//=====================================================================================
//element of game mechanic for phone
//=====================================================================================
@Composable
fun gameItemPhone(name:String,description:String, color: Color){
    Box(Modifier.padding(end=25.dp).width(300.dp).height(150.dp).background(color, shape = RoundedCornerShape(10))){
        Column(Modifier.padding(15.dp),horizontalAlignment = Alignment.CenterHorizontally) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    name, style = TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = darkTheme.primary
                    )
                )
            }
            Text(description, style = TextStyle(
                fontSize = 20.sp,
                color = darkTheme.primary
            )
            )
        }
    }

}

//=====================================================================================
//CompactProfile
//Input values:
//              key:String - user key
//=====================================================================================
@Composable
fun CompactProfile(key:String){
    listMyTop = emptyList()
    listFriendTop = emptyList()

    listMyAward = emptyList()
    listFriendAward = emptyList()

     listMyThree= emptyList()
     listFriendThree = emptyList()
    var whoami by remember {
        mutableStateOf(
            WhoamiRequest(
                "Rowan Petrov", 1, 10, emptyList(), emptyList(), emptyList(),
                emptyList(), emptyList(), emptyList()
            )
        )
    }
    /*
    val s = rememberCoroutineScope()
    s.launch {

    }
     */

    if(key!=null){
        val newWhoami = server.whoamiRequest(key)
        if(newWhoami!=null) whoami=newWhoami
    }
    if(whoami.myTop!=null){
        for(x in whoami.myTop){
            if(key!=null) {
                var top = server.getTop(key, x)
                if(top!=null) listMyTop+=top
            }
        }
    }
    if(whoami.inTop!=null){
        for(x in whoami.inTop){
            if(key!=null) {
                var top = server.getTop(key, x)
                if(top!=null) listFriendTop+=top
            }
        }
    }
    if(whoami.myThree!=null){

    }
    if(whoami.inThree!=null){

    }
    if(whoami.myAward!=null){

    }
    if(whoami.inAward!=null){

    }
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    /*ModalDrawer(
        drawerState = DrawerState(initialValue = drawerState.currentValue),
        drawerContent = {
            Box(Modifier.fillMaxSize().background(Color.Cyan))
        },
    ){*/
    Scaffold {
        Column() {
            shapkaPhone()
            LazyColumn {
                item {
                    mainProfilePhone(
                        whoami.sex,
                        whoami.username,
                        whoami.myThree.size + whoami.inThree.size,
                        whoami.myTop.size + whoami.inTop.size,
                        whoami.myAward.size + whoami.inAward.size
                    )
                }
                item {
                    Box(
                        Modifier.fillMaxWidth().height(254.dp).background(darkTheme.background)
                    ) {
                        Column {
                            Text(
                                "Tops", style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    color = darkTheme.primary
                                ), modifier = Modifier.padding(top = 25.dp, start = 25.dp)
                            )

                            LazyRow(Modifier.padding(start = 25.dp, top = 25.dp)) {
                                if (whoami.myTop != null) {
                                    items(listMyTop) { myTop ->
                                        Box(Modifier.clickable {
                                            topActive=myTop.id
                                            state =11
                                        }) {
                                            gameItemPhone(
                                                myTop.name,
                                                myTop.description,
                                                darkTheme.onPrimary
                                            )
                                        }
                                    }
                                }
                                if (whoami.inTop != null) {
                                    items(listFriendTop) { intop ->
                                        Box(Modifier.clickable {
                                            topActive=intop.id
                                            state =11
                                        }) {
                                            gameItemPhone(
                                                intop.name,
                                                intop.description,
                                                Color(122, 122, 122)
                                            )
                                        }
                                    }
                                }
                                item {
                                    val nC = Color(
                                        darkTheme.secondary.red,
                                        darkTheme.secondary.green,
                                        darkTheme.secondary.blue,
                                        alpha = 0.5f
                                    )
                                    Box(
                                        Modifier.width(300.dp).height(150.dp)
                                            .background(nC, shape = RoundedCornerShape(10)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "Add", style = TextStyle(
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = darkTheme.primary
                                            )
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
                item {
                    Box(
                        Modifier.fillMaxWidth().height(254.dp).background(darkTheme.background)
                    ) {
                        Column {
                            Text(
                                "Threes", style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    color = darkTheme.primary
                                ), modifier = Modifier.padding(top = 25.dp, start = 25.dp)
                            )

                            LazyRow(Modifier.padding(start = 25.dp, top = 25.dp)) {
                                if (whoami.myThree != null) {
                                    items(listMyThree) { myTop ->
                                        gameItemPhone(
                                            myTop.name,
                                            myTop.description,
                                            darkTheme.onPrimary
                                        )
                                    }
                                }
                                if (whoami.inThree != null) {
                                    items(listFriendThree) { intop ->
                                        gameItemPhone(
                                            intop.name,
                                            intop.description,
                                            Color(122, 122, 122)
                                        )
                                    }
                                }
                                item {
                                    val nC = Color(
                                        darkTheme.secondary.red,
                                        darkTheme.secondary.green,
                                        darkTheme.secondary.blue,
                                        alpha = 0.5f
                                    )
                                    Box(
                                        Modifier.width(300.dp).height(150.dp)
                                            .background(nC, shape = RoundedCornerShape(10)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "Add", style = TextStyle(
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = darkTheme.primary
                                            )
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
                item {
                    Box(
                        Modifier.fillMaxWidth().height(254.dp).background(darkTheme.background)
                    ) {
                        Column {
                            Text(
                                "Awards", style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    color = darkTheme.primary
                                ), modifier = Modifier.padding(top = 25.dp, start = 25.dp)
                            )

                            LazyRow(Modifier.padding(start = 25.dp, top = 25.dp)) {
                                if (whoami.myAward != null) {
                                    items(listMyAward) { myTop ->
                                        gameItemPhone(
                                            myTop.name,
                                            myTop.description,
                                            darkTheme.onPrimary
                                        )
                                    }
                                }
                                if (whoami.inAward != null) {
                                    items(listFriendAward) { intop ->
                                        gameItemPhone(
                                            intop.name,
                                            intop.description,
                                            Color(122, 122, 122)
                                        )
                                    }
                                }
                                item {
                                    val nC = Color(
                                        darkTheme.secondary.red,
                                        darkTheme.secondary.green,
                                        darkTheme.secondary.blue,
                                        alpha = 0.5f
                                    )
                                    Box(
                                        Modifier.width(300.dp).height(150.dp)
                                            .background(nC, shape = RoundedCornerShape(10)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "Add", style = TextStyle(
                                                fontSize = 24.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = darkTheme.primary
                                            )
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
                item {
                    Box(
                        Modifier.fillMaxWidth().height(100.dp).background(darkTheme.background),
                        contentAlignment = Alignment.Center
                    ) {
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

                    }
                }

            }
        }
    }
    //}

}


@Composable
fun ExpandedProfile(key:String){
    val scope = rememberCoroutineScope()
    if(updateProfile){
        val server = Reuests()
        scope.launch {
            server.whoami(
                onSuccess = {
                    res->
                    userTek=res
                },
                onFailure = {
                    m->
                    println(m)
                }
            )
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
            updateProfile=false
                //state =1
        }
    }

    var whoami = userTek
    /*if(key!=null){
        val newWhoami = server.whoamiRequest(key)
        if(newWhoami!=null) whoami=newWhoami
    }
    if(whoami.myTop!=null){
        for(x in whoami.myTop){
            if(key!=null) {
                var top = server.getTop(key, x)
                if(top!=null) listMyTop+=top
            }
        }
    }
    if(whoami.inTop!=null){
        for(x in whoami.inTop){
            if(key!=null) {
                var top = server.getTop(key, x)
                if(top!=null) listFriendTop+=top
            }
        }
    }
    if(whoami.myThree!=null){

    }
    if(whoami.inThree!=null){

    }
    if(whoami.myAward!=null){

    }
    if(whoami.inAward!=null){

    }*/
    LazyColumn() {
        item{
            shapkaDesctop(darkTheme.background, darkTheme.primary, darkTheme.secondary)
        }
        item {
            mainProfileDesctop(whoami.sex, whoami.username, whoami.myThree.size+whoami.inThree.size, whoami.myTop.size+whoami.inTop.size,whoami.myAward.size+whoami.inAward.size)
        }
        item {
            Box(Modifier.fillMaxWidth().height(474.dp).background(darkTheme.background)){
                Column {
                    Text("Tops", style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        color = darkTheme.primary
                    ), modifier = Modifier.padding(top=50.dp,start=100.dp))

                    LazyRow(Modifier.padding(start=100.dp, top=80.dp)) {
                        if(whoami.myTop!=null) {
                            items(listMyTop) { myTop ->
                                Box(Modifier.padding(end=25.dp).clickable {
                                    topActive=myTop.id
                                    topActiveBody = myTop
                                    state =11
                                }) {
                                    gameItemDesktop(
                                        myTop.name,
                                        myTop.description,
                                        darkTheme.onPrimary
                                    )
                                }
                            }
                        }
                        if(whoami.inTop!=null) {
                            items(listFriendTop) { intop ->
                                Box(Modifier.padding(end=25.dp).clickable {
                                    topActive=intop.id
                                    topActiveBody = intop
                                    state =11
                                }) {
                                    gameItemDesktop(
                                        intop.name,
                                        intop.description,
                                        Color(122, 122, 122)
                                    )
                                }
                            }
                        }
                        item {
                            val nC = Color(
                                darkTheme.secondary.red, darkTheme.secondary.green, darkTheme.secondary.blue,
                                alpha = 0.5f
                            )
                            Box(Modifier.padding(end=25.dp).width(400.dp).height(200.dp).background(nC, shape = RoundedCornerShape(10))
                                .clickable {
                                    sigType=1
                                    signState=true
                                },
                                contentAlignment = Alignment.Center){
                                Text("sign in", style = TextStyle(
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold,
                                    color =  darkTheme.primary
                                )
                                )
                            }
                        }
                        item {
                            val nC = Color(
                                darkTheme.secondary.red, darkTheme.secondary.green, darkTheme.secondary.blue,
                                alpha = 0.5f
                            )
                            Box(Modifier.padding(end=25.dp).width(400.dp).height(200.dp).background(nC, shape = RoundedCornerShape(10))
                                .clickable {
                                    sigType=1
                                    createWindow=true
                                },
                                contentAlignment = Alignment.Center){
                                Text("Add", style = TextStyle(
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold,
                                    color =  darkTheme.primary
                                )
                                )
                            }
                        }
                    }

                }
            }
        }
        item {
            Box(Modifier.fillMaxWidth().height(474.dp).background(darkTheme.background)){
                Column {
                    Text("Threes", style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        color = darkTheme.primary
                    ), modifier = Modifier.padding(top=50.dp,start=100.dp))

                    LazyRow(Modifier.padding(start=100.dp, top=80.dp)) {
                        if(whoami.myThree!=null) {
                            items(listMyThree) { myTop ->
                                gameItemDesktop(myTop.name,myTop.description, darkTheme.onPrimary)
                            }
                        }
                        if(whoami.inThree!=null) {
                            items(listFriendThree) { intop ->
                                gameItemDesktop(intop.name,intop.description, Color(122,122,122))
                            }
                        }
                        item {
                            val nC = Color(
                                darkTheme.secondary.red, darkTheme.secondary.green, darkTheme.secondary.blue,
                                alpha = 0.5f
                            )
                            Box(Modifier.width(400.dp).height(200.dp).background(nC, shape = RoundedCornerShape(10)),
                                contentAlignment = Alignment.Center){
                                Text("Add", style = TextStyle(
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold,
                                    color =  darkTheme.primary
                                )
                                )
                            }
                        }
                    }

                }
            }
        }
        item {
            Box(Modifier.fillMaxWidth().height(474.dp).background(darkTheme.background)){
                Column {
                    Text("Awards", style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        color = darkTheme.primary
                    ), modifier = Modifier.padding(top=50.dp,start=100.dp))

                    LazyRow(Modifier.padding(start=100.dp, top=80.dp)) {
                        if(whoami.myAward!=null) {
                            items(listMyAward) { myTop ->
                                gameItemDesktop(myTop.name,myTop.description, darkTheme.onPrimary)
                            }
                        }
                        if(whoami.inAward!=null) {
                            items(listFriendAward) { intop ->
                                gameItemDesktop(intop.name,intop.description, Color(122,122,122))
                            }
                        }
                        item {
                            val nC = Color(
                                darkTheme.secondary.red, darkTheme.secondary.green, darkTheme.secondary.blue,
                                alpha = 0.5f
                            )
                            Box(Modifier.width(400.dp).height(200.dp).background(nC, shape = RoundedCornerShape(10)),
                                contentAlignment = Alignment.Center){
                                Text("Add", style = TextStyle(
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Bold,
                                    color =  darkTheme.primary
                                )
                                )
                            }
                        }
                    }

                }
            }
        }
    }
    if(signState) {
        createWindow=false
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            bigSignCard(sigType, Color(32,32,32), darkTheme.primary)
        }
    }
    if(createWindow) {
        signState=false
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            createGameItembig(sigType, Color(32,32,32), darkTheme.primary)
        }
    }
}
