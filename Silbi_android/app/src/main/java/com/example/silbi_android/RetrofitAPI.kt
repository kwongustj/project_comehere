package com.example.silbi_android

import android.provider.ContactsContract
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface RetrofitAPI {
    @GET("/todos")//서버에 GET 요청을 할 주소를 입력
    fun getTodoList() : Call<JsonObject> //MainActivity에서 불러와서 이 함수에 큐를 만들고 대기열에 콜백을 넣어주면 그거갖고 요청하는거임.
}

interface RetrofitAPI2 {

    @GET("/hello")//서버에 GET 요청을 할 주소를 입력
    fun getTodoList(
        @Query("people_who") people: String,
        @Query("people_who2") people2: String,
        @Query("place1") place1: String?,
        @Query("place2") place2: String?
    ) : Call<JsonObject> //MainActivity에서 불러와서 이 함수에 큐를 만들고 대기열에 콜백을 넣어주면 그거갖고 요청하는거임.
}

interface RetrofitAPI3 {

    @GET("/keyword2")//서버에 GET 요청을 할 주소를 입력
    fun getTodoList(
        @Query("keyword1") keyword1: String,
        @Query("keyword2") keyword2: String,
        @Query("keyword3") keyword3: String,
        @Query("keyword4") keyword4: String,
        @Query("keyword5") keyword5: String,
        @Query("keyword6") keyword6: String?,
        @Query("keyword7") keyword7: String?,
        @Query("keyword8") keyword8: String?
    ) : Call<JsonObject> //MainActivity에서 불러와서 이 함수에 큐를 만들고 대기열에 콜백을 넣어주면 그거갖고 요청하는거임.
}