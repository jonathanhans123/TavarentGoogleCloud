package com.istts.taverent

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
AppDatabase merupakan file yang digunakan untuk menginisiasi sebuah database dalam
local storage android.
File ini nyimpen variabel2 DAO yang kita buat ke depannya

Pertama, ada annotation(@)Database di mana di dalamnya ini kita bakal ngisi array of entity.
Entity ini kayak sebuah tabel dalam database yang representasinya di kotlin bakal jadi sebuah
class .kt. Jadi semua Entity yang hendak digunakan dan disimpan dalam database ditaruh di
@Database
 */
@Database(entities = [
    UserEntity::class,
    PenginapanEntity::class,
    ChatEntity::class,
    HomepenginapEntity::class,
    PembayaranEntity::class,
],version=1)
abstract class AppDatabase : RoomDatabase(){
    abstract val userDao: UserDao

    companion object {
        private var _database: AppDatabase? = null

        fun build(context:Context?): AppDatabase {
            if(_database == null){
                _database = Room.databaseBuilder(
                    context!!,AppDatabase::class.java,
                    "tavarent_database").build()
            }
            return _database!!
        }
    }
}