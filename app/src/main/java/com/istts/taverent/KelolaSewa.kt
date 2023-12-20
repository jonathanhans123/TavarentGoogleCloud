package com.istts.taverent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.databinding.FragmentKelolaSewaBinding
import org.json.JSONArray


class KelolaSewa : Fragment() {
    private lateinit var binding: FragmentKelolaSewaBinding
    private lateinit var pemilik: Pemilik
    private lateinit var rvPenginapan: RVPenginapanPenginapanFavorit

    var WS_HOST = ""
    var penginapans: ArrayList<Penginapan> = ArrayList()
    var penginapansFilter: ArrayList<Penginapan> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentKelolaSewaBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WS_HOST = resources.getString(R.string.WS_HOST)

        pemilik = arguments?.getParcelable<Pemilik>("pemilik") as Pemilik

        rvPenginapan = RVPenginapanPenginapanFavorit(penginapansFilter,R.layout.rv_penginapan_favorit){view, idx ->

        }
        binding.rvPenginapanKelola.adapter = rvPenginapan
        binding.rvPenginapanKelola.layoutManager = LinearLayoutManager(view.context,
            LinearLayoutManager.VERTICAL,false)

        refreshPenginapan(view)
    }

    fun refreshPenginapan(view: View) {
        val strReq = object : StringRequest(
            Method.GET, "$WS_HOST/penginapan/list/rating",
            Response.Listener {
                val obj: JSONArray = JSONArray(it)
                penginapans.clear()
                for (i in 0 until obj.length()) {
                    val o = obj.getJSONObject(i)
                    val id = o.getInt("id")
                    val nama = o.getString("nama")
                    val alamat = o.getString("alamat")
                    val deskripsi = o.getString("deskripsi")
                    val fasilitas = o.getString("fasilitas")
                    var jk_boleh = o.getString("jk_boleh")
                    var tipe = o.getString("tipe")
                    var harga = o.getString("harga").toInt()
                    var koordinat = o.getString("koordinat")
                    var id_pemilik = o.getInt("id_pemilik")
                    var rating = 0
                    val p = Penginapan(
                        id,
                        nama,
                        alamat,
                        deskripsi,
                        fasilitas,
                        jk_boleh,
                        tipe,
                        harga,
                        koordinat,
                        id_pemilik,
                        rating
                    )
                    penginapans.add(p)
                    findPenginapanPemilik()
                }
            },
            Response.ErrorListener {
                Toast.makeText(view.context, "WS_ERROR1", Toast.LENGTH_SHORT).show()
            }
        ) {}
        val queue: RequestQueue = Volley.newRequestQueue(view.context)
        queue.add(strReq)
    }
    fun findPenginapanPemilik(){
        penginapansFilter.clear()
        for (i in 0 until penginapans.size){
            if (penginapans[i].id_pemilik==pemilik.id){
                penginapansFilter.add(penginapans[i])
            }
        }
        rvPenginapan.notifyDataSetChanged()
    }
}