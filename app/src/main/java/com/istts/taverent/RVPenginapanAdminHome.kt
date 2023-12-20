package com.istts.taverent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class RVPenginapanAdminHome(
    private val songs: ArrayList<Penginapan>,
    private val layout: Int,
    private val onMoreClickListener: (view: View, idx:Int)->Unit,
): RecyclerView.Adapter<RVPenginapanAdminHome.CustomViewHolder>(){

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
        holder.textnamakos.text = item.nama
        holder.textjk.text = item.jk_boleh
        holder.textalamat.text = item.alamat

    }


    override fun getItemCount(): Int {
        return songs.size
    }

    class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageview: ImageView = itemView.findViewById(R.id.imageView6)
        val textjk: TextView = itemView.findViewById(R.id.tvJkboleh)
        val textnamakos: TextView = itemView.findViewById(R.id.tvNamakos)
        val textkota: TextView = itemView.findViewById(R.id.tvKota)
        val textalamat: TextView = itemView.findViewById(R.id.tvAlamat)
        val textrating: TextView = itemView.findViewById(R.id.textView20)
        val ratingbar: RatingBar = itemView.findViewById(R.id.ratingBar)

    }

}
