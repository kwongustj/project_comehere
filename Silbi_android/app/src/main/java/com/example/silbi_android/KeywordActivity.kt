package com.example.silbi_android
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.myapplication.rate
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_keyword.*

class KeywordActivity: AppCompatActivity() {

    var RateList = arrayListOf<rate>()
    private val Button: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btn1)
    }

    private val Button1: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btn2)
    }

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

        Button.setOnClickListener {
            startActivity(Intent(this, Keyword2Activity::class.java))
        }

        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) { // 리스트 만들 배열 가져오기
                RateList.clear()
                val test = snapshot.child("정보")
                for (ds in test.children) {
                    val e1 = rate(
                        ds.child("점포이름").getValue().toString(),
                        ds.child("전화번호").getValue().toString(),
                        ds.child("층수").getValue().toString()
                    )
                    RateList.add(e1)
                }
                listView.adapter = Adapter
            }



            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })

    }
}