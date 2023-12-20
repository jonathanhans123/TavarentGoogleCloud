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
import com.istts.taverent.databinding.FragmentPenginapHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.json.JSONArray

class PenginapHomeFragment : Fragment() {
    private lateinit var binding: FragmentPenginapHomeBinding
    var WS_HOST = ""
    private lateinit var penginap: Penginap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPenginapHomeBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }
    private lateinit var db: AppDatabase
    private lateinit var Lguest: MutableList<HomepenginapEntity>
    private lateinit var Pembs: MutableList<PembayaranEntity>
    private val coroutine = CoroutineScope(Dispatchers.IO)
    var pembayarans: ArrayList<Pembayaran> = ArrayList()
    var penginapans: ArrayList<Penginapan> = ArrayList()

    private lateinit var rvPembayaran: RVPembayaran
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        WS_HOST = resources.getString(R.string.WS_HOST)
        db = AppDatabase.build(context)
        Pembs= mutableListOf()
        Lguest = mutableListOf()
        penginap = arguments?.getParcelable<Penginap>("penginap") as Penginap
//        coroutine.launch {
//            Lguest.clear()
//            Lguest.addAll(db.userDao.fetchHpenginapant().toMutableList())
//            Log.i("USER", Lguest.toString())
//            Pembs.clear()
//            Pembs.addAll(db.userDao.fetchPembayaran().toMutableList())
//            Log.i("USER", Pembs.toString())
//            activity?.runOnUiThread(Runnable {
//                for (i in 0 until Lguest.size) {
//                    val id = Lguest[i].id
//                    val nama = Lguest[i].nama
//                    val alamat = Lguest[i].alamat
//                    val deskripsi = Lguest[i].deskripsi
//                    val fasilitas = Lguest[i].fasilitas
//                    var jk_boleh = Lguest[i].jk_boleh
//                    var tipe = Lguest[i].tipe
//                    var harga = Lguest[i].harga
//                    var koordinat = Lguest[i].koordinat
//                    var id_pemilik =Lguest[i].id_pemilik
//                    val p = Penginapan(id,nama,alamat,deskripsi,fasilitas,jk_boleh,tipe,harga,koordinat,id_pemilik)
//                    penginapans.add(p)
//                }
//                for (i in 0 until Pembs.size) {
//                    var id = Pembs[i].id
//                    var total = Pembs[i].total
//                    var tanggal_mulai = Pembs[i].tanggal_mulai
//                    var tanggal_selesai = Pembs[i].tanggal_selesai
//                    var id_penginap = Pembs[i].id_penginap
//                    var id_penginapan = Pembs[i].id_penginapan
//                    var id_kupon = Pembs[i].id_kupon
//                    var id_promo = Pembs[i].id_promo
//                    var nama_penginapan = Pembs[i].nama_penginapan
//                    val p = Pembayaran(id,total,tanggal_mulai,tanggal_selesai,id_penginap,id_penginapan,id_kupon,id_promo,nama_penginapan)
//                    pembayarans.add(p)
//                }
//            })
//        }
        refreshPenginapan(view)
        refreshPembayaran(view)
        rvPembayaran = RVPembayaran(pembayarans)
        binding.rvPembayaran.adapter = rvPembayaran
        binding.rvPembayaran.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)
    }

    fun refreshPembayaran(view:View){
        val strReq = object : StringRequest(
            Method.POST,"$WS_HOST/pembayaran/list/penginapan",
            Response.Listener {
                val obj: JSONArray = JSONArray(it)
                pembayarans.clear()
                for (i in 0 until obj.length()){
                    val o = obj.getJSONObject(i)
                    val id = o.getInt("id")
                    val total = o.getInt("total")
                    val tanggal_mulai = o.getString("tanggal_mulai")
                    val tanggal_selesai = o.getString("tanggal_selesai")
                    val id_penginap = o.getInt("id_penginap")
                    val id_penginapan = o.getInt("id_penginapan")
                    var id_kupon = 0
                    if (!o.isNull("id_kupon")&&o.has("id_promo")) {
                        id_kupon = o.getInt("id_kupon")
                    }
                    var id_promo = 0
                    if (!o.isNull("id_promo")&&o.has("id_promo")) {
                        id_promo = o.getInt("id_promo")
                    }
                    val nama = o.getString("nama")
                    val p = Pembayaran(id,total,tanggal_mulai,tanggal_selesai,id_penginap,id_penginapan,id_kupon,id_promo,nama)
//                    val pemb = PembayaranEntity(
//                        id=id,
//                        total= total,
//                        tanggal_mulai= tanggal_mulai,
//                        tanggal_selesai= tanggal_selesai,
//                        id_penginap= id_penginap,
//                        id_penginapan= id_penginapan,
//                        id_kupon=id_kupon,
//                        id_promo=id_promo,
//                        nama_penginapan=nama
//                    )
//                    coroutine.launch {
//                        db.userDao.insert(pemb)
//                    }
                    pembayarans.add(p)
                }
                if(pembayarans.size==0){
                    binding.linearbelumadakos.visibility = View.VISIBLE
                }else{
                    binding.rvPembayaran.visibility = View.VISIBLE

                }
            },
            Response.ErrorListener {
                Toast.makeText(view.context, "WS_ERROR1", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id_penginap"] = penginap.id.toString()
                return params
            }
        }
        val queue: RequestQueue = Volley.newRequestQueue(view.context)
        queue.add(strReq)
    }
    fun refreshPenginapan(view:View){
        val strReq = object : StringRequest(
            Method.GET,"$WS_HOST/penginapan/list",
            Response.Listener {
                val obj: JSONArray = JSONArray(it)
                penginapans.clear()
                for (i in 0 until obj.length()){
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
                    val p = Penginapan(id,nama,alamat,deskripsi,fasilitas,jk_boleh,tipe,harga,koordinat,id_pemilik)
//                    val penginapan = HomepenginapEntity(
//                        id=id,
//                        nama = nama,
//                        alamat = alamat,
//                        deskripsi = deskripsi,
//                        fasilitas = fasilitas,
//                        jk_boleh = jk_boleh,
//                        tipe = tipe,
//                        harga = harga,
//                        koordinat = koordinat,
//                        id_pemilik = id_pemilik,
//                    )
//                    coroutine.launch {
//                        db.userDao.deleteHpenginapanTable()
//                        db.userDao.insert(penginapan)
//                    }
                    penginapans.add(p)
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