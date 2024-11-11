package ru.kulishov.statistic

import io.ktor.server.auth.Principal
import kotlinx.serialization.Serializable
data class CustomPrincipal(val userId: Int, val realm: String) : Principal
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
data class WhoamiRequest(val username:String,val sex:Int,val plan:Int,val myThree:List<Int>,val inThree:List<Int>,val myTop:List<Int>,val inTop:List<Int>,val myAward:List<Int>,val inAward:List<Int>)