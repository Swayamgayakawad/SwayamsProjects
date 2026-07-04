package com.example.datatranmit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(
    private val messageList: List<Message>
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val txtName: TextView =
            itemView.findViewById(R.id.txtName)

        val txtMessage: TextView =
            itemView.findViewById(R.id.txtMessage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_message,
                parent,
                false
            )

        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MessageViewHolder,
        position: Int
    ) {

        val message = messageList[position]

        holder.txtName.text = message.name
        holder.txtMessage.text = message.text
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
}