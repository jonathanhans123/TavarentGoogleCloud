package com.istts.taverent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVChatPenginap(
    private val songs: ArrayList<Chat>,
    private val tipe: String,
    var onClickListener: ((view:View,idx:Int)->Unit)?=null,
): RecyclerView.Adapter<RVChatPenginap.CustomViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CustomViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(
            R.layout.rv_chat, parent ,false
        ))
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = songs[position]
        if (tipe=="penginap") {
            holder.tvUsername.text = item.username_pemilik
        }else{
            holder.tvUsername.text = item.username_penginap
        }
        holder.tvTanggal.text = item.created_at.substring(5,10)
        holder.tvMessage.text = item.pesan
        holder.itemView.setOnClickListener{
            onClickListener?.invoke(it,position)
        }
    }


    override fun getItemCount(): Int {
        return songs.size
    }

    class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageChat: ImageView = itemView.findViewById(R.id.imageViewChat)
        val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        val tvMessage: TextView = itemView.findViewById(R.id.tvMessage)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvDate)
    }

}
