package com.example.silbi_android
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_keyword.*

class KeywordActivity: AppCompatActivity(){

    private val Button: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btn1)
    }

    private val Button1: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.btn2)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keyword)

        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)

        buildingName.setText(intent.getStringExtra("building"))

        Button.setOnClickListener {
            startActivity(Intent(this, Keyword2Activity::class.java))
        }

        Button1.setOnClickListener {
            bottomSheetDialog.show()
        }
    }
}