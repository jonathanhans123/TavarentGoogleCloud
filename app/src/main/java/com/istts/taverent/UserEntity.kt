package com.istts.taverent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    var id: Int,
    var id_user:Int,
    var username: String,
    var password:String,
    var nama_lengkap:String,
    var email:String,
    var no_telp:String,
    var deleted_at:String,
    var saldo:Int,
    var jenis:String
){

}
