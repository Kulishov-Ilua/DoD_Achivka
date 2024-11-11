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
data class User(val userId:Int,val token:String,val plan:Int,val myThree:List<Int>,val inThree:List<Int>,val myTop:List<Int>,val inTop:List<Int>,val myAward:List<Int>,val inAward:List<Int>)

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
data class Userkey(val userId: Int, val login:String, val password:ByteArray){}
val myRealm = "fsfkos"
fun getMd5Digest(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(UTF_8))
    var usrKeyList = mutableListOf(Userkey(0,"test", getMd5Digest("test:${myRealm}:5")))



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
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//Award
//Variables:
//              awardId:Int - id award
//              imagePassiveId:Image - passive award image
//              imageActiveId:Image - active award image
//              userActive:List<BigInteger> - users
//              status:Bool - status
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

data class Award(val awardId:Int, val imagePassiveId: ContentType.Image, val imageActiveId: ContentType.Image, val userActive:List<BigInteger>, var status:Boolean)

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

data class UserAwardList(val id:Int,val name:String, val description:String,val admin:Int,val awardList:List<Award>,val users:List<BigInteger>)

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

var usernameList = listOf(Username(0,1,"test"))

var userList = listOf(User(0,"afl;dsjfksdjflksdjfls",10, emptyList(), emptyList(), emptyList(),
    emptyList(), emptyList(), emptyList()
))

var threeList = emptyList<Three>()

var awardList = emptyList<UserAwardList>()

var topList = emptyList<Top>()