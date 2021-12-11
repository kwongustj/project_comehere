package com.example.silbi_android

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.myapplication.rate
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_mypage.*

class MypageActivity: AppCompatActivity(){


    private val username: TextView by lazy {
        findViewById<TextView>(R.id.place)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        var id = ""
        var RateList = arrayListOf<rate>()
        val Adapter = RateAdapter(this, RateList)
        val listView = findViewById<ListView>(R.id.listView)
        val database: FirebaseDatabase =
            FirebaseDatabase.getInstance("https://silbi-7becf-default-rtdb.asia-southeast1.firebasedatabase.app/")

        val user = Firebase.auth.currentUser
        if (user != null) {
            Log.d("user",user.toString())
            var email = user?.email
            email = user!!.email.toString()
            val splitArray = email.split("@")
            id = splitArray[0]
            username.text = id
        } else {
            Log.d("user","정보 가져올 수 없음")
        }

        val myRef: DatabaseReference = database.getReference("$id")
        myRef.addValueEventListener(object : ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) { // 리스트 만들 배열 가져오기
                RateList.clear()
                val test = snapshot.child("점포")
                for (ds in test.children) {
                            val e1 = rate(
                                ds.child("name").getValue().toString(),
                                ds.child("phone").getValue().toString(),
                                ds.child("floor").getValue().toString(),
                                ds.child("image").getValue().toString()
                            )
                            RateList.add(e1)
                            Log.d("파이어베이스에서 가져온 값",RateList.toString())
                        }
                listView.adapter = Adapter
                    }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })


    }

        }