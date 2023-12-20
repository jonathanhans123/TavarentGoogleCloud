package com.istts.taverent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Pembayaran2(
    var id: Int,
    var total: Int,
    var tanggal_mulai: String,
    var tanggal_selesai: String,
    var id_penginap: Int,
    var id_penginapan: Int,
    var id_kupon:Int?,
    var id_promo:Int?,
    var nama_penginapan:String?
) : Parcelable {
    override fun toString(): String {
        return "$id "
    }
}