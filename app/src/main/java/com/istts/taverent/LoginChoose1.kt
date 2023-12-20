package com.istts.taverent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.databinding.FragmentLoginChoose1Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray


class LoginChoose1 : Fragment() {

    private lateinit var binding: FragmentLoginChoose1Binding
    private lateinit var db: AppDatabase
    private lateinit var users: MutableList<UserEntity>
    private val coroutine = CoroutineScope(Dispatchers.IO)
    var WS_HOST = ""
    var network = false
    var pemiliks: ArrayList<Pemilik> = ArrayList()
    var penginaps: ArrayList<Penginap> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginChoose1Binding.inflate(layoutInflater)
        db = AppDatabase.build(context)
        users = mutableListOf()
        coroutine.launch {
            users.clear()
            users.addAll(db.userDao.fetch().toMutableList())
            Log.i("USER", users.toString())
        }
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WS_HOST = resources.getString(R.string.WS_HOST)

        refreshPemilik(view)
        refreshPenginap(view)

        binding.imageButton.setOnClickListener {
            val fragment  = LoginChoose2()
            val bundle = Bundle()
            fragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.frag1,fragment)
                .commit()
        }

        val tipe = arguments?.getString("tipe")
        binding.textView.setText("Selamat Kembali $tipe")
        if (tipe!=null) {
            binding.btnLogin.setOnClickListener {
                refreshPemilik(view)
                refreshPenginap(view)
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                if (email != "" && password != "") {
                    if (email=="admin"&&password=="admin") {
                        val intent = Intent(view.context,AdminActivity::class.java)
                        activity?.runOnUiThread { startActivity(intent) }
                    }else{
                        var exist = false

                        if (tipe == "pemilik") {
                            for (i in 0 until pemiliks.size) {
                                if (pemiliks[i].email == email) {
                                    if (pemiliks[i].password == password) {
                                        Toast.makeText(view.context, "Success", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(view.context,SewaMain::class.java)
                                        intent.putExtra("pemilik",pemiliks[i])
                                        intent.putExtra("id_pemilik",pemiliks[i].id.toString())
                                        intent.putExtra("nama_pemilik",pemiliks[i].nama_lengkap.toString())
                                        intent.putExtra("username",pemiliks[i].username.toString())

                                        val user = UserEntity(
                                            id = 0,
                                            id_user= pemiliks[i].id,
                                            username= pemiliks[i].username,
                                            password=pemiliks[i].password,
                                            nama_lengkap=pemiliks[i].nama_lengkap,
                                            email=pemiliks[i].email,
                                            no_telp=pemiliks[i].no_telp,
                                            deleted_at=pemiliks[i].deleted_at,
                                            saldo=pemiliks[i].saldo,
                                            jenis = "pemilik"
                                        )
                                        coroutine.launch {
                                            db.userDao.deleteUserTable()
                                            db.userDao.insert(user)
                                        }
                                        activity?.runOnUiThread { startActivity(intent) }
                                    } else {
                                        Toast.makeText(view.context,
                                            "Password Incorrect",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                    exist = true
                                    break
                                }
                            }
                            if(!network){
                                Toast.makeText(view.context,
                                    "No internet Access",
                                    Toast.LENGTH_SHORT).show()
                            }
                            else if (!exist) {
                                Toast.makeText(view.context,
                                    "Email not registered",
                                    Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            for (i in 0 until penginaps.size) {
                                if (penginaps[i].email == email) {
                                    if (penginaps[i].password == password) {
                                        Toast.makeText(view.context, "Success", Toast.LENGTH_SHORT)
                                            .show()
                                        val intent = Intent(view.context,PenginapActivity::class.java)
                                        intent.putExtra("penginap",penginaps[i])
                                        intent.putExtra("id_penginap",penginaps[i].id.toString())
                                        Toast.makeText(view.context, penginaps[i].id.toString(), Toast.LENGTH_SHORT).show()
                                        val user = UserEntity(
                                            id = 0,
                                            id_user= penginaps[i].id,
                                            username= penginaps[i].username,
                                            password=penginaps[i].password,
                                            nama_lengkap=penginaps[i].nama_lengkap,
                                            email=penginaps[i].email,
                                            no_telp=penginaps[i].no_telp,
                                            deleted_at=penginaps[i].deleted_at,
                                            saldo=penginaps[i].saldo,
                                            jenis = "penginap"
                                        )
                                        coroutine.launch {
                                            db.userDao.deleteUserTable()
                                            db.userDao.insert(user)
                                        }
                                        activity?.runOnUiThread { startActivity(intent) }
                                    } else {
                                        Toast.makeText(view.context,
                                            "Paasword Incorrect",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                    exist = true
                                    break
                                }
                            }
                            if(!network){
                                Toast.makeText(view.context,
                                    "No internet Access",
                                    Toast.LENGTH_SHORT).show()
                            }
                            else if (!exist) {
                                Toast.makeText(view.context,
                                    "Email not registered",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(view.context,
                        "Fill all fields",
                        Toast.LENGTH_SHORT).show()
                }
                pemiliks.clear()
                penginaps.clear()
            }
        }
    }
    fun refreshPemilik(view:View){
        val strReq = object : StringRequest(
            Method.GET,"$WS_HOST/pemilik/list",
            Response.Listener {
                network = true
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
                network = false
            }
        ){}
        val queue: RequestQueue = Volley.newRequestQueue(view.context)
        queue.add(strReq)
    }
    fun refreshPenginap(view:View){
        val strReq = object : StringRequest(
            Method.GET,"$WS_HOST/penginap/list",
            Response.Listener {
                network = true
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
                network = false
            }
        ){}
        val queue: RequestQueue = Volley.newRequestQueue(view.context)
        queue.add(strReq)
    }
}