package com.istts.taverent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.databinding.ActivityChatBinding
import org.json.JSONArray
import org.json.JSONObject

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    var WS_HOST = ""
    var chats:ArrayList<Chat> = ArrayList()
    private lateinit var rvPesan: RVPesan
    private lateinit var penginap: Penginap
    private lateinit var pemilik: Pemilik
    var tipe = ""
    var id_pemilik = 0
    var id_penginap = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        WS_HOST = resources.getString(R.string.WS_HOST)
        tipe = intent.getStringExtra("tipe").toString()
        id_pemilik = intent.getIntExtra("pemilik",0)
        checkPemilik(id_pemilik)

        id_penginap = intent.getIntExtra("penginap",0)
        checkPenginap(id_penginap)

        Log.e("tag",id_penginap.toString()+id_pemilik.toString())
        rvPesan = RVPesan(chats,tipe,this@ChatActivity)
        binding.rvChat.adapter = rvPesan
        binding.rvChat.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)



        binding.imageButton12.setOnClickListener{
            finish()
        }
        binding.imageButton13.setOnClickListener {
            if (binding.editTextChat.text.toString()!=""){
                insertChat(view,binding.editTextChat.text.toString())
            }
        }


    }

    fun checkPenginap(id_penginap:Int){
        Log.e("tag",id_penginap.toString())
        val strReq = object : StringRequest(
            Method.POST,"$WS_HOST/penginap/find",
            Response.Listener {
                val o: JSONObject = JSONObject(it)
                val id = o.getInt("id")
                val username = o.getString("username")
                val password = o.getString("password")
                val nama_lengkap = o.getString("nama_lengkap")
                val email = o.getString("email")
                val no_telp = o.getString("no_telp")
                var deleted_at = ""
                var saldo = o.getInt("saldo")
                if (o.has("deleted_at")) {
                    deleted_at = o.getString("deleted_at")
                }
                penginap =
                    Penginap(id, username, password, nama_lengkap, email, no_telp, deleted_at,saldo)
                if (tipe == "pemilik") {
                    binding.textView51.text = "${penginap.username} (${penginap.nama_lengkap})"
                }

                refreshChat(penginap.id, id_pemilik)
            },
            Response.ErrorListener {
                Toast.makeText(this, "WS_ERROR4", Toast.LENGTH_SHORT).show()
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id_penginap"] = id_penginap.toString()
                return params
            }
        }
        val queue: RequestQueue = Volley.newRequestQueue(this)
        queue.add(strReq)
    }
    fun checkPemilik(id_pemilik:Int){
        val strReq = object : StringRequest(
            Method.POST,"$WS_HOST/pemilik/find",
            Response.Listener {
                val o: JSONObject = JSONObject(it)
                val id = o.getInt("id")
                val username = o.getString("username")
                val password = o.getString("password")
                val nama_lengkap = o.getString("nama_lengkap")
                val email = o.getString("email")
                val no_telp = o.getString("no_telp")
                var saldo = o.getInt("saldo")
                var deleted_at = ""
                if (o.has("deleted_at")) {
                    deleted_at = o.getString("deleted_at")
                }
                pemilik = Pemilik(id, username, password, nama_lengkap, email, no_telp, deleted_at,saldo)
                if (tipe == "penginap") {
                    binding.textView51.text = "${pemilik.username} (${pemilik.nama_lengkap})"
                }

            },
            Response.ErrorListener {
                Toast.makeText(this, "WS_ERROR1", Toast.LENGTH_SHORT).show()
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id_pemilik"] = id_pemilik.toString()
                return params
            }
        }
        val queue: RequestQueue = Volley.newRequestQueue(this)
        queue.add(strReq)
    }
    fun refreshChat(id_penginap:Int,id_pemilik:Int) {
        val strReq = object : StringRequest(
            Method.POST, "$WS_HOST/chat/pesan/list",
            Response.Listener {
                val obj: JSONArray = JSONArray(it)
                chats.clear()
                for (i in 0 until obj.length()) {
                    val o = obj.getJSONObject(i)
                    Log.d("tag",o.toString())

                    val id = o.getInt("id")
                    val pesan = o.getString("pesan")
                    val id_penginap = o.getInt("id_penginap")
                    val username_penginap = o.getString("penginapusername")
                    val id_pemilik = o.getInt("id_pemilik")
                    var username_pemilik = o.getString("pemilikusername")
                    var created_at = o.getString("created_at")
                    var status = o.getString("status")
                    var sender = o.getString("sender")
                    val p = Chat(id,
                        pesan,
                        id_penginap,
                        username_penginap,
                        id_pemilik,
                        username_pemilik,
                        created_at,
                        status,
                        sender
                    )
                    chats.add(p)
                    rvPesan.notifyDataSetChanged()
                }
            },
            Response.ErrorListener {
                Toast.makeText(this@ChatActivity, "WS_ERROR2", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id_penginap"] = id_penginap.toString()
                params["id_pemilik"] = id_pemilik.toString()
                return params
            }
        }
        val queue: RequestQueue = Volley.newRequestQueue(this@ChatActivity)
        queue.add(strReq)
    }

    fun insertChat(view:View,pesan:String){
        Log.d("pesan",pesan.toString())
        Log.d("penginap",penginap.id.toString())
        Log.d("pemilik", pemilik.id.toString())
        Log.d("tipe",tipe)
        val strReq = object : StringRequest(
            Method.POST, "$WS_HOST/chat/insert",
            Response.Listener {
                refreshChat(penginap.id,pemilik.id)
                binding.editTextChat.setText("")
            },
            Response.ErrorListener {
                Toast.makeText(view.context, "WS_ERROR3", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["pesan"] = pesan.toString()
                params["id_penginap"] = penginap.id.toString()
                params["id_pemilik"] = pemilik.id.toString()
                params["sender"] = tipe.toString()
                params["status"] = "test"
                return params
            }
        }
        val queue: RequestQueue = Volley.newRequestQueue(view.context)
        queue.add(strReq)
    }
}