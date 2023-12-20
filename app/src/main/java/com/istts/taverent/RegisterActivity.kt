package com.istts.taverent

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.databinding.ActivityRegisterBinding
import org.json.JSONArray

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    var WS_HOST = ""

    var pemiliks: ArrayList<Pemilik> = ArrayList()
    var penginaps: ArrayList<Penginap> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        WS_HOST=resources.getString(R.string.WS_HOST)
        refreshPemilik()
        refreshPenginap()

        binding.imageButton2.setOnClickListener {
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK,resultIntent)
            runOnUiThread { finish() }
        }
        binding.radioButton.isChecked
        binding.button4.setOnClickListener {
            val email = binding.editEmail.text.toString().trim()
            val notelp = binding.editNotelp.text.toString().trim()
            val username = binding.editUsername.text.toString().trim()
            val namalengkap = binding.editNamalengkap.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()
            var tipe = ""
            if (binding.radioButton.isChecked){
                tipe = binding.radioButton.text.toString()
            }else{
                tipe = binding.radioButton2.text.toString()
            }

            if (email!=null&&notelp!=""&&username!=""&&namalengkap!=""&&password!=""){
                if (tipe=="Pemilik"){

                    insertPemilik(email,notelp, username, namalengkap, password)
                }else if (tipe=="Penginap"){

                    insertPenginap(email,notelp, username, namalengkap, password)
                }
            }else{
                runOnUiThread {
                    Toast.makeText(this@RegisterActivity, "Fill all fields", Toast.LENGTH_SHORT).show() }
            }
        }
    }
    fun insertPemilik(email:String,notelp:String,username:String,namalengkap:String,password:String){
        var exist = false

        for (i in 0 until pemiliks.size){
            print(pemiliks[i].toString())
            if (email==pemiliks[i].email||notelp==pemiliks[i].email||username==pemiliks[i].username){
                exist = true
            }
        }
        if (!exist){
            val strReg = object : StringRequest(
                Method.POST,"$WS_HOST/pemilik/insert",
                Response.Listener {
                    refreshPemilik()
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "User created", Toast.LENGTH_SHORT).show() }
                },
                Response.ErrorListener {
                    Toast.makeText(this, "WS_ERROR", Toast.LENGTH_SHORT).show()
                }
            ){
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    params["email"] = email
                    params["no_telp"] = notelp
                    params["username"] = username
                    params["nama_lengkap"] = namalengkap
                    params["password"] = password
                    return params
                }
            }
            val queue: RequestQueue = Volley.newRequestQueue(this)
            queue.add(strReg)

        }else{
            runOnUiThread {
                Toast.makeText(this@RegisterActivity, "Username/Email/Notelp not Unique", Toast.LENGTH_SHORT).show() }
        }
    }
    fun insertPenginap(
        email:String,
        notelp:String,
        username:String,
        namalengkap:String,
        password:String
    ){
        var exist = false
        for (i in 0 until penginaps.size){
            if (email==penginaps[i].email||
                notelp==penginaps[i].email||
                username==penginaps[i].username){
                exist = true
            }
        }
        if (!exist){
            val strReg = object : StringRequest(
                Method.POST,"$WS_HOST/penginap/insert",
                Response.Listener {
                    refreshPemilik()
                    Toast.makeText(this@RegisterActivity,
                        "User created",
                        Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener {
                    Toast.makeText(this,
                        it.message.toString(),
                        Toast.LENGTH_SHORT).show()
                }
            ){
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    params["email"] = email
                    params["no_telp"] = notelp
                    params["username"] = username
                    params["nama_lengkap"] = namalengkap
                    params["password"] = password
                    return params
                }
            }
            val queue: RequestQueue = Volley.newRequestQueue(this)
            queue.add(strReg)

        }else{
            Toast.makeText(this@RegisterActivity,
                "Username/Email/Notelp not Unique",
                Toast.LENGTH_SHORT).show()
        }
    }
    fun refreshPemilik(){
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
                    var deleted_at = ""
                    val saldo = o.getInt("saldo")
                    if (o.has("deleted_at")) {
                        deleted_at = o.getString("deleted_at")
                    }
                    val p = Pemilik(id,username,password,nama_lengkap,email,no_telp,deleted_at,saldo)
                    pemiliks.add(p)
                }
            },
            Response.ErrorListener {
                Toast.makeText(this@RegisterActivity, "WS_ERROR1", Toast.LENGTH_SHORT).show()
            }
        ){}
        val queue: RequestQueue = Volley.newRequestQueue(this@RegisterActivity)
        queue.add(strReq)
    }
    fun refreshPenginap(){
        val strReq = object : StringRequest(
            Method.GET,"$WS_HOST/penginap/list",
            Response.Listener {
                val obj: JSONArray = JSONArray(it)
                penginaps.clear()
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
                    val p = Penginap(id,username,password,nama_lengkap,email,no_telp,deleted_at,saldo)
                    penginaps.add(p)
                }
            },
            Response.ErrorListener {
                Toast.makeText(this@RegisterActivity, "WS_ERROR2", Toast.LENGTH_SHORT).show()
            }
        ){}
        val queue: RequestQueue = Volley.newRequestQueue(this@RegisterActivity)
        queue.add(strReq)
    }
}