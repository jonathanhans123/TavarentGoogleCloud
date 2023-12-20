package com.istts.taverent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Penginap(
    var id: Int,
    var username:String,
    var password:String,
    var nama_lengkap:String,
    var email:String,
    var no_telp:String,
    var deleted_at:String,
    var saldo:Int,
) : Parcelable {
    override fun toString(): String {
        return "$id $username $password $nama_lengkap $email $no_telp $deleted_at"
    }
}