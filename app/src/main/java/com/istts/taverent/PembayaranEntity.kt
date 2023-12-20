package com.istts.taverent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pembayaran")
data class PembayaranEntity(
    @PrimaryKey
    var id: Int,
    var total: Int,
    var tanggal_mulai: String,
    var tanggal_selesai: String,
    var id_penginap: Int,
    var id_penginapan: Int,
    var id_kupon:Int,
    var id_promo:Int,
    var nama_penginapan:String
){

}
