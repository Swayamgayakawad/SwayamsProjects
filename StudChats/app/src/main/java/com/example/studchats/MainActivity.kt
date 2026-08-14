package com.example.studchats

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
 import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var menu: ImageButton
    lateinit var card: CardView
    lateinit var searchIn: EditText
    lateinit var searchBtn: ImageButton
    lateinit var Addbtn: Button
    lateinit var Signup: Button
    lateinit var frindlist: RecyclerView

    private var userlist = ArrayList<ChatRoom>()
    private lateinit var adapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Views
        menu = findViewById(R.id.immenu)
        card = findViewById(R.id.mycard)
        searchIn = findViewById(R.id.editText)
        searchBtn = findViewById(R.id.search)
        Signup = findViewById(R.id.signup)
        Addbtn = findViewById(R.id.add)
        frindlist = findViewById(R.id.userlistlist)

        // RecyclerView
        frindlist.layoutManager = LinearLayoutManager(this)
        adapter = ChatAdapter(userlist)
        frindlist.adapter = adapter

        chatRooms()
        cardViwe()

        Addbtn.setOnClickListener {
            startActivity(Intent(this, Add::class.java))
        }
    }

    fun cardViwe() {
        card.visibility = View.GONE
        searchIn.visibility = View.GONE

        menu.setOnClickListener {
            card.visibility = if (card.isVisible) View.GONE else View.VISIBLE
        }

        searchBtn.setOnClickListener {
            searchIn.visibility = if (searchIn.isVisible) View.GONE else View.VISIBLE
        }

        Signup.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }

    fun chatRooms() {

        val database = FirebaseDatabase.getInstance().reference

        // 🔥 Get current user phone (IMPORTANT)
        val sharedPref = getSharedPreferences("USER", MODE_PRIVATE)
        val mynum = sharedPref.getString("phone", "") ?: ""

        Log.d("DEBUG", "MyNum = $mynum")

        if (mynum.isEmpty()) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        database.child("ChatRooms").get()
            .addOnSuccessListener { snapshot ->

                userlist.clear()

                for (data in snapshot.children) {

                    val roomID = data.key.toString()
                    val name = data.child("name").value.toString()
                    val memberName = data.child("memberName").value.toString()

                    Log.d("DEBUG", "RoomID = $roomID")

                    if (roomID.contains(mynum)) {

                        val chatRoom = if (roomID.startsWith(mynum)) {
                            ChatRoom(roomID, name, memberName)
                        } else {
                            ChatRoom(roomID, memberName, name)
                        }

                        userlist.add(chatRoom)
                    }
                }

                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load chats", Toast.LENGTH_SHORT).show()
            }
    }
}