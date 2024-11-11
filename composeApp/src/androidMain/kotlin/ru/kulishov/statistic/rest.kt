package ru.kulishov.statistic
/*
import android.content.ContentValues.TAG
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface MyApi{
    @GET("/whoami") //get запрос и относительный URL
    fun whoami(): Call<List<WhoamiRequest>>// Определение метода для выполнения GET-запроса
}

fun retrofitRequest(onSuccess: (List<WhoamiRequest>) -> Unit, onFailure: (String) -> Unit) { // при удачном запросе вернем список объектов, при неудачном - строку ошибки
    val retrofit = Retrofit.Builder()
        .baseUrl("https://ed39-87-117-56-165.ngrok-free.app")
        .addConverterFactory(GsonConverterFactory.create()) //указываем на использование Gson для работы с Json
        .build()

    val stataApi: MyApi = retrofit.create(MyApi::class.java)
    stataApi.whoami().enqueue(object : Callback<List<WhoamiRequest>> {
        //получили ответ
        override fun onResponse(call: Call<List<WhoamiRequest>>, response: Response<List<WhoamiRequest>>){
            //если ответ положительный
            if(response.isSuccessful){
                val whoami = response.body() // получаем тело ответа
                //если тело не пустое
                if (whoami != null) {
                    onSuccess(whoami) // вызываем callback с результатом
                } else {
                    onFailure("Response body is null")
                }
            }else{
                Log.e(TAG,"Failed ${response.message()}" )
                onFailure("Request failed: ${response.message()}")
            }
        }
        //не получили ответ
        override fun onFailure(call: Call<List<WhoamiRequest>>, t: Throwable) {
            Log.e(TAG,"Network error ${t.message}")
            onFailure("Network error: ${t.message}")
        }
    })
}

*/