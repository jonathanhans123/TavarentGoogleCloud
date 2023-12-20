package com.istts.taverent

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.databinding.ActivityPenginapProfileBinding
import org.json.JSONArray

class PenginapProfile : AppCompatActivity() {
    private lateinit var binding: ActivityPenginapProfileBinding
    var WS_HOST = ""
    var pemiliks: ArrayList<Pemilik> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPenginapProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        WS_HOST = resources.getString(R.string.WS_HOST)
        refreshPemilik(view)

        binding.btnbackSewa.setOnClickListener{
            finish()
        }

        binding.btnsimpan2.setOnClickListener {
            val nama_lengkap = binding.etnamalengkapPemilik.text.toString()
            val passwords = binding.etpasswordPemilik.text.toString()
            val no_telp = binding.etnoTeleponPemilik.text.toString()
            var id_penginap = intent.getStringExtra("id_penginap").toString()
            if(nama_lengkap != "" || passwords!= ""||no_telp!= "") {
                val strReg = object : StringRequest(
                    Method.POST, "$WS_HOST/penginap/update",
                    Response.Listener {
                        Toast.makeText(this, " Berhasil DiUpdate", Toast.LENGTH_SHORT)
                            .show()
                        val resultIntent = Intent()
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    },
                    Response.ErrorListener {
                        Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                ) {
                    override fun getParams(): MutableMap<String, String>? {
                        val params = HashMap<String, String>()
                        params["id"] = id_penginap
                        params["nama_lengkap"] = nama_lengkap
                        params["password"] = passwords
                        params["no_telp"] = no_telp
                        return params
                    }
                }
                val queue: RequestQueue = Volley.newRequestQueue(this)
                queue.add(strReg)
            }
            else{
                Toast.makeText(this, "field tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun refreshPemilik(view: View){
        val strReq = object : StringRequest(
            Method.GET,"$WS_HOST/pemilik/list",
            Response.Listener {
                val obj: JSONArray = JSONArray(it)
                pemiliks.clear()
                for (i in 0 until obj.length()){
                    val o = obj.getJSONObject(i)
                    val id = o.getInt("id")
                    val username = o.getString("username")
                    val password = o.getString("password")
                    val nama_lengkap = o.getString("nama_lengkap")
                    val email = o.getString("email")
                    val no_telp = o.getString("no_telp")
                    val saldo = o.getInt("saldo")
                    var deleted_at = ""
                    if (o.has("deleted_at")) {
                        deleted_at = o.getString("deleted_at")
                    }
                    val p = Pemilik(id,username,password,nama_lengkap,email,no_telp,deleted_at,saldo)
                    pemiliks.add(p)
                }
            },
            Response.ErrorListener {
                Toast.makeText(view.context, "WS_ERROR2", Toast.LENGTH_SHORT).show()
            }
        ){}
        val queue: RequestQueue = Volley.newRequestQueue(view.context)
        queue.add(strReq)
    }
}