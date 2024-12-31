package ru.kulishov.statistic

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.DigestAuthenticationProvider
import io.ktor.server.auth.DigestCredential
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.auth.basic
import io.ktor.server.auth.digest
import io.ktor.server.auth.principal
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.request.receive
import io.ktor.server.request.receiveText
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.Digest
import kotlinx.serialization.json.Json

import java.io.File
import java.security.DigestInputStream
import java.time.LocalDateTime

fun main() {

    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(CORS){
        allowMethod(HttpMethod.Get)    // Разрешить GET-запросы
        allowMethod(HttpMethod.Post)   // Разрешить POST-запросы
        allowHeader(HttpHeaders.Authorization) // Разрешить заголовок Authorization
        allowHeader(HttpHeaders.ContentType)   // Разрешить заголовок Content-Type
        allowCredentials = true // Разрешить отправку cookies и других данных с авторизацией
        allowHost("localhost:8080") // Указать доверенный источникanyHost()
    }
    install(ContentNegotiation){
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(Authentication){
        /*digest("auth-digest") {
            realm = myRealm
            digestProvider { username, realm ->
                val user = usrKeyList.find { x-> x.login== username}?.password
                if (user == null) {
                    println("Пользователь $username не найден.")
                    null
                }else {user}

            }
            validate { credentials ->
                println("Received credentials: ${credentials.userName}")
                if (credentials.userName.isNotEmpty()) {
                    CustomPrincipal(usrKeyList.find { it.login==credentials.userName}!!.userId, credentials.realm)
                } else {
                    null
                }
            }
        }*/
        basic("auth-basic") {
            realm = "Access to the '/secure' path"
            validate { credentials ->
                // Проверяем логин и пароль
                val user = usrKeyList.find { it -> it.login==credentials.name&&it.password==credentials.password  }?.userId
                if(user!=null){
                    UserIdPrincipal(user.toString())
                } else {
                    null // Ошибка
                }
            }
        }



    }
    routing {
        post("/adduser") {
            try {
                val user = call.receive<AddUserRequest>()
                if(usrKeyList.find { it.login==user.login }==null){
                    usrKeyList += (Userkey(usrKeyList.size, user.login, user.password))
                    call.respond(HttpStatusCode.OK)
                }else call.respond(HttpStatusCode.Conflict)
            } catch (e: Exception) {
                println("Error receiving data: ${e.message}")
                call.respond(HttpStatusCode.BadRequest, "Invalid request data")
            }
        }
        authenticate("auth-basic") {
            get("/") {
                val principal = call.principal<UserIdPrincipal>()
                println(principal)
                if (principal!= null) {
                    // principal.name содержит имя пользователя
                    call.respondText("Ktor: Привет, ${principal.name}!")
                } else {
                    call.respondText("Ktor: Пользователь не найден")
                }
            //call.respondText("Ktor: ${/*Greeting().greet()*/ text}")

            }
            get("/whoami"){
                val principal = call.principal<UserIdPrincipal>()
                if(principal!=null) {
                    val user = userList.find { it.userId==principal.name.toInt() }
                    val name = usernameList.find { it.userId==principal.name.toInt() }!!.username
                    var sex = usernameList.find { it.userId==principal.name.toInt() }!!.sex
                    if(sex==null) sex = -1
                    if(user!=null && name!=null){
                        call.respond(HttpStatusCode.OK, WhoamiRequest(name, sex,user.plan,user.myThree, user.inThree, user.myTop
                        , user.inTop, user.myAward, user.inAward))
                    }else call.respond(HttpStatusCode.NotFound)

                }else call.respond(HttpStatusCode.Unauthorized)
                //call.respond(HttpStatusCode.OK, )
            }
            get("/getTop/{topId}"){
                val principal = call.principal<UserIdPrincipal>()
                val topId = call.parameters["topId"]
                if(topId!=null) {
                    val user = userList.find { it.userId == principal!!.name.toInt() }
                    if (user != null) {
                        val top = topList.find { cr -> cr.id == topId.toInt() }
                        if (top != null) {
                            if (top.admin.user == user.userId) {
                                call.respond(HttpStatusCode.OK, top)
                            } else {
                                for (x in top.users) {
                                    if (x.user == user.userId) call.respond(HttpStatusCode.OK, top)
                                }
                                call.respond(HttpStatusCode.Unauthorized)
                            }
                        } else call.respond(HttpStatusCode.NotFound)
                    } else call.respond(HttpStatusCode.Unauthorized)
                }else call.respond(HttpStatusCode.BadRequest)
            }
            get("/getUsernameList"){
                val userIdList= call.receive<List<Int>>()
                var usernames = emptyList<Username>()
                for(x in userIdList){
                    val user = usernameList.find { cr-> cr.userId==x }
                    if(user!=null) usernames+=user
                }
                call.respond(HttpStatusCode.OK,usernames)
            }
            get("/checkAdminTop/{topId}"){
                val principal = call.principal<UserIdPrincipal>()
                if(principal!=null) {
                    val topIdStr = call.parameters["topId"]
                    if (topIdStr != null) {
                        val top = topList.find { cr-> cr.id==topIdStr.toInt() }
                        if(top!=null) {
                            if (top.admin.user == principal.name.toInt()) call.respond(
                                HttpStatusCode.OK)
                            else call.respond(HttpStatusCode.Conflict)
                        }else call.respond(HttpStatusCode.NotFound)
                    } else call.respond(HttpStatusCode.BadRequest)
                }else call.respond(HttpStatusCode.Unauthorized)
            }
            post("kickUpUser/{topId}/{userId}"){
                val principal=call.principal<UserIdPrincipal>()
                val topId = call.parameters["topId"]
                val userId = call.parameters["userId"]
                if(principal!=null){
                    if(topId!=null&&userId!=null) {
                        val top = topList.find { cr -> cr.id == topId.toInt() }
                        if (top != null) {
                            if (top.admin.user == principal.name.toInt()) {
                                val userfnd = top.users.find { cr -> cr.user == userId.toInt() }
                                if (userfnd != null){
                                    top.users -= userfnd
                                    call.respond(HttpStatusCode.OK)
                                }else call.respond(HttpStatusCode.NotFound)
                            }else call.respond(HttpStatusCode.Locked)
                        }else call.respond(HttpStatusCode.NotFound)
                    }else call.respond(HttpStatusCode.BadRequest)

                }else call.respond(HttpStatusCode.Unauthorized)
            }
            post("updateUserTopResult/{topId}/{userId}/{newResult}"){
                val topId = call.parameters["topId"]
                val userId = call.parameters["userId"]
                val newResult = call.parameters["newResult"]
                val principal=call.principal<UserIdPrincipal>()

                if(principal!=null){
                    if(topId!=null&&userId!=null&&newResult!=null){
                        val top = topList.find { cr-> cr.id==topId.toInt() }
                        if(top!=null){
                            if(top.admin.user==principal.name.toInt()) {
                                val userfnd = top.users.find { cr-> cr.user==userId.toInt() }
                                if(userfnd!=null){
                                    userfnd.value=newResult.toInt()
                                    call.respond(HttpStatusCode.OK)
                                }else call.respond(HttpStatusCode.NotFound)
                            }else call.respond(HttpStatusCode.Locked)
                        }else call.respond(HttpStatusCode.NotFound)
                    }else call.respond(HttpStatusCode.BadRequest)
                }else call.respond(HttpStatusCode.Unauthorized)
            }
            get("getTopToken/{topId}"){
                val topId = call.parameters["topId"]
                val principal=call.principal<UserIdPrincipal>()
                if(principal!=null){
                    if(topId!=null){
                        val top = topList.find { cr-> cr.id==topId.toInt() }
                        if(top!=null){
                            if(top.admin.user==principal.name.toInt()) {
                                call.respond(top.token)
                            }else call.respond(HttpStatusCode.Locked)
                        }else call.respond(HttpStatusCode.NotFound)
                    }else call.respond(HttpStatusCode.BadRequest)
                }else call.respond(HttpStatusCode.Unauthorized)
            }
            post("signTop/{token}"){
                val token = call.parameters["token"]
                val principal=call.principal<UserIdPrincipal>()
                if(principal!=null){
                    if(token!=null){
                        val top = topList.find { cr-> cr.token==token }
                        if(top!=null){
                            if(top.users.find { cr-> cr.user== principal.name.toInt()}==null){
                                top.users+=UserTop(principal.name.toInt(),0)
                                var token = System.currentTimeMillis().toString()
                                token+= topList.size.toString()
                                token+=System.currentTimeMillis().toString()
                                top.token=token
                                val userN = userList.find { cr-> cr.userId== principal.name.toInt()}
                                if(userN!=null){
                                    userN.inTop += top.id
                                }
                            }

                            call.respond(HttpStatusCode.OK)
                        }else call.respond(HttpStatusCode.NotFound)
                    }else call.respond(HttpStatusCode.BadRequest)
                }else call.respond(HttpStatusCode.Unauthorized)
            }
            post("createTop"){
                val name = call.receive<CreateRequest>()
                val principal=call.principal<UserIdPrincipal>()
                if(principal!=null){
                    var token = System.currentTimeMillis().toString()
                    token+= topList.size.toString()
                    token+=principal.name
                    token+=System.currentTimeMillis().toString()
                    topList+=Top(topList.size,name.name,name.description!!,token,
                        UserTop(principal.name.toInt(),0),
                        listOf( UserTop(principal.name.toInt(),0))
                    )
                    val userN = userList.find { cr-> cr.userId== principal.name.toInt()}
                    if(userN!=null){
                        userN.myTop += topList.size-1
                    }
                    //println(topList)
                    call.respond(HttpStatusCode.OK, topList)
                }else call.respond(HttpStatusCode.Unauthorized)
            }
            //--------------------------------------------------------------------------------------
            //Award
            //--------------------------------------------------------------------------------------
            post("createAwardList"){
                val name = call.receive<CreateRequest>()
                val principal=call.principal<UserIdPrincipal>()
                if(principal!=null){
                    var token = System.currentTimeMillis().toString()
                    token+= topList.size.toString()
                    token+=principal.name
                    token+=System.currentTimeMillis().toString()
                    awardList+=UserAwardList(awardList.size,token,name.name,name.description,principal.name.toInt(),
                        emptyList(),
                        emptyList() )
                    val userN = userList.find { cr-> cr.userId== principal.name.toInt()}
                    if(userN!=null){
                        userN.myAward += awardList.size-1
                    }
                    //println(topList)
                    call.respond(HttpStatusCode.OK, topList)
                }else call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }
}