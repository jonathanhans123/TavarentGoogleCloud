package com.istts.taverent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Penginapan(
    var id: Int,
    var nama: String,
    var alamat: String,
    var deskripsi: String,
    var fasilitas: String,
    var jk_boleh: String,
    var tipe: String,
    var harga: Int,
    var koordinat: String,
    var id_pemilik: Int,
    var rating: Int = 0,
) : Parcelable