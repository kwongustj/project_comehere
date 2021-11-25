package com.example.silbi_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import com.example.silbi_android.databinding.ActivityFindBinding
import com.example.silbi_android.databinding.ActivityMainBinding
import com.example.silbi_android.model.SearchResultEntity
import com.example.silbi_android.model.search.Poi
import com.example.silbi_android.model.search.Pois
import com.example.silbi_android.utility.RetrofitUtil
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class FindActivity: AppCompatActivity(),CoroutineScope{

    private lateinit var job:Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val Button: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btn1)
    }

    private lateinit var binding: ActivityFindBinding
    private lateinit var adapter: SearchRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        job = Job()

        initAdapter()
        initViews()
        bindViews()
        initData()

    }

    private fun initViews() = with(binding) {
        emptyResultTextView.isVisible = false
        recyclerView.adapter = adapter
    }

    private fun bindViews() = with(binding){
        Button.setOnClickListener{
            searchKeyword(textbuilding.text.toString())
        }
    }

    private fun initAdapter() {
        adapter = SearchRecyclerAdapter()

    }

    private fun initData() {
        adapter.notifyDataSetChanged()
    }

    private fun setData(pois: Pois) {
        val dataList = pois.poi.map {
            SearchResultEntity(
                name = it.name ?: " 빌딩 명 없음",
                fullAddress = makeMainAdress(it)
            )
        }
        adapter.setSearchResultList(dataList) {
        Toast.makeText(this, "빌딩이름:${it.name} 주소: ${it.fullAddress}",Toast.LENGTH_SHORT).show()
            startActivity(
                Intent(this, KeywordActivity::class.java).apply {
                    putExtra("building",it.name)
                }
            )
        }
    }

    private fun searchKeyword(keywordString: String) {

        launch(coroutineContext) {
            try{
                withContext(Dispatchers.IO) {
                 val response = RetrofitUtil.apiService.getSearchLocation(
                     keyword = keywordString
                 )
                    if(response.isSuccessful) {
                        val body = response.body()
                        withContext(Dispatchers.Main) {
                            Log.d("response",body.toString())
                            body?.let { searchResponse ->
                                setData(searchResponse.searchPoiInfo.pois)
                            }
                        }
                    }
                }

            }catch (e:Exception) {

            }
        }
    }

    private fun makeMainAdress(poi: Poi): String =
        if (poi.secondNo?.trim().isNullOrEmpty()) {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    poi.firstNo?.trim()
        } else {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    (poi.firstNo?.trim() ?: "") + " " +
                    poi.secondNo?.trim()
        }
}