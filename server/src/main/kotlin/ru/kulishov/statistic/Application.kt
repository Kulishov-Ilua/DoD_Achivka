package ru.kulishov.statistic

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.auth.digest
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.request.receiveText
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import java.io.File

fun main() {

    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
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
                usrKeyList.find { x-> x.login== username}?.password
            }
        }
    }
    routing {
        post("/adduser") {
            try {
                val user = call.receive<AddUserRequest>()
                usrKeyList += (Userkey(usrKeyList.size, user.login, user.password.toByteArray()))
                call.respond(HttpStatusCode.OK)
            } catch (e: Exception) {
                println("Error receiving data: ${e.message}")
                call.respond(HttpStatusCode.BadRequest, "Invalid request data")
            }
        }
        authenticate("auth-digest") {
            get("/") {
                call.respondText("Ktor: ${Greeting().greet()}")
            }
            get("/whoami"){
                call.respond(HttpStatusCode.OK, )
            }
        }
    }
}