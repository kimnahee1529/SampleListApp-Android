package com.example.mysampleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BoardWriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_write)


        val writeBtn : Button = findViewById(R.id.writeUploadBtn) //Button을 이렇게 적어도 됨
        writeBtn.setOnClickListener {

            val writetext = findViewById<EditText>(R.id.writeTextArea)

            // Write a message to the database
            val database = Firebase.database
            val myRef = database.getReference("board")

            myRef.push().setValue(
                Model(writetext.text.toString())
            )


        }
    }
}