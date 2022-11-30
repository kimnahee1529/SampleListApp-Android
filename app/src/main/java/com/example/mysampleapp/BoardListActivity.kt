package com.example.mysampleapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BoardListActivity : AppCompatActivity() {

    val TAG = "BoardListActivity"

    lateinit var LVAdapter : ListViewAdapter

    val list = mutableListOf<Model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_list)

        val writeBtn = findViewById<Button>(R.id.writeBtn)
        writeBtn.setOnClickListener {

            val intent = Intent(this, BoardWriteActivity::class.java)
            startActivity(intent)

        }



        LVAdapter = ListViewAdapter(list)

        val lv = findViewById<ListView>(R.id.lv)
        lv.adapter  = LVAdapter

        getData()

    }

    fun getData(){

        val database = Firebase.database
        val myRef = database.getReference("board")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
//                val post = dataSnapshot.getValue<Post>() //어떠한 형태로 데이터를 받을 것인지
                Log.d(TAG, dataSnapshot.toString())
                // ...

                for (dataModel in dataSnapshot.children){
                    val item = dataModel.getValue(Model::class.java)
                    Log.e(TAG, item.toString())
                    list.add(item!!)
                }

                //데이터를 다 받아오고 리스트뷰를 생성하는 게 아니라
                //이미 생성되어 있는 리스트뷰에 값을 받아와서 아무것도 안 그려진다.
                //그래서 동기화 과정이 필요하다
                LVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        myRef.addValueEventListener(postListener)
    }
}