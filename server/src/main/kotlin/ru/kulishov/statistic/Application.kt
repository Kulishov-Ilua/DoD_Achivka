package ru.kulishov.statistic

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

fun main() {

    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(CORS){
        anyHost()
    }
    install(ContentNegotiation){
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(Authentication){
        digest("auth-digest") {
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
        }



    }
    routing {
        post("/adduser") {
            try {
                val user = call.receive<AddUserRequest>()
                if(usrKeyList.find { it.login==user.login }==null){
                    usrKeyList += (Userkey(usrKeyList.size, user.login, user.password.toByteArray()))
                    call.respond(HttpStatusCode.OK)
                }else call.respond(HttpStatusCode.Conflict)
            } catch (e: Exception) {
                println("Error receiving data: ${e.message}")
                call.respond(HttpStatusCode.BadRequest, "Invalid request data")
            }
        }
        authenticate("auth-digest") {
            get("/") {
                var principal = call.principal<CustomPrincipal>()
                println(principal)
                if (principal?.userId != null) {
                    // principal.name содержит имя пользователя
                    call.respondText("Ktor: Привет, ${principal.userId}!")
                } else {
                    call.respondText("Ktor: Пользователь не найден")
                }
            //call.respondText("Ktor: ${/*Greeting().greet()*/ text}")

            }
            get("/whoami"){
                val principal = call.principal<CustomPrincipal>()
                if(principal!=null) {
                    val user = userList.find { it.userId==principal.userId }
                    val name = usernameList.find { it.userId==principal.userId }!!.username
                    var sex = usernameList.find { it.userId==principal.userId }!!.sex
                    if(sex==null) sex = -1
                    if(user!=null && name!=null){
                        call.respond(HttpStatusCode.OK, WhoamiRequest(name, sex,user.plan,user.myThree, user.inThree, user.myTop
                        , user.inTop, user.myAward, user.inAward))
                    }else call.respond(HttpStatusCode.NotFound)

                }else call.respond(HttpStatusCode.Unauthorized)
                call.respond(HttpStatusCode.OK, )
            }
        }
    }
}