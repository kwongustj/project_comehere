package com.example.silbi_android

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Keyword2Activity: AppCompatActivity(){

    lateinit var mRetrofit2: Retrofit
    lateinit var mRetrofitAPI2: RetrofitAPI2
    lateinit var mCallTodoList2: Call<JsonObject>
    var array2 = arrayListOf<String>()
    var array3 = arrayListOf<String>(" ", " ")
    private val Button: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btn1)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keyword2)

        if(intent.hasExtra("listString")) {
            var listString = intent.getStringExtra("listString")
            if (listString != null) {
                val list = listString.split("  ")
                for (i in list) {
                    array2.add(i)
                }
                Log.d("aaaa",array2[0])
            }
        // text_sub의 문구를 msg라는 키값을 가진 intent의 정보로 바꾸어준다. }
            Toast.makeText(this@Keyword2Activity, listString, Toast.LENGTH_SHORT).show()
            setRetrofit2()
            callTodoList2()
        }


        Button.setOnClickListener {
            startActivity(Intent(this, RecommendActivity::class.java))
        }
    }

    private fun callTodoList2() {
        mCallTodoList2 = mRetrofitAPI2.getTodoList(
            array2[0],
            array2[1],
            array2[2],
            array2[3]
        )
        mCallTodoList2.enqueue(mRetrofitCallback2)//응답을 큐 대기열에 넣는다.
    }

    private val mRetrofitCallback2
            =(object : Callback<JsonObject> {
        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
            t.printStackTrace()
            Log.d(TAG, "에러입니다. => ${t.message.toString()}")

        }

        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
            val result = response.body()
            Log.d(TAG, "결과는 => $result")

            var mGson = Gson()
            val dataParsed0 = mGson.fromJson(result, DataModel2.TodoInfo10::class.java)
            array3.set(0, dataParsed0.todo1.data)
            val dataParsed1 = mGson.fromJson(result, DataModel2.TodoInfo11::class.java)
            array3.set(1, dataParsed1.todo2.data)
            Log.d("값", array3[0])
            Log.d("값2", array3[1])



        }
    })


    private fun setRetrofit2() {
        //레트로핏으로 가져올 url설정하고 세팅
        mRetrofit2 = Retrofit
            .Builder()
            .baseUrl(getString(R.string.baseUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //인터페이스로 만든 레트로핏 api요청 받는 것 변수로 등록
        mRetrofitAPI2 = mRetrofit2.create(RetrofitAPI2::class.java)
    }

}