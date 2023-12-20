package com.istts.taverent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RVPemilikAdminHome(
    private val songs: ArrayList<Pemilik>,
    private val layout: Int,
    private val onMoreClickListener: (view: View, idx:Int)->Unit,
): RecyclerView.Adapter<RVPemilikAdminHome.CustomViewHolder>(){

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
        holder.textnama.text = item.nama_lengkap

    }


    override fun getItemCount(): Int {
        return songs.size
    }

    class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageview: ImageView = itemView.findViewById(R.id.imageView4)
        val textnama: TextView = itemView.findViewById(R.id.textView14)

    }

}
