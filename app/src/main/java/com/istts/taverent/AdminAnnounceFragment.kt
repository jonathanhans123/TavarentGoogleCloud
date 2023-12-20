package com.istts.taverent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.databinding.FragmentAdminAnnounceBinding
import org.json.JSONArray

class AdminAnnounceFragment : Fragment() {
    private lateinit var binding: FragmentAdminAnnounceBinding
    var WS_HOST = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdminAnnounceBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    private lateinit var rvAnnouceAdmin: RVAnnouceAdmin
    var pengumumans: ArrayList<Pengumuman> = ArrayList()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WS_HOST = resources.getString(R.string.WS_HOST)

        refreshPengumuman(view)
        rvAnnouceAdmin = RVAnnouceAdmin(pengumumans,R.layout.rv_announce_admin)

        binding.rvpengumuman.adapter = rvAnnouceAdmin
        binding.rvpengumuman.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL,false)

        val byresult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result: ActivityResult ->
            if (result.resultCode==Activity.RESULT_OK){
                refreshPengumuman(view)
            }
        }
        binding.imageButton6.setOnClickListener {
            val intent = Intent(view.context,AddAnnounceActivity::class.java)
            byresult.launch(intent)
        }

    }



    fun refreshPengumuman(view:View){
        val strReq = object : StringRequest(
            Method.GET,"$WS_HOST/pengumuman/list",
            Response.Listener {
                val obj: JSONArray = JSONArray(it)
                pengumumans.clear()
                for (i in 0 until obj.length()){
                    val o = obj.getJSONObject(i)
                    val judul = o.getString("judul")
                    val isi = "• "+o.getString("isi").replace(".","\n• ")
                    val tanggal = o.getString("created_at").substring(0,10)
                    val tipe = o.getInt("tipe")
                    val p = Pengumuman(judul,isi,tanggal,tipe)
                    pengumumans.add(p)
                    rvAnnouceAdmin.notifyDataSetChanged()
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