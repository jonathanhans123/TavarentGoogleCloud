package com.istts.taverent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
data class ChatEntity(
    @PrimaryKey
    val id : Int,
    val pesan : String,
    val id_penginap : Int,
    val username_penginap : String,
    val id_pemilik : Int,
    val username_pemilik : String,
    val created_at : String,
    val status : String,
    val sender : String,
){

}
