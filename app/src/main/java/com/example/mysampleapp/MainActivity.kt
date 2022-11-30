package com.example.mysampleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mysampleapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//글쓰기로 데이터 추가하고 다시 리스트 화면으로 오면 이전 리스트가 한꺼번에 다 추가되어 있음... 이거 고치고 싶다!!!
class MainActivity : AppCompatActivity() {

//    FirebaseAuth의 인스턴스를 선언
    private lateinit var auth: FirebaseAuth

    private lateinit var binding : ActivityMainBinding //(Databinding)

    override fun onCreate(savedInstanceState: Bundle?) {
//        FirebaseAuth 인스턴스를 초기화
        auth = Firebase.auth
        val TAG = "MainActivity"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT).show()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main) //(Databinding)
        val joinBtnClicked = findViewById<Button>(R.id.joinBtn)
        joinBtnClicked.setOnClickListener {
//            //email,pwd를 받아오는 두 번째 방법(Databinding)
//            val email = binding.emailArea
//            val pwd = binding.pwdArea

            //email,pwd를 받아오는 첫 번째 방법
            val email = findViewById<EditText>(R.id.emailArea)
            val pwd = findViewById<EditText>(R.id.pwdArea)



            //신규 사용자 가입
            auth.createUserWithEmailAndPassword(email.text.toString(), pwd.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "no", Toast.LENGTH_SHORT).show()
                    }
                }

        }

        binding.logoutBtn.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT).show()
        }

        binding.loginBtn.setOnClickListener {

            val email = findViewById<EditText>(R.id.emailArea)
            val pwd = findViewById<EditText>(R.id.pwdArea)

            //기존 사용자 로그인
            auth.signInWithEmailAndPassword(
                email.text.toString(),
                pwd.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
                        Toast.makeText(this, auth.currentUser?.uid.toString(), Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, BoardListActivity::class.java)
                        startActivity(intent)

                    } else {
                        Toast.makeText(this, "no", Toast.LENGTH_SHORT).show()
                    }
                }

        }


    }
}