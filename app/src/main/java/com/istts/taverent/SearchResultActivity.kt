package com.istts.taverent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.istts.taverent.databinding.ActivitySearchResultBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.here.sdk.core.GeoCoordinates
import org.json.JSONArray

class SearchResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchResultBinding
    var WS_HOST = ""
    var penginapans: ArrayList<Penginapan> = ArrayList()
    var penginapansJarak: ArrayList<Penginapan> = ArrayList()
    var penginapansFilter: ArrayList<Penginapan> = ArrayList()
    var koordinat = ""
    var alamat = ""
    var diskon = false
    private lateinit var penginap:Penginap
    private lateinit var rvPenginapanPenginapanFavorit: RVPenginapanPenginapanFavorit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        WS_HOST = resources.getString(R.string.WS_HOST)

        koordinat = intent.getStringExtra("koordinat").toString()
        alamat = intent.getStringExtra("alamat").toString()
        binding.editSearch.setText(alamat)
        penginap = intent.getParcelableExtra<Penginap>("penginap") as Penginap

        refreshPenginapan()

        binding.imageButton14.setOnClickListener {
            finish()
        }
        rvPenginapanPenginapanFavorit = RVPenginapanPenginapanFavorit(penginapansFilter,R.layout.rv_penginapan_favorit){view, idx ->
            val intent = Intent(this, PenginapanDetailActivity::class.java)
            intent.putExtra("penginapan",penginapansFilter[idx])
            intent.putExtra("penginap",penginap)
            startActivity(intent)
        }
        binding.rvSearchPenginapan.adapter = rvPenginapanPenginapanFavorit
        binding.rvSearchPenginapan.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.editSearch.setOnClickListener{
            finish()
        }

        binding.chip4.setOnClickListener {
            var bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(this@SearchResultActivity,R.style.BottomSheetDialogTheme)
            var bottomSheetView: View = LayoutInflater.from(this@SearchResultActivity).inflate(R.layout.layout_bottom_sheet,
                findViewById(R.id.bottomsheetcontainer))
            bottomSheetView.findViewById<Button>(R.id.button13).setOnClickListener {
                var minimumharga = -1
                var maximumharga = -1
                var rating = -1
                if (bottomSheetView.findViewById<EditText>(R.id.editTextNumber).text.toString()!=""){
                    minimumharga = bottomSheetView.findViewById<EditText>(R.id.editTextNumber).text.toString().toInt()
                }
                if (bottomSheetView.findViewById<EditText>(R.id.editTextNumber2).text.toString()!=""){
                    maximumharga = bottomSheetView.findViewById<EditText>(R.id.editTextNumber).text.toString().toInt()
                }
                if (bottomSheetView.findViewById<EditText>(R.id.editTextNumber3).text.toString()!=""){
                    rating = bottomSheetView.findViewById<EditText>(R.id.editTextNumber).text.toString().toInt()
                }
                filterPenginapan(
                    minimumharga,maximumharga,rating
                )
            }
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
    }

    fun refreshPenginapan() {
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
                    findRelevan()
                    filterPenginapan()
                }
            },
            Response.ErrorListener {
                Toast.makeText(this, "WS_ERROR1", Toast.LENGTH_SHORT).show()
            }
        ) {}
        val queue: RequestQueue = Volley.newRequestQueue(this)
        queue.add(strReq)
    }

    fun findRelevan(){
        penginapansJarak.clear()
        for (i in 0 until penginapans.size){
            if (findDistance(koordinat,penginapans[i].koordinat)<100000.0){
                penginapansJarak.add(penginapans[i])
            }
        }
        rvPenginapanPenginapanFavorit.notifyDataSetChanged()
    }

    fun findDistance(koor1:String, koor2: String):Double{
        val geoCoordinates1 = GeoCoordinates(
            koor1.substring(0,koor1.indexOf(',')).toDouble(),
            koor1.substring(koor1.indexOf(',')+1,koor1.length-1).toDouble()
        )
        val geoCoordinates2 = GeoCoordinates(
            koor2.substring(0,koor2.indexOf(',')).toDouble(),
            koor2.substring(koor2.indexOf(',')+1,koor2.length-1).toDouble()
        )

        return geoCoordinates1.distanceTo(geoCoordinates2)
    }
    fun filterPenginapan(
        hargaminimum:Int = -1,
        hargamaksimum:Int = -1,
        ratingminimum: Int = -1
    ){
        penginapansFilter.clear()
        for (i in 0 until penginapansJarak.size){
            var valid = true
            if (penginapansJarak[i].harga<hargaminimum&&hargaminimum!=-1){
                valid = false
            }
            if (penginapansJarak[i].harga>hargamaksimum&&hargamaksimum!=-1){
                valid = false
            }
            if (penginapansJarak[i].rating<ratingminimum&&ratingminimum!=-1){
                valid = false
            }
            if (valid){
                penginapansFilter.add(penginapansJarak[i])
            }
        }
        rvPenginapanPenginapanFavorit.notifyDataSetChanged()

    }

}

