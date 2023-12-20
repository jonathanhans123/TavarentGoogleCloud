package com.istts.taverent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVPesan(
    private val songs: ArrayList<Chat>,
    private val tipe: String,
    private val context: Context,
): RecyclerView.Adapter<RVPesan.CustomViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CustomViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(
            R.layout.rv_pesan, parent ,false
        ))
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = songs[position]
        if ((item.sender=="pemilik"&&tipe=="penginap")||(item.sender=="penginap"&&tipe=="pemilik")) {
            holder.imagePesan2.visibility = View.GONE
            holder.tvTanggal2.visibility = View.GONE

            holder.tvPesan.text = item.pesan
            holder.tvTanggal.text = item.created_at.substring(5,10)
        }else if ((item.sender=="pemilik"&&tipe=="pemilik")||(item.sender=="penginap"&&tipe=="penginap")){
            holder.imagePesan.visibility = View.GONE
            holder.tvTanggal.visibility = View.GONE

            holder.tvPesan.backgroundTintList = context.getColorStateList(R.color.gray)
            holder.tvPesan.text = item.pesan
            holder.tvTanggal2.text = item.created_at.substring(5,10)
        }

    }


    override fun getItemCount(): Int {
        return songs.size
    }

    class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imagePesan: ImageView = itemView.findViewById(R.id.imageViewPesan)
        val imagePesan2: ImageView = itemView.findViewById(R.id.imageViewPesan2)
        val tvPesan: TextView = itemView.findViewById(R.id.tvPesan)
        val tvTanggal: TextView = itemView.findViewById(R.id.tvWaktu)
        val tvTanggal2: TextView = itemView.findViewById(R.id.tvWaktu2)
    }

}
