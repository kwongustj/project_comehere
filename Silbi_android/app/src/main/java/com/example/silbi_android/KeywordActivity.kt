package com.example.silbi_android
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.myapplication.rate
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_keyword.*
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KeywordActivity: AppCompatActivity() {

    val TAG = "TAG_MainActivity"
    var array = Array<String>(10,{""})

    var RateList = arrayListOf<rate>()


    private val chip: Chip by lazy {
        findViewById<Chip>(R.id.chip)
    }

    lateinit var mRetrofit :Retrofit
    lateinit var mRetrofitAPI: RetrofitAPI
    lateinit var mCallTodoList : Call<JsonObject>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keyword)


        val database: FirebaseDatabase =
            FirebaseDatabase.getInstance("https://silbi-7becf-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val myRef: DatabaseReference = database.getReference("점포")

        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        val bottomSheetDialog = BottomSheetDialog(this)
        val listView = findViewById<ListView>(R.id.listView)
        val Adapter = RateAdapter(this,RateList)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.dismissWithAnimation

        buildingName.setText(intent.getStringExtra("building"))


        setRetrofit()
        callTodoList()
        Log.d("array",array[0])
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) { // 리스트 만들 배열 가져오기
                var i = 0
                RateList.clear()
                val test = snapshot.child("정보")
                for (ds in test.children) {
                    for (i in (0..9)) {
                        if (ds.child("점포이름").getValue().toString() == array[i]) {
                            val e1 = rate(
                                ds.child("점포이름").getValue().toString(),
                                ds.child("전화번호").getValue().toString(),
                                ds.child("층수").getValue().toString()
                            )
                            RateList.add(e1)
                        }

                    }
                }
                listView.adapter = Adapter
            }



            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })

    }

    private fun callTodoList() {
        mCallTodoList = mRetrofitAPI.getTodoList()
        mCallTodoList.enqueue(mRetrofitCallback)//응답을 큐 대기열에 넣는다.
    }

    private val mRetrofitCallback  = (object : Callback<JsonObject>{
        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
            t.printStackTrace()
            Log.d(TAG, "에러입니다. => ${t.message.toString()}")

        }
        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
            val result = response.body()
            Log.d(TAG, "결과는 => $result")

            var mGson = Gson()
            val dataParsed0 = mGson.fromJson(result, DataModel.TodoInfo0::class.java)
            array.set(0,dataParsed0.todo0.task)
            val dataParsed1 = mGson.fromJson(result, DataModel.TodoInfo1::class.java)
            array.set(1,dataParsed1.todo1.task)
            val dataParsed2 = mGson.fromJson(result, DataModel.TodoInfo2::class.java)
            array.set(2,dataParsed2.todo2.task)
            val dataParsed3 = mGson.fromJson(result, DataModel.TodoInfo3::class.java)
            array.set(3,dataParsed3.todo3.task)
            val dataParsed4 = mGson.fromJson(result, DataModel.TodoInfo4::class.java)
            array.set(4,dataParsed4.todo4.task)
            val dataParsed5 = mGson.fromJson(result, DataModel.TodoInfo5::class.java)
            array.set(5,dataParsed5.todo5.task)
            val dataParsed6 = mGson.fromJson(result, DataModel.TodoInfo6::class.java)
            array.set(6,dataParsed6.todo6.task)
            val dataParsed7 = mGson.fromJson(result, DataModel.TodoInfo7::class.java)
            array.set(7,dataParsed7.todo7.task)
            val dataParsed8 = mGson.fromJson(result, DataModel.TodoInfo8::class.java)
            array.set(8,dataParsed8.todo8.task)
            val dataParsed9 = mGson.fromJson(result, DataModel.TodoInfo9::class.java)
            array.set(9,dataParsed9.todo9.task)

        }
    })
    private fun setRetrofit(){
        //레트로핏으로 가져올 url설정하고 세팅
        mRetrofit = Retrofit
            .Builder()
            .baseUrl(getString(R.string.baseUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //인터페이스로 만든 레트로핏 api요청 받는 것 변수로 등록
        mRetrofitAPI = mRetrofit.create(RetrofitAPI::class.java)
    }


}