package ru.kulishov.statistic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BasicAuthConfig
import io.ktor.client.plugins.auth.providers.BasicAuthCredentials
import io.ktor.client.plugins.auth.providers.DigestAuthCredentials
import io.ktor.client.plugins.auth.providers.basic
import io.ktor.client.plugins.auth.providers.digest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

val basicURl="http://localhost:8003"

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Award
//Variables:
//              awardId:Int - id award
//              imagePassiveId:Image - passive award image
//              imageActiveId:Image - active award image
//              userActive:List<BigInteger> - users
//              status:Bool - status
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Serializable
data class Award(val awardId:Int, val name:String,
                 val description: String="",
                 val userActive:List<Int>,
                 var status:Boolean=false)

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//UserAwardList
//Variables:
//              id:Int - award list id
//              name:String - award list name
//              description:String - description
//              admin:BigInteger - admin
//              awardList:List<Award> - award list
//              users:List<BigInteger> - users
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Serializable
data class UserAwardList(val id:Int,val token:String, val name:String, val description:String,val admin:Int,val awardList:List<Award>,val users:List<Int>)

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Three
//Variables:
//              id:Int - three id
//              name:String - extr name
//              description:String - description
//              threeId:BigInteger - three id
//              admin:BigInteger - admin
//              users:List<BigInteger> - users
//              extr:List<Extr> - extr
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

data class Three(val id:Int,val name:String, val description:String, val threeId:Int,val admin:Int,val users:List<Int>,val extr:List<Extr>)

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Extr
//Variables:
//              name:String - extr name
//              description:String - description
//              imagePassive:Image - passive image
//              imageActive:Image - active image
//              status:Int - status
//              old:List<Extr> - old
//              young:List<Extr> - young
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

data class Extr(val name:String, val description:String, val imagePassive: ContentType.Image, val imageActive: ContentType.Image, val status:Int, val old:List<Extr>, val young:List<Extr>)

@Serializable
data class CreateRequest(
    val name:String,
    val description:String
)
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//RequestClass
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
class Reuests{
    private val client = HttpClient(){
        install(Auth) {
            basic {
                credentials {
                    BasicAuthCredentials(username = loginInp, password = passwordInp)
                }
                realm = "Access to the '/secure' path" // Realm должен совпадать с серверным
            }
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })

        }


        defaultRequest {
            header("Accept", "*/*")
            header("User-Agent", "Mozilla/5.0")
        }

    }

    suspend fun whoami(onSuccess: (WhoamiRequest)->Unit, onFailure: (String)->Unit) {
        val response = client.get("$basicURl/whoami"){
            contentType(ContentType.Application.Json)
        }

        if(response.status== HttpStatusCode.OK){
            val resp:WhoamiRequest = response.body()
            onSuccess(resp)
        }else{
            onFailure("Error: ${response.status}")
        }

    }
    suspend fun getId(onSuccess: (Int)->Unit, onFailure: (String)->Unit) {
        val response = client.get("$basicURl/id"){
            contentType(ContentType.Application.Json)
        }

        if(response.status== HttpStatusCode.OK){
            val resp:Int = response.body()
            onSuccess(resp)
        }else{
            onFailure("Error: ${response.status}")
        }

    }
    suspend fun getTop(topId:Int,onSuccess: (Top)->Unit, onFailure: (String)->Unit) {
        val response = client.get("$basicURl/getTop/$topId"){
            contentType(ContentType.Application.Json)
        }
        if(response.status== HttpStatusCode.OK){
            val resp:Top = response.body()
            onSuccess(resp)
        }else{
            onFailure("Error: ${response.status}")
        }

    }
    //=====================================================================================
    //getUsernameList
    //Input values:
    //              userIdList:List<Int> - users id list
    //Output values:
    //              usernames:List<Username> - usernames list
    //=====================================================================================
    suspend fun getUsernameList(userIdList:List<Int>,onSuccess: (List<Username>)->Unit, onFailure: (String)->Unit){
        val response = client.get("$basicURl/getUsernameList"){
            contentType(ContentType.Application.Json)
            setBody(userIdList)
        }
        if(response.status== HttpStatusCode.OK){
            val resp:List<Username> = response.body()
            onSuccess(resp)
        }else{
            onFailure("Error: ${response.status}")
        }
    }
    //=====================================================================================
    //checkAdminTop
    //Input values:
    //              topId:Int - id top
    //Output values:
    //              ret:Boolean - result
    //=====================================================================================
    suspend fun checkAdminTop(topId:Int):Boolean{
        val response = client.get("$basicURl/checkAdminTop/$topId"){
        }
        if(response.status== HttpStatusCode.OK){
            return true
        }else{
            return false
        }
    }
    //=====================================================================================
    //checkAdminTop
    //Input values:
    //              topId:Int - id top
    //Output values:
    //              ret:Boolean - result
    //=====================================================================================
    suspend fun checkAdminAward(topId:Int):Boolean{
        val response = client.get("$basicURl/checkAdminAward/$topId"){
        }
        if(response.status== HttpStatusCode.OK){
            return true
        }else{
            return false
        }
    }

    //=====================================================================================
    //kickUpUser
    //Input values:
    //              topId:Int - top id
    //              userId:Int - user id
    //=====================================================================================
    suspend fun kickUpUser(topId:Int,userId:Int){
        val response = client.post("$basicURl/kickUpUser/$topId/$userId"){
        }
        if(response.status== HttpStatusCode.OK){
        }else{
            println("procedure kickUpUser unsuccessful")
        }
    }
    //=====================================================================================
    //updateUserTopResult
    //Input values:
    //              topId:Int - id top
    //              userId:Int - user id
    //              newResult:Int - new result
    //=====================================================================================
    suspend fun updateUserTopResult(topId:Int,userId:Int,newResult:Int){
        val response = client.post("$basicURl/updateUserTopResult/$topId/$userId/$newResult"){
        }
        if(response.status== HttpStatusCode.OK){
        }else{
            println("procedure updateUserTopResult unsuccessful")
        }
    }
    //=====================================================================================
    //getTopToken
    //Input values:
    //              topId:Int - id top
    //Output values:
    //              token:String - token
    //=====================================================================================
    suspend fun getTopToken(topId:Int):String{
        var ret = ""
        val response = client.get("$basicURl/getTopToken/$topId"){

        }
        if(response.status== HttpStatusCode.OK){
            ret = response.body()

        }
        return ret
    }
    //=====================================================================================
    //signTop
    //Input values:
    //              token:String - token
    //Output values:
    //              status:Boolean - status
    //=====================================================================================
    suspend fun signTop(token:String):Boolean{
        val response = client.post("$basicURl/signTop/$token"){
        }
        if(response.status== HttpStatusCode.OK){
            return true
        }else{
            println("procedure signTop unsuccessful")
            return false
        }
    }
    //=====================================================================================
    //createTop
    //Input values:
    //              name:String - token
    //Output values:
    //              status:Boolean - status
    //=====================================================================================
    suspend fun createTop(name:String,description: String):Boolean{
        val response = client.post("$basicURl/createTop"){
            contentType(ContentType.Application.Json)
            setBody(CreateRequest(name,description))
        }
        if(response.status== HttpStatusCode.OK){
            return true
        }else{
            println("procedure createTop unsuccessful: ${response.status}")
            return false
        }
    }
    //--------------------------------------------------------------------------------------
    //Award
    //--------------------------------------------------------------------------------------
    suspend fun getAwardList(topId:Int,onSuccess: (UserAwardList)->Unit, onFailure: (String)->Unit) {
        val response = client.get("$basicURl/getAwardList/$topId"){
            contentType(ContentType.Application.Json)
        }
        if(response.status== HttpStatusCode.OK){
            val resp:UserAwardList = response.body()
            onSuccess(resp)
        }else{
            onFailure("Error: ${response.status}")
        }

    }
    suspend fun awardStat(awardListId:Int,awardId:Int,userId:Int,onSuccess: ()->Unit, onFailure: (String)->Unit) {
        val response = client.post("$basicURl/awardStatus/$awardListId/$awardId/$userId"){
            //contentType(ContentType.Application.Json)
        }
        if(response.status== HttpStatusCode.OK){
            onSuccess()
        }else{
            onFailure("Error: ${response.toString()}")
        }

    }
    //=====================================================================================
    //createTop
    //Input values:
    //              name:String - token
    //Output values:
    //              status:Boolean - status
    //=====================================================================================
    suspend fun createAwardList(name:String,description: String):Boolean{
        val response = client.post("$basicURl/createAwardList"){
            contentType(ContentType.Application.Json)
            setBody(CreateRequest(name,description))
        }
        if(response.status== HttpStatusCode.OK){
            return true
        }else{
            println("procedure createTop unsuccessful: ${response.status}")
            return false
        }
    }
    //=====================================================================================
    //getTopToken
    //Input values:
    //              topId:Int - id top
    //Output values:
    //              token:String - token
    //=====================================================================================
    suspend fun getAwardToken(topId:Int):String{
        var ret = ""
        val response = client.get("$basicURl/getAwardToken/$topId"){

        }
        if(response.status== HttpStatusCode.OK){
            ret = response.body()

        }
        return ret
    }
    //=====================================================================================
    //signTop
    //Input values:
    //              token:String - token
    //Output values:
    //              status:Boolean - status
    //=====================================================================================
    suspend fun signAward(token:String):Boolean{
        val response = client.post("$basicURl/signAward/$token"){
        }
        if(response.status== HttpStatusCode.OK){
            return true
        }else{
            println("procedure signTop unsuccessful")
            return false
        }
    }
    //=====================================================================================
    //createTop
    //Input values:
    //              name:String - token
    //Output values:
    //              status:Boolean - status
    //=====================================================================================
    suspend fun createAward(awardListId:Int,name:String,description: String):Boolean{
        val response = client.post("$basicURl/createAwardItem/$awardListId"){
            contentType(ContentType.Application.Json)
            setBody(CreateRequest(name,description))
        }
        if(response.status== HttpStatusCode.OK){
            return true
        }else{
            println("procedure createAward unsuccessful: ${response.status}")
            return false
        }
    }
}

//##################################################################################################
//##################################################################################################
//#####################                    emulate server                    #######################
//##################################################################################################
//####  Author:Kulishov I.V.                         ###############################################
//####  Version:0.0.1                                ###############################################
//####  Date:11.11.2024                              ###############################################
//##################################################################################################
//##################################################################################################
data class Userkey(val userId: Int, val login:String, val password:String){}
var usrKeyList = mutableListOf(
    Userkey(0,"test", "1"),
    Userkey(1,"test1", "1"),
    Userkey(2,"test2", "1"),
    Userkey(3,"test3", "1"),
    Userkey(4,"test4", "1"),
    Userkey(5,"test5", "1"),
    Userkey(6,"test6", "1"),
    Userkey(7,"test7", "1"),
    Userkey(8,"test8", "1"),
    Userkey(9,"test9", "1"),

    )
@Serializable
data class Username(val userId:Int, val sex:Int,val username:String)
var userNameList = mutableListOf(
    Username(0,1,"Ivanov I.I."),
    Username(1,1,"Grinev A.A."),
    Username(2,2,"Larina A.A."),
    Username(3,1,"Bazarov A.A."),
    Username(4,1,"Pechorin A.A."),
    Username(5,2,"Dobroselova A.A."),
    Username(6,1,"Chatski A.A."),
    Username(7,1,"Myshkin A.A."),
    Username(8,1,"Raskolnikov A.A."),
    Username(9,2,"Karenina A.A."),
)


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Top
//Variables:
//              id:Int - three id
//              name:String - extr name
//              description:String - description
//              token:String - top token
//              admin:Int - user id
//              users:List<UserTop> - users
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Serializable
data class Top(val id:Int, val name:String, val description:String, val token:String, val admin:UserTop,
               var users:List<UserTop>)

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//UserTop
//Variables:
//              userId:Int - user
//              value:Int - user value
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Serializable
data class UserTop(val user:Int, var value:Int)
var threeList = emptyList<Three>()
var awardList = emptyList<UserAwardList>()
var topList = listOf(
    Top(0,"Test top", "просто какое-то описание топа. Что-то же нужно написать","sjklfjsklj943ls", UserTop(0,0), listOf(
        UserTop(1, 999),
        UserTop(2, 800),
        UserTop(3, 650),
        UserTop(4, 400),
        UserTop(5, 200),
        UserTop(6, 50),
        UserTop(7, 20),
        UserTop(8, 10),
        UserTop(9, 5)
    ))

)

data class User(val userId:Int,val token:String,val plan:Int,val myThree:List<Int>,val inThree:List<Int>,val myTop:List<Int>,val inTop:List<Int>,val myAward:List<Int>,val inAward:List<Int>)
var userList = mutableListOf(
    User(0,"qqq",3,  emptyList(), emptyList(),listOf(0), emptyList(),
    emptyList(), emptyList()),
    User(1,"qq1",1, emptyList(), emptyList(), emptyList(),listOf(0), emptyList(),
        emptyList()),
    User(2,"qq2",1, emptyList(), emptyList(), emptyList(),listOf(0), emptyList(),
        emptyList()),
    User(3,"qq3",1, emptyList(), emptyList(), emptyList(),listOf(0), emptyList(),
        emptyList()),
    User(4,"qq4",1, emptyList(), emptyList(), emptyList(),listOf(0), emptyList(),
        emptyList()),
    User(5,"qq5",1, emptyList(), emptyList(), emptyList(),listOf(0), emptyList(),
        emptyList()),
    User(6,"qq6",1, emptyList(), emptyList(), emptyList(),listOf(0), emptyList(),
        emptyList()),
    User(7,"qq7",1, emptyList(), emptyList(), emptyList(),listOf(0), emptyList(),
        emptyList()),
    User(8,"qq8",1, emptyList(), emptyList(), emptyList(),listOf(0), emptyList(),
        emptyList()),
    User(9,"qq9",1, emptyList(), emptyList(), emptyList(),listOf(0), emptyList(),
        emptyList()),
    )

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//WhoamiRequest
//Variables:
//              username:String - username
//              sex:Int - user sex
//              plan:Int - user plan
//              myThree:List<Int> - user admin three
//              inThree:List<Int> - user in three
//              myTop:List<Int> - user admin top
//              inTop:List<Int> - user in top
//              myAward:List<Int> - user admin award
//              inAward:List<Int> - user in award
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Serializable
data class WhoamiRequest(var username:String, var sex:Int, var plan:Int, var myThree:List<Int>, var inThree:List<Int>, var myTop:List<Int>, var inTop:List<Int>, var myAward:List<Int>, var inAward:List<Int>){
}
class serverE:serverEmulator{}
val server=serverE()
public interface serverEmulator{
    //=====================================================================================
    //getId
    //Input values:
    //              key:String - key
    //Output values:
    //              id:Int? - id
    //=====================================================================================
    private fun getId(key:String):Int? {
        var user = userList.find { cr -> cr.token == key }
        if (user != null) {
            return user.userId
        }else return null
    }
    //=====================================================================================
    //whoamiRequest
    //Input values:
    //              key:String - key
    //Output values:
    //              user:WhoamiRequest - user
    //=====================================================================================
    fun whoamiRequest(key:String):WhoamiRequest?{
        var ret = WhoamiRequest("",1,-1, emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList())
        var user = userList.find { cr-> cr.token==key }
        if(user!=null){
            var userName = userNameList.find { cr-> cr.userId==user.userId }
            if (userName != null) ret.username=userName.username
            if (userName != null) {
                ret.sex=userName.sex
            }
            ret.plan=user.plan
            var threeListF = emptyList<Int>()
            var threeListM = emptyList<Int>()

            var topListF = emptyList<Int>()
            var topListM = emptyList<Int>()

            var awardListF = emptyList<Int>()
            var awardListM = emptyList<Int>()
            for (x in threeList){
                if(x.admin==user.userId) threeListM+=x.threeId
                if(x.users.contains(user.userId)) threeListF+=x.threeId
            }
            for(y in topList){
                if(y.admin.user==user.userId) topListM+=y.id
                for(y1 in y.users){
                    if(y1.user==user.userId) topListF+=y.id
                }
            }
            for(z in awardList){
                if(z.admin==user.userId) awardListM+=z.id
                if(z.users.contains(user.userId)) awardListM+=z.id
            }
            ret.inTop=topListF; ret.myTop=topListM; ret.myThree=threeListM; ret.inThree=threeListF; ret.inAward=awardListF;ret.myAward=awardListM
            return ret;
        }else {
            return null
        }
    }

    //=====================================================================================
    //authorization
    //Input values:
    //              login:String - login
    //              password:String - password
    //Output values:
    //              token:String? - token
    //=====================================================================================
    fun authorization(login:String, password:String):String?{
        val find = usrKeyList.find{cr-> cr.login==login&&cr.password==password}
        if(find!=null){
            var token = userList.find { cr-> cr.userId==find.userId }
            if(token!=null){
                return token.token
            }else return null
        }else return null
    }
    //=====================================================================================
    //getTop
    //Input values:
    //              key:String - key
    //              idTop:Int - top id
    //Output values:
    //              top:Top - top
    //=====================================================================================
    fun getTop(key:String,idTop:Int):Top?{
        var userId = getId(key)
        if(userId!=null){
            val top = topList.find { cr-> cr.id==idTop }
            if(top!=null){
                if(top.admin.user==userId){
                    return top
                }else{
                    for(x in top.users){
                        if(x.user==userId) return top
                    }
                    return null
                }
            }else return null
        }else return null
    }
    //=====================================================================================
    //getAward
    //Input values:
    //              key:String - user key
    //              idAward:Int - award id
    //Output values:
    //              award:UserAwardList? - award list
    //=====================================================================================
    fun getAward(key:String, idAward:Int):UserAwardList?{
        var userId = getId(key)
        if(userId!=null){
            val award = awardList.find { cr-> cr.id==idAward }
            if(award!=null){
                if(award.admin==userId){
                    return award
                }else{
                    if(award.users.contains(userId)) return award
                    else return null
                }
            }else return null
        }else return null
    }
    //=====================================================================================
    //getThree
    //Input values:
    //              key:String - user key
    //              idThree:Int - three id
    //Output values:
    //              Three:Three? - three
    //=====================================================================================
    fun getThree(key:String, idThree:Int):Three?{
        var userId = getId(key)
        if(userId!=null){
            val three = threeList.find { cr-> cr.id==idThree }
            if(three!=null){
                if(three.admin==userId){
                    return three
                }else{
                    if(three.users.contains(userId)) return three
                    else return null
                }
            }else return null
        }else return null
    }
    //=====================================================================================
    //getUsernameList
    //Input values:
    //              userIdList:List<Int> - users id list
    //Output values:
    //              usernames:List<Username> - usernames list
    //=====================================================================================
    fun getUsernameList(userIdList:List<Int>):List<Username>{
        var usernames = emptyList<Username>()
        for(x in userIdList){
            val user = userNameList.find { cr-> cr.userId==x }
            if(user!=null) usernames+=user
        }
        return usernames
    }
    //=====================================================================================
    //checkAdminTop
    //Input values:
    //              topId:Int - id top
    //              key:String - user key
    //Output values:
    //              ret:Boolean - result
    //=====================================================================================
    fun checkAdminTop(topId:Int,key:String):Boolean{
        val top = topList.find { cr-> cr.id==topId }
        if(top!=null){
            if(top.admin.user==getId(key)) return true
            else return false
        }else return false
    }
    //=====================================================================================
    //getTopToken
    //Input values:
    //              key:String - user key
    //              topId:Int - top id
    //Output values:
    //              token:String? - token
    //=====================================================================================
    fun getTopToken(key:String,topId:Int):String?{
        val top = topList.find { cr-> cr.id==topId }
        if(top!=null){
            if(top.admin.user==getId(key)) return top.token
            else return null
        }else return null
    }
    //=====================================================================================
    //kickUpUser
    //Input values:
    //              key:String - user key
    //              topId:Int - top id
    //              userId:Int - user id
    //=====================================================================================
    fun kickUpUser(key:String,topId:Int,userId:Int){
        val top = topList.find { cr-> cr.id==topId }
        if(top!=null){
            if(top.admin.user==getId(key)) {
                val userfnd = top.users.find { cr-> cr.user==userId }
                if(userfnd!=null) top.users-=userfnd
            }
        }
    }
    //=====================================================================================
    //updateUserTopResult
    //Input values:
    //              key:String - user key
    //              topId:Int - id top
    //              userId:Int - user id
    //              newResult:Int - new result
    //=====================================================================================
    fun updateUserTopResult(key:String,topId:Int,userId:Int,newResult:Int){
        val top = topList.find { cr-> cr.id==topId }
        if(top!=null){
            if(top.admin.user==getId(key)) {
                val userfnd = top.users.find { cr-> cr.user==userId }
                if(userfnd!=null) userfnd.value=newResult
            }
        }
    }

}