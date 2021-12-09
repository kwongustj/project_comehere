package com.example.silbi_android

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.rate
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_keyword.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Collections.list

class RecommendActivity: AppCompatActivity(){

    lateinit var mRetrofit3: Retrofit
    lateinit var mRetrofitAPI3: RetrofitAPI3
    lateinit var mCallTodoList3: Call<JsonObject>

    private val viewPager: ViewPager2 by lazy {
        findViewById<ViewPager2>(R.id.viewpager)
    }

    val database: FirebaseDatabase =
        FirebaseDatabase.getInstance("https://silbi-7becf-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val myRef: DatabaseReference = database.getReference("점포")

    var array4 = arrayListOf<String>()
    var array5 = arrayListOf<String>()
    private var cardslist = mutableListOf<card>()
    val cardpagerAdapter = cardpagerAdapter( cardslist)

//    val Adapter = cardpagerAdapter(cardslist, )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommand)

        setRetrofit3()
        callTodoList3()

        initViews()


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
            val dataParsed0 = mGson.fromJson(result, DataModel3.TodoInfo0::class.java)
            array4.add(dataParsed0.todo0.task)
            array5.add(dataParsed0.todo0.url)
            val dataParsed1 = mGson.fromJson(result, DataModel3.TodoInfo1::class.java)
            array4.add(dataParsed1.todo1.task)
            array5.add(dataParsed1.todo1.url)
            val dataParsed2 = mGson.fromJson(result, DataModel3.TodoInfo2::class.java)
            array4.add(dataParsed2.todo2.task)
            array5.add(dataParsed2.todo2.url)
            val dataParsed3 = mGson.fromJson(result, DataModel3.TodoInfo3::class.java)
            array4.add(dataParsed3.todo3.task)
            array5.add(dataParsed3.todo3.url)
            val dataParsed4 = mGson.fromJson(result, DataModel3.TodoInfo4::class.java)
            array4.add(dataParsed4.todo4.task)
            array5.add(dataParsed4.todo4.url)

            Log.d("받은 점포 명: ", array4.toString())
            Log.d("url" , array5.toString())

            myRef.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) { // 리스트 만들 배열 가져오기
                    cardslist.clear()
                    val test = snapshot.child("정보")
                    for (ds in test.children) {
                        for (i in (0..4)) {
                            if (ds.child("점포이름").getValue().toString() == array4[i]) {

                                val e1 = card(
                                    ds.child("점포이름").getValue().toString(),
                                    array5[i],
                                    ds.child("전화번호").getValue().toString(),
                                    ds.child("층수").getValue().toString()
                                )
                                cardslist.add(e1)
                            }
                        viewPager.adapter = cardpagerAdapter

                        }
                    }
                }


                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", error.toException())
                }

            })

        }
    }
            )



    private fun setRetrofit3() {
        mRetrofit3 = Retrofit
            .Builder()
            .baseUrl(getString(R.string.baseUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mRetrofitAPI3 = mRetrofit3.create(RetrofitAPI3::class.java)
    }


    private fun initViews() {
        val cards = cardslist
        viewPager.adapter = cardpagerAdapter(
            cards
        )
    }
}
