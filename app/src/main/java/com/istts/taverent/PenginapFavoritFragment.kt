package com.istts.taverent

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.databinding.FragmentPenginapFavoritBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

class PenginapFavoritFragment : Fragment() {
    private lateinit var binding: FragmentPenginapFavoritBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPenginapFavoritBinding.inflate(layoutInflater)
        val view = binding.root
        return view
    }
    private lateinit var db: AppDatabase
    private lateinit var Lguest: MutableList<PenginapanEntity>
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var rvPenginapanPenginapanFavorit: RVPenginapanPenginapanFavorit
    private lateinit var penginapans: ArrayList<Penginapan>
    private lateinit var penginap: Penginap
    var WS_HOST = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase.build(context)
        Lguest = mutableListOf()
        WS_HOST = resources.getString(R.string.WS_HOST)
        penginapans = ArrayList()
        penginap = arguments?.getParcelable<Penginap>("penginap") as Penginap
        rvPenginapanPenginapanFavorit = RVPenginapanPenginapanFavorit(penginapans,R.layout.rv_penginapan_favorit){view, idx->
            val intent = Intent(view.context,PenginapanDetailActivity::class.java)
            intent.putExtra("penginapan",penginapans[idx])
            intent.putExtra("penginap",penginap)
            startActivity(intent)
        }
        binding.rvFavorit.adapter = rvPenginapanPenginapanFavorit
        binding.rvFavorit.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false )
        if (checkForInternet(requireContext())) {

        } else {
            coroutine.launch {
            Lguest.clear()
            Lguest.addAll(db.userDao.fetchLGuest().toMutableList())
            Log.i("USER", Lguest.toString())

            activity?.runOnUiThread(Runnable {
                for (i in 0 until Lguest.size) {
                    val id = Lguest[i].id
                    val nama = Lguest[i].nama
                    val alamat = Lguest[i].alamat
                    val deskripsi = Lguest[i].deskripsi
                    val fasilitas = Lguest[i].fasilitas
                    var jk_boleh = Lguest[i].jk_boleh
                    var tipe = Lguest[i].tipe
                    var harga = Lguest[i].harga
                    var koordinat = Lguest[i].koordinat
                    var id_pemilik =Lguest[i].id_pemilik
                    val p = Penginapan(id,nama,alamat,deskripsi,fasilitas,jk_boleh,tipe,harga,koordinat,id_pemilik)
                    penginapans.add(p)
                    rvPenginapanPenginapanFavorit.notifyDataSetChanged()
                }
            })
        }
        }

        val strReq = object : StringRequest(
            Method.POST,"$WS_HOST/penginapan/list/favorit",
            Response.Listener {
                val obj: JSONArray = JSONArray(it)
                penginapans.clear()
                coroutine.launch {
                    db.userDao.deleteLGuestTable()
                }
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
                    val penginapan = PenginapanEntity(
                        id=id,
                        nama = nama,
                        alamat = alamat,
                        deskripsi = deskripsi,
                        fasilitas = fasilitas,
                        jk_boleh = jk_boleh,
                        tipe = tipe,
                        harga = harga,
                        koordinat = koordinat,
                        id_pemilik = id_pemilik,
                    )
                    coroutine.launch {
                        db.userDao.insert(penginapan)
                    }
                    penginapans.add(p)
                    rvPenginapanPenginapanFavorit.notifyDataSetChanged()
                }
            },
            Response.ErrorListener {
            }
        ){
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id_penginap"] = penginap.id.toString()
                return params
            }
        }
        val queue: RequestQueue = Volley.newRequestQueue(view.context)
        queue.add(strReq)
    }
    private fun checkForInternet(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
            }
            } else {
            @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
            }
    }
}