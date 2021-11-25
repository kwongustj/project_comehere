package com.example.silbi_android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import com.example.silbi_android.databinding.ActivityFindBinding
import com.example.silbi_android.databinding.ActivityMainBinding
import com.example.silbi_android.model.SearchResultEntity

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
        initData()
        setData()

        Button.setOnClickListener {
            startActivity(Intent(this, KeywordActivity::class.java))
        }
    }

    private fun initViews() = with(binding) {
        emptyResultTextView.isVisible = false
        recyclerView.adapter = adapter
    }

    private fun initAdapter() {
        adapter = SearchRecyclerAdapter()

    }

    private fun initData() {
        adapter.notifyDataSetChanged()
    }

    private fun setData() {
        val dataList = (0..10).map {
            SearchResultEntity(
                name = "빌딩 $it",
                fullAddress = "주소 $it"
            )
        }
        adapter.setSearchResultList(dataList) {
        Toast.makeText(this, "빌딩이름:${it.name} 주소: ${it.fullAddress}",Toast.LENGTH_SHORT).show()
        }
    }
}