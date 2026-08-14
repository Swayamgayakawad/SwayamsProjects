package com.example.studchats

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val chatRooms: ArrayList<ChatRoom>) :
    RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.user, parent, false)
        return ChatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {

        val currentChatRoom = chatRooms[position]


        holder.userName.text = currentChatRoom.MemberName

        holder.card.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "Chatting with ${currentChatRoom.MemberName}",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(holder.itemView.context, ChattingViwe::class.java)
            intent.putExtra("member_name", currentChatRoom.MemberName)
            intent.putExtra("room_id", currentChatRoom.RoomId)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return chatRooms.size
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.mamberNames)
        val card: CardView = itemView.findViewById(R.id.userbtns)
    }
}