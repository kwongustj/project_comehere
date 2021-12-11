package com.example.silbi_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_mypage.*

class MypageActivity: AppCompatActivity(){


    private val username: TextView by lazy {
        findViewById<TextView>(R.id.place)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        findName()


    }

    private fun findName() {
        val user = Firebase.auth.currentUser
        if (user != null) {
            Log.d("user",user.toString())
            val email = user?.email

            username.text = email
        } else {
            Log.d("user","정보 가져올 수 없음")
        }

            }
        }