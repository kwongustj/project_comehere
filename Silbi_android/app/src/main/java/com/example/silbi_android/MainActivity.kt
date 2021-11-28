package com.example.silbi_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private val Button1: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.gobtn)
    }

    private val Button2: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.gomypage)
    }

    private val drawerLayout: DrawerLayout by lazy {
        findViewById<DrawerLayout>(R.id.drawer)
    }

    private val menu01: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.menu01)
    }

    private val menu02: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.menu02)
    }





    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Button1.setOnClickListener {
            startActivity(Intent(this, FindActivity::class.java))
        }
        Button2.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        menu01.setOnClickListener {
            startActivity(Intent(this, MypageActivity::class.java))
        }

        menu02.setOnClickListener {
            startActivity(Intent(this, AskActivity::class.java))
        }

    }

}