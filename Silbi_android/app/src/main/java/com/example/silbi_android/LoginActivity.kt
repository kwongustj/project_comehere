package com.example.silbi_android

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

//    private val Button: AppCompatButton by lazy {
//        findViewById<AppCompatButton>(R.id.loginbtn)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        Button.setOnClickListener {
//            startActivity(Intent(this, FindActivity::class.java))
//        }

        auth = Firebase.auth

    //    val emailEditText = findViewById<EditText>(R.id.writeemail)
      //  val passwordEditText = findViewById<EditText>(R.id.writepassword)

        initLoginButton()
        initSignUpButton()
        //initEmailAndPasswordEditText()



    }

    private fun initLoginButton() {
        val loginButton = findViewById<AppCompatButton>(R.id.loginbtn)
        loginButton.setOnClickListener {
            val email = getInputEmail()
            val password = getInputPassword()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        finish()
                    } else {
                        Toast.makeText(this,"로그인 실패!! 이메일&패스워드 확인해주쇼", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun initSignUpButton() {
        val signUpButton = findViewById<AppCompatButton>(R.id.signupButton)
        signUpButton.setOnClickListener {

            val email = getInputEmail()
            val password = getInputPassword()

            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) {task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this,"회원가입 성공!!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this,"회원가입 실패ㅠㅠ", Toast.LENGTH_SHORT).show()
                    }

                }
        }
    }

//    private fun initEmailAndPasswordEditText() {
//        val emailEditText = findViewById<EditText>(R.id.writeemail)
//        val passwordEditText = findViewById<EditText>(R.id.writepassword)
//        val loginButton = findViewById<AppCompatButton>(R.id.loginbtn)
//         val signUpButton = findViewById<AppCompatButton>(R.id.signupButton)
//
//        emailEditText.addTextChangedListener{
//
//        }
//
//        emailEditText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val enable = emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()
//                loginButton.isEnabled = enable
//                signUpButton.isEnabled = enable
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//        }
//        )
//    }

    private fun getInputEmail(): String {
        return findViewById<EditText>(R.id.writeemail).text.toString()
    }

    private fun getInputPassword(): String {
        return findViewById<EditText>(R.id.writepassword).text.toString()
    }


}