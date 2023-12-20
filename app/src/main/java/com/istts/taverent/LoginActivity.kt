package com.istts.taverent

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.istts.taverent.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var db: AppDatabase
    private lateinit var users: MutableList<UserEntity>
    private val coroutine = CoroutineScope(Dispatchers.IO)
    var WS_HOST = ""
    var pemiliks: ArrayList<Pemilik> = ArrayList()
    var penginaps: ArrayList<Penginap> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        WS_HOST = resources.getString(R.string.WS_HOST)
        db = AppDatabase.build(this)
        users = mutableListOf()
        coroutine.launch {
            users.clear()
            users.addAll(db.userDao.fetch().toMutableList())
            Log.i("USER", users.toString())
            runOnUiThread {
                for(i in 0 until users.size){
                    if(users[i].jenis.equals("pemilik")){
                        val user = Pemilik(
                            id = users[i].id_user,
                            username= users[i].username,
                            password=users[i].password,
                            nama_lengkap=users[i].nama_lengkap,
                            email=users[i].email,
                            no_telp=users[i].no_telp,
                            deleted_at=users[i].deleted_at,
                            saldo=users[i].saldo
                        )
                        pemiliks.add(user)
                        val intent = Intent(view.context,SewaMain::class.java)
                        intent.putExtra("pemilik",pemiliks[i])
                        intent.putExtra("id_pemilik",pemiliks[i].id.toString())
                        intent.putExtra("nama_pemilik",pemiliks[i].nama_lengkap.toString())
                        intent.putExtra("username",pemiliks[i].username.toString())
                        startActivity(intent)
                    }else{
                        val user = Penginap(
                            id = users[i].id_user,
                            username= users[i].username,
                            password=users[i].password,
                            nama_lengkap=users[i].nama_lengkap,
                            email=users[i].email,
                            no_telp=users[i].no_telp,
                            deleted_at=users[i].deleted_at,
                            saldo=users[i].saldo
                        )
                        penginaps.add(user)
                        val intent = Intent(view.context,PenginapActivity::class.java)
                        intent.putExtra("penginap",penginaps[i])
                        intent.putExtra("id_penginap",penginaps[i].id.toString())
                        startActivity(intent)
                    }
                }
            }
        }
        setfrag()
        binding.button2.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            runOnUiThread {
                byResult.launch(intent)
            }
        }


    }
    val byResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult->
        if (result.resultCode==Activity.RESULT_OK){
            setfrag()
        }
    }
    fun setfrag(){
        val fragment = LoginChoose2()
        supportFragmentManager.beginTransaction().replace(
            R.id.frag1,fragment
        ).setReorderingAllowed(true).commit()
    }
}