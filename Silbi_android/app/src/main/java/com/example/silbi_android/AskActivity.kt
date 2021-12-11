package com.example.silbi_android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton


class AskActivity : AppCompatActivity() {


    private val Button: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.Button)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask)


        Button.setOnClickListener {
            Toast.makeText(getApplicationContext(),"제출되었습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }

}