package com.example.studchats

import android.annotation.SuppressLint
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChattingViweControl {
    fun sendMessage() {

    }
    fun creatSubRoom(roomId: String) {

    }
    @SuppressLint("SuspiciousIndentation")
    fun massageRicive(roomID: String){
        var database = FirebaseDatabase.getInstance()
        val ref = database.getReference("ChatRooms")
            .child(roomID)
            .child("messages")

            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = ArrayList<Message>()

                    for (data in snapshot.children) {
                        val msg = data.getValue(Message::class.java)
                        if (msg != null) list.add(msg)
                    }

                    // update RecyclerView here
                }

                override fun onCancelled(error: DatabaseError) {
                }


            })
    }

}