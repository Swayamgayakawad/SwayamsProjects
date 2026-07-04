package com.example.studchats

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class ChattingViwe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chatting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val SenderMessage = findViewById<TextInputEditText>(R.id.sendmessage)
        val SendBtn = findViewById<ImageButton>(R.id.sendbtn)
        val FrindName = findViewById<TextView>(R.id.Frindtv)
        val message = SenderMessage.text.toString()
        val memberName = intent.getStringExtra("member_name") ?: ""
        val roomId = intent.getStringExtra("room_id") ?: ""
        FrindName.text = memberName
        val control = ChattingViweControl()
        control.creatSubRoom(roomId)
        control.massageRicive(roomId)
        SendBtn.setOnClickListener {
            control.sendMessage()
        }
    }
}