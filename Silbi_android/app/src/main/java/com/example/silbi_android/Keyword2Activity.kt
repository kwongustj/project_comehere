package com.example.silbi_android

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.chip.ChipGroup
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_keyword.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Keyword2Activity: AppCompatActivity(){

    lateinit var mRetrofit2: Retrofit
    lateinit var mRetrofitAPI2: RetrofitAPI2
    lateinit var mCallTodoList2: Call<JsonObject>

    private val chipgroup: ChipGroup by lazy {
        findViewById<ChipGroup>(R.id.chipgroup)
    }
    var selectedKeywordList = arrayListOf<String>()
    var array2 = arrayListOf<String>()
    var array3 = arrayListOf<String>()

    private val Button: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btn1)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keyword2)

            var listString = intent.getStringExtra("listString")
            if (listString != null) {
                val list = listString.split("  ")
                for (i in list) {
                    array2.add(i)
                    Log.d("${i}",i.toString())
                }
                setRetrofit2()
                callTodoList2()
            }

        buildingName.setText(intent.getStringExtra("building"))

            Toast.makeText(this@Keyword2Activity, listString, Toast.LENGTH_SHORT).show()


        Button.setOnClickListener {
            var string = selectedKeywordList.joinToString(" ")
            val intent = Intent(this, RecommendActivity::class.java)
            intent.apply{
                intent.putExtra("string", string)
            }
            startActivity(intent)
        }
    }

    fun onAddChip(view: Keyword2Activity, i: String) {
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
                Toast.makeText(this@Keyword2Activity, "빼", Toast.LENGTH_SHORT).show()
                check = false
                selectedKeywordList.remove(i)

            } else {
                Toast.makeText(this@Keyword2Activity, "추가됨", Toast.LENGTH_SHORT).show()
                check = true
                if (selectedKeywordList.size == 8 ) {
                    selectedKeywordList.add(i)
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Title")
                    builder.setMessage("5 ~ 8개까지만 선택해주세요")
                    builder.setNeutralButton("확인", null)
                    builder.show()

                } else {
                    selectedKeywordList.add(i)
                }
            }
        }
        chipgroup.addView(chip)
    }

    private fun callTodoList2() {

        val size = array2.size
        if(size != 4) {
            for( i in (1..4-size)) {
                array2.add(" ")
            }
        }
        mCallTodoList2 = mRetrofitAPI2.getTodoList(
            array2[0],
            array2[1],
            array2[2],
            array2[3]
        )
        mCallTodoList2.enqueue(mRetrofitCallback2)//응답을 큐 대기열에 넣는다.
    }

    private val mRetrofitCallback2
            = (object : Callback<JsonObject> {
        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
            t.printStackTrace()
            Log.d(TAG, "에러입니다. => ${t.message.toString()}")

        }

        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
            val result = response.body()

            var mGson = Gson()
            val dataParsed0 = mGson.fromJson(result, DataModel2.TodoInfo10::class.java)
            array3.add(dataParsed0.todo1!!.data)
            var list = dataParsed0.todo1.data.toString().split(", ")

            for (i in list) {
                onAddChip(this@Keyword2Activity, i)
            }

            if (dataParsed0.todo2 != null) {
                list = dataParsed0.todo2!!.data.toString().split(", ")
                array3.add(dataParsed0.todo2!!.data)

                for (i in list) {
                    onAddChip(this@Keyword2Activity, i)
                }
            }
        }
    })


    private fun setRetrofit2() {
        mRetrofit2 = Retrofit
            .Builder()
            .baseUrl(getString(R.string.baseUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mRetrofitAPI2 = mRetrofit2.create(RetrofitAPI2::class.java)
    }

}