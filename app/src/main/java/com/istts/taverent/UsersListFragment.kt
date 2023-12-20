package com.istts.taverent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.databinding.FragmentUsersListBinding
import org.json.JSONArray


class UsersListFragment : Fragment() {
    private lateinit var binding: FragmentUsersListBinding
    private lateinit var rvPenginapAdminHome: RVPenginapAdminHome
    private lateinit var rvPemilikAdminHome: RVPemilikAdminHome

    var WS_HOST = ""

    var penginaps: ArrayList<Penginap> = ArrayList()
    var penginapsSearch: ArrayList<Penginap> = ArrayList()
    var pemiliks: ArrayList<Pemilik> = ArrayList()
    var pemiliksSearch: ArrayList<Pemilik> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUsersListBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    fun searchpenginap(s:String){
        penginapsSearch.clear()
        for (i in 0 until penginaps.size){
            if (penginaps[i].nama_lengkap!!.contains(s,true)){
                penginapsSearch.add(penginaps[i])
            }
        }
        Log.e("count",penginapsSearch.size.toString())
        rvPenginapAdminHome.notifyDataSetChanged()
    }
    fun searchpemilik(s:String){
        pemiliksSearch.clear()
        for (i in 0 until pemiliks.size){
            if (pemiliks[i].nama_lengkap!!.contains(s,true)){
                pemiliksSearch.add(pemiliks[i])
            }
        }
        rvPemilikAdminHome.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WS_HOST = resources.getString(R.string.WS_HOST)

        refreshPenginap(view)
        refreshPemilik(view)
        rvPenginapAdminHome = RVPenginapAdminHome(penginapsSearch,R.layout.rv_user_admin_home){ view, idx ->

        }
        rvPemilikAdminHome = RVPemilikAdminHome(pemiliksSearch,R.layout.rv_user_admin_home){ view, idx ->

        }
        binding.radioButton3.isChecked = true
        binding.rvPenginapan.adapter  = rvPenginapAdminHome

        binding.radioButton3.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked){
                binding.rvPenginapan.adapter = rvPenginapAdminHome
                binding.rvPenginapan.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)

                rvPenginapAdminHome.notifyDataSetChanged()
            }else{
                binding.rvPenginapan.adapter = rvPemilikAdminHome
                binding.rvPenginapan.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)

                rvPemilikAdminHome.notifyDataSetChanged()
            }
        }

        binding.imageButton5.setOnClickListener {
            searchpenginap(binding.editTextTextPersonName.text.toString())
            searchpemilik(binding.editTextTextPersonName.text.toString())
        }
        binding.rvPenginapan.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)

    }
    fun refreshPenginap(view:View){
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
                    var deleted_at = ""
                    val saldo = o.getInt("saldo")
                    if (o.has("deleted_at")) {
                        deleted_at = o.getString("deleted_at")
                    }
                    val p = Penginap(id,username,password,nama_lengkap,email,no_telp,deleted_at,saldo)
                    penginaps.add(p)
                    searchpenginap("")
                }
            },
            Response.ErrorListener {
                Toast.makeText(view.context, "WS_ERROR1", Toast.LENGTH_SHORT).show()
            }
        ){}
        val queue: RequestQueue = Volley.newRequestQueue(view.context)
        queue.add(strReq)
    }
    fun refreshPemilik(view:View){
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
                    searchpemilik("")
                }
            },
            Response.ErrorListener {
                Toast.makeText(view.context, "WS_ERROR1", Toast.LENGTH_SHORT).show()
            }
        ){}
        val queue: RequestQueue = Volley.newRequestQueue(view.context)
        queue.add(strReq)
    }
}