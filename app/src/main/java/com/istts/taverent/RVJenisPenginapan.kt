package com.istts.taverent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RVJenisPenginapan(
    private val nama: ArrayList<String>,
    private val image: ArrayList<Int>,
    private val onMoreClickListener: (view: View, idx:Int)->Unit,
): RecyclerView.Adapter<RVJenisPenginapan.CustomViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CustomViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(
            R.layout.rv_jenis_penginapan, parent ,false
        ))
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = nama[position]
        val image = image[position]
        holder.imageview.setImageResource(image)
        holder.textview.text = item
        holder.card.setOnClickListener {
            onMoreClickListener?.invoke(it,position)
        }
    }


    override fun getItemCount(): Int {
        return nama.size
    }

    class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageview: ImageView = itemView.findViewById(R.id.imageView7)
        val textview: TextView = itemView.findViewById(R.id.textView22)
        val card: CardView = itemView.findViewById(R.id.card)


    }

}
