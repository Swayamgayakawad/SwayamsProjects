package com.example.datatranmit

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etMessage: EditText
    private lateinit var btnSend: Button
    private lateinit var recyclerView: RecyclerView

    private val messageList = ArrayList<Message>()
    private lateinit var adapter: MessageAdapter

    private val ref = FirebaseDatabase.getInstance()
        .getReference("Messages")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        etMessage = findViewById(R.id.etMessage)
        btnSend = findViewById(R.id.btnSend)
        recyclerView = findViewById(R.id.recyclerMessages)

        adapter = MessageAdapter(messageList)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnSend.setOnClickListener {

            val name = etName.text.toString()
            val message = etMessage.text.toString()

            if (name.isNotEmpty() && message.isNotEmpty()) {

                val msg = Message(name, message)

                ref.push().setValue(msg)

                etMessage.setText("")
            }
        }

        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                messageList.clear()

                for (child in snapshot.children) {

                    val msg =
                        child.getValue(Message::class.java)

                    if (msg != null) {
                        messageList.add(msg)
                    }
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}