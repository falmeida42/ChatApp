package com.falmeida.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVED = 1
    val ITEM_SEND = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 1){
            val view: View = LayoutInflater.from(context).inflate(R.layout.recived, parent, false)
            return ReceivedViewHolder(view)
        }else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.send, parent, false)
            return SendViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]

        if (holder.javaClass == SendViewHolder::class.java){

            val viewHolder = holder as SendViewHolder
            holder.sendMessage.text = currentMessage.message
        }else {

            val viewHolder = holder as ReceivedViewHolder
            holder.receivedMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {

        val currentMessage = messageList[position]

        return if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            ITEM_SEND
        } else {
            ITEM_RECEIVED
        }

    }
    override fun getItemCount(): Int {
        return messageList.size
    }

    class SendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sendMessage = itemView.findViewById<TextView>(R.id.text_send_message)
    }

    class ReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receivedMessage = itemView.findViewById<TextView>(R.id.text_received_message)
    }
}