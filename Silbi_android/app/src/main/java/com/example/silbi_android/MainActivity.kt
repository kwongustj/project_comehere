package com.example.silbi_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {

    private val Button: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.loginbtn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Button.setOnClickListener {
            startActivity(Intent(this, FindActivity::class.java))
        }
    }
}