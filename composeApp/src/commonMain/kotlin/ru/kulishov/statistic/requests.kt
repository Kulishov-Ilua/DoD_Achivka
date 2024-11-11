package ru.kulishov.statistic

import androidx.compose.runtime.mutableStateOf
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.DigestAuthCredentials
import io.ktor.client.plugins.auth.providers.digest
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable




//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Award
//Variables:
//              awardId:Int - id award
//              imagePassiveId:Image - passive award image
//              imageActiveId:Image - active award image
//              userActive:List<BigInteger> - users
//              status:Bool - status
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

data class Award(val awardId:Int, val imagePassiveId: ContentType.Image, val imageActiveId: ContentType.Image, val userActive:List<Int>, var status:Boolean)

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

data class UserAwardList(val id:Int,val name:String, val description:String,val admin:Int,val awardList:List<Award>,val users:List<Int>)

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


//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//RequestClass
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
class Reuests{
    private val client = HttpClient(){
        install(Auth){
            digest{
                credentials {
                    DigestAuthCredentials(
                        username = "test",
                        password ="5"
                    )
                }
                //realm = "fsfkos" // Опционально: можно указать требуемую область,
            }
        }


        defaultRequest {
            header("Accept", "*/*")
            header("User-Agent", "Mozilla/5.0")
        }

    }

    suspend fun whoami(onSuccess: (WhoamiRequest)->Unit, onFailure: (String)->Unit) {
        val response = client.get("https://ed39-87-117-56-165.ngrok-free.app/whoami")
        if(response.status== HttpStatusCode.OK){
            val resp:WhoamiRequest = response.body()
            onSuccess(resp)
        }else{
            onFailure("Error: ${response.status}")
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
//              admin:Int - user id
//              users:List<UserTop> - users
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Serializable
data class Top(val id:Int,val name:String, val description:String,val admin:UserTop,val users:List<UserTop>)

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//UserTop
//Variables:
//              userId:Int - user
//              value:Int - user value
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Serializable
data class UserTop(val user:Int,val value:Int)
var threeList = emptyList<Three>()
var awardList = emptyList<UserAwardList>()
var topList = listOf(
    Top(0,"Test top", "просто какое-то описание топа. Что-то же нужно написать", UserTop(0,0), listOf(
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
class server:serverEmulator{}
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

}