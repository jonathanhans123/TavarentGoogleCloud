package com.istts.taverent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.databinding.FragmentAdminHomeBinding
import org.json.JSONArray


class AdminHomeFragment : Fragment() {
    private lateinit var binding: FragmentAdminHomeBinding
    private lateinit var rvPenginapAdminHome: RVPenginapAdminHome

    var WS_HOST = ""

    var penginaps: ArrayList<Penginap> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminHomeBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        WS_HOST = resources.getString(R.string.WS_HOST)

//        binding.linearbottom.addView(Chart(view.context))
//        binding.linearbottom.addView(Chart(view.context))
        refreshPenginap(view)

        rvPenginapAdminHome = RVPenginapAdminHome(penginaps,R.layout.rv_user_admin_home){ view, idx ->
            val popup = PopupMenu(view.context,view)
            popup.inflate(R.menu.popupmenu1)

            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener {
                when (it.itemId){
                    R.id.menudetail->{
//                        val intent = Intent(view.context,)
                    }
                    R.id.menuban->{
                        //ban user
                        activity?.runOnUiThread {
                        rvPenginapAdminHome.notifyDataSetChanged()}
                    }
                }
                true
            })
            popup.show()
        }

        binding.rv1.adapter = rvPenginapAdminHome
        binding.rv1.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)
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
                    val saldo = o.getInt("saldo")
                    var deleted_at = ""
                    if (o.has("deleted_at")) {
                        deleted_at = o.getString("deleted_at")
                    }
                    val p = Penginap(id,username,password,nama_lengkap,email,no_telp,deleted_at,saldo)
                    penginaps.add(p)
                    rvPenginapAdminHome.notifyDataSetChanged()
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