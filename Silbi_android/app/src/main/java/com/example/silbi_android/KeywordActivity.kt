package com.example.silbi_android
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class KeywordActivity: AppCompatActivity(){
    private val Button: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btn1)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keyword)

        Button.setOnClickListener {
            startActivity(Intent(this, Keyword2Activity::class.java))
        }
    }
}