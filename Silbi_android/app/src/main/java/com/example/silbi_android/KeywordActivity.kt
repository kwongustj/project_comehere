package com.example.silbi_android

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.marginRight
import com.example.myapplication.rate
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_keyword.*
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KeywordActivity : AppCompatActivity() {

    private val chipgroup: ChipGroup by lazy {
        findViewById<ChipGroup>(R.id.chipgroup)
    }

    private val chipgroup2: ChipGroup by lazy {
        findViewById<ChipGroup>(R.id.chipgroup2)
    }

    val TAG = "TAG_MainActivity"
    var array = Array<String>(10, { "" })

    var RateList = arrayListOf<rate>()
    var selectedKeywordList =arrayListOf<String>("1","2","3")

    private val btn1: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btn1)
    }

    lateinit var mRetrofit: Retrofit
    lateinit var mRetrofitAPI: RetrofitAPI2
    lateinit var mCallTodoList: Call<JsonObject>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keyword)

        var list: List<String> = listOf("#가족과 왔어요", "#연인과 왔어요", "#친구와 왔어요", "#아이와 왔어요")
        var list2: List<String> = listOf("#배고파요","#옷사고싶어요","#가구&가전제품 보러왔어요","#카페에서놀래요","#편의시설이 궁금해요")


        val database: FirebaseDatabase =
            FirebaseDatabase.getInstance("https://silbi-7becf-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val myRef: DatabaseReference = database.getReference("점포")

        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        val bottomSheetDialog = BottomSheetDialog(this)
        val listView = findViewById<ListView>(R.id.listView)
        val Adapter = RateAdapter(this, RateList)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.dismissWithAnimation

        buildingName.setText(intent.getStringExtra("building"))

        btn1.setOnClickListener {
            for(i in selectedKeywordList) {
                Toast.makeText(this@KeywordActivity,i,Toast.LENGTH_SHORT).show()
            }
            //startActivity(Intent(this, Keyword2Activity::class.java))
        }


        setRetrofit()
        callTodoList()
        Log.d("array", array[0])

        for( i in list) {
            onAddChip(this,i)
        }

        for( i in list2){
            onAddChip2(this,i)
        }
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) { // 리스트 만들 배열 가져오기
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

    fun onAddChip(view: KeywordActivity, i:String) {
        val chip = Chip(this)
        chip.text = i
        chip.setChipBackgroundColorResource(R.color.bg_chip_state_list)
        val drawble = ChipDrawable.createFromAttributes(this,null,0, R.style.Widget_MaterialComponents_Chip_Choice)
        chip.setChipDrawable(drawble)
        chip.isCheckable = true
        var check = chip.isChecked
        chip.setOnClickListener{
            if(check == true){
                Toast.makeText(this@KeywordActivity, "빼", Toast.LENGTH_SHORT).show()
                var str_data = i.replace("#","")
                str_data = str_data.replace("과 왔어요","")
                str_data = str_data.replace("와 왔어요","")
                check = false
                selectedKeywordList.remove(str_data)
            } else {
                Toast.makeText(this@KeywordActivity, "추가됨", Toast.LENGTH_SHORT).show()
                var str_data = i.replace("#", "")
                str_data = str_data.replace("과 왔어요","")
                str_data = str_data.replace("와 왔어요","")
                check = true
                selectedKeywordList.add(str_data)
            }
        }
        chipgroup.addView(chip)
    }

    fun onAddChip2(view: KeywordActivity, i:String) {
        val chip = Chip(this)
        chip.text = i
        chip.setChipBackgroundColorResource(R.color.bg_chip_state_list)
        val drawble = ChipDrawable.createFromAttributes(this,null,0, R.style.Widget_MaterialComponents_Chip_Choice)
        chip.setChipDrawable(drawble)
        chip.isCheckable = true
        var check = chip.isChecked
        chip.setOnClickListener{
            if(check == true){
                Toast.makeText(this@KeywordActivity, "빼", Toast.LENGTH_SHORT).show()
                var str_data = i.replace("#", "")
                selectedKeywordList.remove(str_data)
                check = false
            } else {
                Toast.makeText(this@KeywordActivity, "추가됨", Toast.LENGTH_SHORT).show()
                var str_data = i.replace("#", "")
                selectedKeywordList.add(str_data)
                check = true
            }
        }
        chipgroup2.addView(chip)
    }

    private fun callTodoList() {
        mCallTodoList = mRetrofitAPI.getTodoList(selectedKeywordList[0],selectedKeywordList[1],selectedKeywordList[2])
        mCallTodoList.enqueue(mRetrofitCallback)//응답을 큐 대기열에 넣는다.
    }

    private val mRetrofitCallback = (object : Callback<JsonObject> {
        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
            t.printStackTrace()
            Log.d(TAG, "에러입니다. => ${t.message.toString()}")

        }

        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
            val result = response.body()
            Log.d(TAG, "결과는 => $result")

            var mGson = Gson()
            val dataParsed0 = mGson.fromJson(result, DataModel.TodoInfo0::class.java)
            array.set(0, dataParsed0.todo0.task)
            val dataParsed1 = mGson.fromJson(result, DataModel.TodoInfo1::class.java)
            array.set(1, dataParsed1.todo1.task)
            val dataParsed2 = mGson.fromJson(result, DataModel.TodoInfo2::class.java)
            array.set(2, dataParsed2.todo2.task)
            val dataParsed3 = mGson.fromJson(result, DataModel.TodoInfo3::class.java)
            array.set(3, dataParsed3.todo3.task)
            val dataParsed4 = mGson.fromJson(result, DataModel.TodoInfo4::class.java)
            array.set(4, dataParsed4.todo4.task)
            val dataParsed5 = mGson.fromJson(result, DataModel.TodoInfo5::class.java)
            array.set(5, dataParsed5.todo5.task)
            val dataParsed6 = mGson.fromJson(result, DataModel.TodoInfo6::class.java)
            array.set(6, dataParsed6.todo6.task)
            val dataParsed7 = mGson.fromJson(result, DataModel.TodoInfo7::class.java)
            array.set(7, dataParsed7.todo7.task)
            val dataParsed8 = mGson.fromJson(result, DataModel.TodoInfo8::class.java)
            array.set(8, dataParsed8.todo8.task)
            val dataParsed9 = mGson.fromJson(result, DataModel.TodoInfo9::class.java)
            array.set(9, dataParsed9.todo9.task)

            
        }
    })

    private fun setRetrofit() {
        //레트로핏으로 가져올 url설정하고 세팅
        mRetrofit = Retrofit
            .Builder()
            .baseUrl(getString(R.string.baseUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //인터페이스로 만든 레트로핏 api요청 받는 것 변수로 등록
        mRetrofitAPI = mRetrofit.create(RetrofitAPI2::class.java)
    }


}