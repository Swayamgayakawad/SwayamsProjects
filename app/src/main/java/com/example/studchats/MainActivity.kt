package com.example.studchats

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var menu: ImageButton
    lateinit var card: CardView
    lateinit var searchIn: EditText
    lateinit var searchBtn: ImageButton
    lateinit var Settings: Button
    lateinit var Add: Button
    lateinit var CreatGrop: Button
    lateinit var Signup: Button
    lateinit var frindlist: RecyclerView
    lateinit var userlist: ArrayList<ChatRoom>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        menu = findViewById(R.id.immenu)
        card = findViewById<CardView>(R.id.mycard)
        searchIn = findViewById<EditText>(R.id.editText)
        searchBtn = findViewById<ImageButton>(R.id.search)
        Signup=findViewById<Button>(R.id.signup)
        Add = findViewById<Button>(R.id.add)
        frindlist = findViewById<RecyclerView>(R.id.userlistlist)
        cardViwe()
        Add.setOnClickListener {
            val intent = Intent(this, Add::class.java)
            startActivity(intent)
            Toast.makeText(this,"Open Add",Toast.LENGTH_SHORT).show()
        }


    }
    fun cardViwe(){
        card.visibility = View.GONE
        searchIn.visibility = View.GONE

        // Menu button click
        menu.setOnClickListener {
            if (card.isVisible) {
                card.visibility = View.GONE
            } else {
                card.visibility = View.VISIBLE
            }
        }
        // Search button click
        searchBtn.setOnClickListener {
            if (searchIn.isVisible) {
                searchIn.visibility = View.GONE
            } else {
                searchIn.visibility = View.VISIBLE
            }
        }
        Signup.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }
}