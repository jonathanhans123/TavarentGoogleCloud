package com.istts.taverent

import java.text.NumberFormat
import java.util.*

object CurrencyUtils {

    // menambahkan method kedalam class Int
    // sehingga dapat dipanggil langsung pada object dengan class Int
    fun Int.toRupiah():String{
        // digunakan untuk mengubah format angka menjadi format uang rupiah
        val numberFormat = NumberFormat.getCurrencyInstance(Locale("in","ID"))
        return numberFormat.format(this)
    }
}