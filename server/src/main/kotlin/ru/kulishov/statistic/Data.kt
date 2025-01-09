package ru.kulishov.statistic

import io.ktor.http.ContentType
import kotlinx.serialization.Serializable
import java.math.BigInteger
import java.security.MessageDigest
import java.security.Principal
import kotlin.text.Charsets.UTF_8

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//User
//Variables:
//              userId:Int - user id
//              token:String - token for API
//              plan:Int - plan
//              myThree:List<Int> - user admin three
//              inThree:List<Int> - user in three
//              myTop:List<Int> - user admin top
//              inTop:List<Int> - user in top
//              myAward:List<Int> - user admin award
//              inAward:List<Int> - user in award
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Serializable
data class User(val userId:Int, val token:String, val plan:Int, val myThree:List<Int>, val inThree:List<Int>,
                var myTop:List<Int>, var inTop:List<Int>, var myAward:List<Int>, var inAward:List<Int>)

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Username
//Variables:
//              userId:Int - user id
//              sex:Int - user sex
//              username:String - username
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Serializable
data class Username(val userId:Int, val sex:Int,val username:String)

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//AddUserRequest
//Variables:
//              login:String - login
//              password:ByteArray - password
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Serializable
data class AddUserRequest( val login:String, val password:String)
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Userkey
//Variables:
//              userId:Int - user id
//              login:String - login
//              password:ByteArray - password
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Serializable
data class Userkey(val userId: Int, val login:String, val password:String){}
val myRealm = "fsfkos"
fun getMd5Digest(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(UTF_8))
    var usrKeyList = mutableListOf(Userkey(0,"test", "1"),
        Userkey(1,"test1", "1"),
        Userkey(2,"test2", "1"),
        Userkey(3,"test3", "1"),
        Userkey(4,"test4", "1"),
        Userkey(5,"test5", "1"),
        Userkey(6,"test6", "1"),
        Userkey(7,"test7", "1"),
        Userkey(8,"test8", "1"),
        Userkey(9,"test9", "1"),)



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
data class Top(val id:Int, val name:String, val description:String, var token:String, val admin:UserTop,
               var users:List<UserTop>)

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//UserTop
//Variables:
//              userId:Int - user
//              value:Int - user value
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
@Serializable
data class UserTop(val user:Int, var value:Int)
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
                 var userActive:List<Int>?,
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
data class UserAwardList(val id:Int, var token:String, val name:String, val description:String, val admin:Int, var awardList:List<Award>?,
                         var users:List<Int>)

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

var usernameList = listOf(Username(0,1,"Ivanov I.I."),
    Username(1,1,"Grinev A.A."),
    Username(2,2,"Larina A.A."),
    Username(3,1,"Bazarov A.A."),
    Username(4,1,"Pechorin A.A."),
    Username(5,2,"Dobroselova A.A."),
    Username(6,1,"Chatski A.A."),
    Username(7,1,"Myshkin A.A."),
    Username(8,1,"Raskolnikov A.A."),
    Username(9,2,"Karenina A.A."),)

var userList = listOf(User(0,"qqq",3,  emptyList(), emptyList(),listOf(0), emptyList(),
    listOf(0), emptyList()),
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
        emptyList())
)

var threeList = emptyList<Three>()

var awardList = listOf<UserAwardList>(
    UserAwardList(0,"sfsfsd","test award list","",0, listOf(
        Award(0,"first","", listOf(0),false),
        Award(1,"second","", emptyList(),false),
    ), listOf(
        0
    )
    )
)

var topList = listOf(Top(0,"Test top", "просто какое-то описание топа. Что-то же нужно написать","sjklfjsklj943ls", UserTop(0,0), listOf(
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

@Serializable
data class CreateRequest(
    val name:String,
    val description:String
)