package com.istts.taverent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.istts.taverent.CurrencyUtils.toRupiah

class RVPembayaran(
    private val songs: ArrayList<Pembayaran>,
): RecyclerView.Adapter<RVPembayaran.CustomViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CustomViewHolder {
        var itemView = LayoutInflater.from(parent.context)
        return CustomViewHolder(itemView.inflate(
            R.layout.rv_pembayaran, parent ,false
        ))
    }
    override fun onBindViewHolder(holder: CustomViewHolder,
                                  position: Int) {
        val item = songs[position]
        holder.textnama.text = item.nama_penginapan
        holder.texttanggalmulai.text = item.tanggal_mulai
        holder.texttanggalselesai.text = item.tanggal_selesai
        holder.texttotal.text = item.total.toRupiah()
    }
    override fun getItemCount(): Int {
        return songs.size
    }
    class CustomViewHolder(view: View):
        RecyclerView.ViewHolder(view) {
        val textnama: TextView = itemView.
        findViewById(R.id.textView69)
        val texttanggalmulai: TextView = itemView.
        findViewById(R.id.tvTanggalMulai)
        val texttanggalselesai: TextView = itemView.
        findViewById(R.id.tvTanggalSelesai)
        val texttotal: TextView = itemView.
        findViewById(R.id.textView81)
    }

}
