package com.example.silbi_android

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_keyword.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecommendActivity: AppCompatActivity(){

    lateinit var mRetrofit3: Retrofit
    lateinit var mRetrofitAPI3: RetrofitAPI3
    lateinit var mCallTodoList3: Call<JsonObject>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommand)

        setRetrofit3()
        callTodoList3()

    }

    private fun callTodoList3() {

        var liststring = intent.getStringExtra("string")
        val list = liststring!!.split(" ")
        var str_arr = arrayListOf<String>(" ", " ", " ", " "," "," "," "," ")
        var count = 0
        for ( i in list){
            str_arr.set(count,i)
            count = count + 1
        }
        Log.d("count",str_arr.toString())

        mCallTodoList3 = mRetrofitAPI3.getTodoList(
            str_arr[0],
            str_arr[1],
            str_arr[2],
            str_arr[3],
            str_arr[4],
            str_arr[5],
            str_arr[6],
            str_arr[7]
        )
        mCallTodoList3.enqueue(mRetrofitCallback3)//응답을 큐 대기열에 넣는다.
    }

    private val mRetrofitCallback3
            = (object : Callback<JsonObject> {
        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
            t.printStackTrace()
            Log.d(ContentValues.TAG, "에러입니다. => ${t.message.toString()}")

        }

        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
            val result = response.body()

            var mGson = Gson()
            val  dataParsed0= mGson.fromJson(result, DataModel2.TodoInfo10::class.java)
            var list = dataParsed0.todo1.data.toString().split(", ")
            val dataParsed1 = mGson.fromJson(result, DataModel2.TodoInfo11::class.java)
            list = dataParsed1.todo2.data.toString().split(", ")

        }
    })


    private fun setRetrofit3() {
        mRetrofit3 = Retrofit
            .Builder()
            .baseUrl(getString(R.string.baseUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mRetrofitAPI3 = mRetrofit3.create(RetrofitAPI3::class.java)
    }
}