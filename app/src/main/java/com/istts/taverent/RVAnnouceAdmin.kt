package com.istts.taverent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVAnnouceAdmin(
    private val songs: ArrayList<Pengumuman>,
    private val layout: Int,
): RecyclerView.Adapter<RVAnnouceAdmin.CustomViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CustomViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(
            layout, parent ,false
        ))
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = songs[position]
        holder.textjudul.text = item.judul
        holder.textisi.text = item.isi
        holder.tanggal.text = item.tanggal
        if (item.tipe == 0){
            holder.tipe.text = "Penginap"
        }else{
            holder.tipe.text = "Pemilik"
        }
    }


    override fun getItemCount(): Int {
        return songs.size
    }

    class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textjudul: TextView = itemView.findViewById(R.id.tvJudul)
        val textisi: TextView = itemView.findViewById(R.id.tvIsi)
        val tanggal: TextView = itemView.findViewById(R.id.tvTanggal)
        val tipe: TextView = itemView.findViewById(R.id.tvTipe)
    }

}
