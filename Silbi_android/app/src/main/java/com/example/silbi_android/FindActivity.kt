package com.example.silbi_android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import com.example.silbi_android.databinding.ActivityFindBinding
import com.example.silbi_android.databinding.ActivityMainBinding

class FindActivity: AppCompatActivity(){

    private val Button: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btn1)
    }

    private lateinit var binding: ActivityFindBinding
    private lateinit var adapter: SearchRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initViews()

        Button.setOnClickListener {
            startActivity(Intent(this, KeywordActivity::class.java))
        }
    }

    private fun initViews() = with(binding) {
        emptyResultTextView.isVisible = false
        recyclerView.adapter = adapter
    }

    private fun initAdapter() {
        adapter = SearchRecyclerAdapter{
            Toast.makeText(this, "아이템 클릭", Toast.LENGTH_SHORT).show()
        }

    }
}