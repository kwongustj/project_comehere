package com.example.silbi_android

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
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
import java.util.*

class KeywordActivity : AppCompatActivity() {

    private val chipgroup: ChipGroup by lazy {
        findViewById<ChipGroup>(R.id.chipgroup)
    }

    private val chipgroup2: ChipGroup by lazy {
        findViewById<ChipGroup>(R.id.chipgroup2)
    }

    val TAG = "TAG_MainActivity"
    var array = Array<String>(10, { "" })
    var array2 = Array<String>(10, { "" })

    var RateList = arrayListOf<rate>()
    var selectedKeywordList1 = arrayListOf<String>()
    var selectedKeywordList2 = arrayListOf<String>()

    private val btn1: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btn1)
    }

    private val btn2: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btn2)
    }

    private val purpose: TextView by lazy {
        findViewById<TextView>(R.id.purpose)
    }


    lateinit var mRetrofit: Retrofit
    lateinit var mRetrofitAPI: RetrofitAPI
    lateinit var mCallTodoList: Call<JsonObject>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keyword)

        var list: List<String> = listOf("#가족과 왔어요", "#연인과 왔어요", "#친구와 왔어요", "#아이와 왔어요")
        var list2: List<String> = listOf(
            "#배고파요",
            "#옷사고싶어요",
            "#가구&가전제품 보러왔어요",
            "#카페에서 쉬고싶어요",
            "#편의시설이 궁금해요",
            "#꾸미고 싶어요",
            "#조용한 곳에서 힐링하고 싶어요",
            "#분위기 있는 곳에 가고싶어요",
            "#가벼운 간식을 사고싶어요",
            "#선물을 사러 왔어요",
            "#생활 용품을 사러 왔어요",
            "#여가를 즐기고 싶어요",
            "#화장품&악세서리 구경하고 싶어요"
        )
        var list3: List<String> = listOf( //아이
            "#배고파요",
            "#옷사고싶어요",
            "#가구&가전제품 보러왔어요",
            "#카페에서 쉬고싶어요",
            "#편의시설이 궁금해요",
            "#조용한 곳에서 힐링하고 싶어요",
            "#분위기 있는 곳에 가고싶어요",
            "#가벼운 간식을 사고싶어요",
            "#선물을 사러 왔어요",
            "#여가를 즐기고 싶어요"
        )
        var list4: List<String> = listOf( //연인 or 친구
            "#배고파요",
            "#옷사고싶어요",
            "#카페에서 쉬고싶어요",
            "#편의시설이 궁금해요",
            "#조용한 곳에서 힐링하고 싶어요",
            "#분위기 있는 곳에 가고싶어요",
            "#가벼운 간식을 사고싶어요",
            "#선물을 사러 왔어요",
        )



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

        setRetrofit()
        callTodoList()
        Log.d("불러오기 성공","Success")
        btn1.setOnClickListener {
            add()
        }

        for (i in list) {
            onAddChip(this, i)
        }
        btn2.setOnClickListener {
            btn1.visibility = View.VISIBLE
            purpose.visibility = View.VISIBLE
            if(selectedKeywordList1.size == 2) {
                chipgroup2.removeAllViews()
                for (i in list2){
                    onAddChip2(this, i)
                }
            } else{
                if(selectedKeywordList1[0] == "아이") {
                    chipgroup2.removeAllViews()
                    for (i in list3) {
                        onAddChip2(this, i)
                    }
                }
                else if (selectedKeywordList1[0] == "가족") {
                    chipgroup2.removeAllViews()
                     for (i in list2) {
                    onAddChip2(this, i)
                }
                }
                else {
                    chipgroup2.removeAllViews()
                    for (i in list4) {
                        onAddChip2(this, i)
                    }
                }
            }
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
                                ds.child("층수").getValue().toString(),
                                array2[i]

                            )
                            RateList.add(e1)
                            Log.d("파이어베이스에서 가져온 값",RateList.toString())
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

    fun add() {
        var selectedKeywordList = arrayListOf<String>()
        selectedKeywordList.addAll(selectedKeywordList2)
        selectedKeywordList.addAll(selectedKeywordList1)
        Log.d("selectedKeywordList",selectedKeywordList.toString())
        var listString = selectedKeywordList.joinToString("  ")
        Log.d("listString",listString)
        val intent = Intent(this, Keyword2Activity::class.java)
            intent.apply{
                intent.putExtra("listString", listString)
                intent.putExtra("building",buildingName.text)
            }
        startActivity(intent)
    }
    fun onAddChip(view: KeywordActivity, i: String) {

        val chip = Chip(this)
        chip.text = i
        chip.setChipBackgroundColorResource(R.color.bg_chip_state_list)
        val drawble = ChipDrawable.createFromAttributes(
            this,
            null,
            0,
            R.style.Widget_MaterialComponents_Chip_Choice
        )
        chip.setChipDrawable(drawble)
        chip.chipBackgroundColor = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked)
            ),
            intArrayOf(Color.rgb(220, 220, 220), Color.rgb(255, 215, 157))
        )
        chip.isCheckable = true
        var check = chip.isChecked

        chip.setOnClickListener {
            if (check == true) {
                var str_data = i.replace("#", "")
                str_data = str_data.replace("과 왔어요", "")
                str_data = str_data.replace("와 왔어요", "")
                check = false
                selectedKeywordList1.remove(str_data)


            } else {
                var str_data = i.replace("#", "")
                str_data = str_data.replace("과 왔어요", "")
                str_data = str_data.replace("와 왔어요", "")
                check = true
                if (selectedKeywordList1.size == 2) {
                    selectedKeywordList1.add(str_data)
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Title")
                    builder.setMessage("2개까지만 선택해주세요")
                    builder.setNeutralButton("확인", null)
                    builder.show()
                } else {
                    selectedKeywordList1.add(str_data)

                }

            }

        }
        chipgroup.addView(chip)
    }

    fun onAddChip2(view: KeywordActivity, i: String) {
        val chip = Chip(this)
        val drawble = ChipDrawable.createFromAttributes(
            this,
            null,
            0,
            R.style.Widget_MaterialComponents_Chip_Choice
        )
        var check = chip.isChecked

        chip.text = i
        chip.setChipBackgroundColorResource(R.color.bg_chip_state_list)
        chip.setChipDrawable(drawble)
        chip.isCheckable = true
        chip.chipBackgroundColor = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked), intArrayOf(android.R.attr.state_checked)
            ),
            intArrayOf(Color.rgb(220, 220, 220), Color.rgb(255, 215, 157))
        )

        chip.setOnClickListener {
            if (check == true) {
                var str_data = i.replace("#", "")
                selectedKeywordList2.remove(str_data)
                check = false
            } else {
                check = true
                if (selectedKeywordList2.size == 2) {
                    var str_data = i.replace("#", "")
                    selectedKeywordList2.add(str_data)
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Title")
                    builder.setMessage("2개까지만 선택해주세요")
                    builder.setNeutralButton("확인", null)
                    builder.show()
                } else {
                    var str_data = i.replace("#", "")
                    selectedKeywordList2.add(str_data)
                }

            }
        }
        chipgroup2.addView(chip)
    }

    private fun callTodoList() {
        mCallTodoList = mRetrofitAPI.getTodoList()
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
            val dataParsed0 = mGson.fromJson(result, DataModel1.TodoInfo0::class.java)
            array.set(0, dataParsed0.todo0.task)
            array2.set(0, dataParsed0.todo0.url)
            val dataParsed1 = mGson.fromJson(result, DataModel1.TodoInfo1::class.java)
            array.set(1, dataParsed1.todo1.task)
            array2.set(1, dataParsed1.todo1.url)
            val dataParsed2 = mGson.fromJson(result, DataModel1.TodoInfo2::class.java)
            array.set(2, dataParsed2.todo2.task)
            array2.set(2, dataParsed2.todo2.url)
            val dataParsed3 = mGson.fromJson(result, DataModel1.TodoInfo3::class.java)
            array.set(3, dataParsed3.todo3.task)
            array2.set(3, dataParsed3.todo3.url)
            val dataParsed4 = mGson.fromJson(result, DataModel1.TodoInfo4::class.java)
            array.set(4, dataParsed4.todo4.task)
            array2.set(4, dataParsed4.todo4.url)
            val dataParsed5 = mGson.fromJson(result, DataModel1.TodoInfo5::class.java)
            array.set(5, dataParsed5.todo5.task)
            array2.set(5, dataParsed5.todo5.url)
            val dataParsed6 = mGson.fromJson(result, DataModel1.TodoInfo6::class.java)
            array.set(6, dataParsed6.todo6.task)
            array2.set(6, dataParsed6.todo6.url)
            val dataParsed7 = mGson.fromJson(result, DataModel1.TodoInfo7::class.java)
            array.set(7, dataParsed7.todo7.task)
            array2.set(7, dataParsed7.todo7.url)
            val dataParsed8 = mGson.fromJson(result, DataModel1.TodoInfo8::class.java)
            array.set(8, dataParsed8.todo8.task)
            array2.set(8, dataParsed8.todo8.url)
            val dataParsed9 = mGson.fromJson(result, DataModel1.TodoInfo9::class.java)
            array.set(9, dataParsed9.todo9.task)
            array2.set(9, dataParsed9.todo9.url)

            Log.d("array",Arrays.toString(array))
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
        mRetrofitAPI = mRetrofit.create(RetrofitAPI::class.java)
    }



}