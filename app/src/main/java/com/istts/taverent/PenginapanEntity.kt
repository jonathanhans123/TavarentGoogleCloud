package com.istts.taverent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "penginapans")
data class PenginapanEntity(
    @PrimaryKey
    val id : Int,
    val nama : String,
    val alamat : String,
    val deskripsi : String,
    val fasilitas : String,
    var jk_boleh : String,
    var tipe : String,
    var harga : Int,
    var koordinat : String,
    var id_pemilik : Int,
){

}
