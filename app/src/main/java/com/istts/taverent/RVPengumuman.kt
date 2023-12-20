package com.istts.taverent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVPengumuman(
    private val judul: ArrayList<String>,
    private val isi: ArrayList<String>,
    var onClickListener: ((view:View,idx:Int)->Unit)?=null,
): RecyclerView.Adapter<RVPengumuman.CustomViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CustomViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(
            R.layout.rv_pengumuman, parent ,false
        ))
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.tvJudul.text = judul[position]
        holder.tvIsi.text = isi[position]
    }


    override fun getItemCount(): Int {
        return judul.size
    }

    class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvJudul: TextView = itemView.findViewById(R.id.tvJudul)
        val tvIsi: TextView = itemView.findViewById(R.id.tvIsi)
    }

}
